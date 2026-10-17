package dev.ozansan.site

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SiteHeader(
    route: String,
    onNavigate: (String) -> Unit,
    dark: Boolean,
    onToggleTheme: () -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = SITE_TITLE.uppercase(),
                style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 1.5.sp),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onNavigate("/") }
                    .padding(vertical = 4.dp),
            )
            Spacer(Modifier.weight(1f))
            NavLink("Blog", "/posts", route, onNavigate)
            Spacer(Modifier.width(4.dp))
            NavLink("About", "/about", route, onNavigate)
            Spacer(Modifier.width(12.dp))
            ThemeToggle(dark, onToggleTheme)
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
    }
}

@Composable
private fun NavLink(
    label: String,
    path: String,
    route: String,
    onNavigate: (String) -> Unit,
) {
    val normalized = normalizeRoute(route)
    val active = normalized == path || normalized.startsWith("$path/")
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable { onNavigate(path) }
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Composable
private fun ThemeToggle(dark: Boolean, onToggle: () -> Unit) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .clickable { onToggle() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (dark) "☀" else "☾",
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun SiteFooter() {
    Column(Modifier.fillMaxWidth().padding(top = 24.dp)) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Text(
            text = if (SITE_SINCE < SITE_YEAR) "© $SITE_SINCE–$SITE_YEAR $SITE_TITLE" else "© $SITE_YEAR $SITE_TITLE",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
        )
    }
}

@Composable
fun SocialLinks() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SocialLink("GitHub", GITHUB_URL, FaBrand.GitHub)
        SocialLink("LinkedIn", LINKEDIN_URL, FaBrand.LinkedIn)
    }
}

@Composable
private fun SocialLink(label: String, url: String, brand: FaBrand) {
    val uriHandler = LocalUriHandler.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable { uriHandler.openUri(url) }
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        FaBrandIcon(
            brand = brand,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            size = 18.dp,
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textDecoration = TextDecoration.Underline,
        )
    }
}

@Composable
fun PageTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(top = 48.dp, bottom = 20.dp),
    )
}

@Composable
fun SectionHeading(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(top = 36.dp, bottom = 12.dp),
    )
}

@Composable
fun SubHeading(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 20.dp, bottom = 6.dp),
    )
}

@Composable
fun Paragraph(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 6.dp),
    )
}

@Composable
fun MutedParagraph(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 4.dp),
    )
}

@Composable
fun Bullet(text: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.width(10.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun LinkText(label: String, url: String) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { uriHandler.openUri(url) }
            .padding(vertical = 2.dp),
    )
}

@Composable
fun TagRow(tags: List<String>) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        tags.forEach { tag ->
            Text(
                text = tag,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 10.dp, vertical = 3.dp),
            )
        }
    }
}

internal fun normalizeRoute(route: String): String {
    var value = route.substringBefore("?").substringBefore("#")
    if (value.length > 1 && value.endsWith("/")) {
        value = value.dropLast(1)
    }
    return if (value.isBlank()) "/" else value
}