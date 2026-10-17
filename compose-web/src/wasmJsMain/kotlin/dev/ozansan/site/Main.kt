package dev.ozansan.site

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
import kotlinx.browser.window
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.configureWebResources

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
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
        window.addEventListener("hashchange", { route = currentRoute() })
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
                    if (current == "/") {
                        Box(Modifier.fillMaxWidth().weight(1f)) {
                            HomePage()
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                        ) {
                            when (current) {
                                "/posts" -> BlogPage(::navigate)
                                "/about" -> AboutPage()
                                else -> {
                                    if (current.startsWith("/posts/")) {
                                        val post = POSTS.find {
                                            it.slug == current.removePrefix("/posts/")
                                        }
                                        if (post != null) PostPage(post) else NotFoundPage(::navigate)
                                    } else {
                                        NotFoundPage(::navigate)
                                    }
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