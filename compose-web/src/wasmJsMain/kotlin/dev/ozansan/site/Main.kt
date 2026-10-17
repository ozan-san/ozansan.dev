package dev.ozansan.site

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.resources.configureWebResources

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    configureWebResources {
        resourcePathMapping { path -> "./$path" }
    }
    ComposeViewport("composeApp") {
        App()
    }
}

@Composable
fun App() {
    var route by remember { mutableStateOf(currentRoute()) }
    var dark by remember { mutableStateOf(initialDark()) }

    LaunchedEffect(Unit) {
        window.addEventListener("hashchange") { route = currentRoute() }
    }

    LaunchedEffect(dark) {
        applyBrowserTheme(dark)
    }

    SiteTheme(dark = dark) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = 760.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    SiteHeader(
                        route = route,
                        onNavigate = { path ->
                            route = path
                            window.location.hash = "#$path"
                        },
                        dark = dark,
                        onToggleTheme = {
                            dark = !dark
                            saveTheme(dark)
                        },
                    )

                    val current = normalizeRoute(route)
                    AnimatedContent(
                        targetState = current,
                        transitionSpec = {
                            val forward = routeIndex(targetState) >= routeIndex(initialState)
                            val enter = slideInHorizontally(
                                initialOffsetX = { width -> if (forward) width else -width },
                            ) + fadeIn(tween(400, easing = FastOutSlowInEasing))
                            val exit = slideOutHorizontally(
                                targetOffsetX = { width -> if (forward) -width else width },
                            ) + fadeOut(tween(400, easing = FastOutSlowInEasing))
                            enter togetherWith exit
                        },
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        label = "route",
                    ) { target ->
                        if (target == "/") {
                            Box(Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                                HomePage()
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .verticalScroll(rememberScrollState()),
                            ) {
                                when {
                                    target == "/posts" -> BlogPage(::navigate)
                                    target == "/about" -> AboutPage()
                                    target.startsWith("/posts/") -> {
                                        val post = POSTS.find {
                                            it.slug == target.removePrefix("/posts/")
                                        }
                                        if (post != null) PostPage(post) else NotFoundPage(::navigate)
                                    }
                                    else -> NotFoundPage(::navigate)
                                }
                            }
                        }
                    }

                    SiteFooter()
                }
            }
        }
    }
}

private fun navigate(path: String) {
    window.location.hash = "#$path"
}

/**
 * ViewPager-style ordering of the top-level pages:
 *
 *   Home (0) <-> Blog (1) <-> About (2)
 *
 * Post detail pages live inside the Blog tab, so they share Blog's index. The
 * animation slides toward higher indices (left-to-right navigation) and toward
 * lower indices (back navigation), just like swiping a pager.
 */
private fun routeIndex(route: String): Int {
    val path = normalizeRoute(route)
    return when {
        path.startsWith("/posts") -> 1
        path == "/about" -> 2
        path == "/" -> 0
        else -> 0
    }
}

private fun currentRoute(): String {
    val hash = window.location.hash.removePrefix("#")
    return if (hash.isBlank()) "/" else hash
}

private fun initialDark(): Boolean {
    return when (window.localStorage.getItem("theme")) {
        "dark" -> true
        "light" -> false
        else -> window.matchMedia("(prefers-color-scheme: dark)").matches
    }
}

private fun saveTheme(dark: Boolean) {
    window.localStorage.setItem("theme", if (dark) "dark" else "light")
}

/**
 * Keeps the browser chrome (iOS Safari's top/bottom bars, Android's address bar)
 * in sync with the app theme. iOS honors <meta name="theme-color"> and a solid
 * HTML background; the theme-color tags are media-query based so we also flip
 * the color-scheme and swap in a matching single tag.
 */
private fun applyBrowserTheme(dark: Boolean) {
    val color = if (dark) "#1b1b1b" else "#fafafa"
    val root = document.documentElement as? org.w3c.dom.HTMLElement
    root?.classList?.let { if (dark) it.add("dark") else it.remove("dark") }
    root?.style?.setProperty("color-scheme", if (dark) "dark" else "light")

    document.querySelectorAll("meta[name=\"theme-color\"]").let { nodes ->
        for (i in 0 until nodes.length) {
            nodes.item(i)?.let { node -> node.parentNode?.removeChild(node) }
        }
    }
    val meta = document.createElement("meta") as org.w3c.dom.HTMLMetaElement
    meta.setAttribute("name", "theme-color")
    meta.setAttribute("content", color)
    document.head?.appendChild(meta)
}
