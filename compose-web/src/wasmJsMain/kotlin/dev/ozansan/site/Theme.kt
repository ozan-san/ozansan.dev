package dev.ozansan.site

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.ozansan.site.generated.resources.Res
import dev.ozansan.site.generated.resources.IBMPlexSansCondensed_Bold
import dev.ozansan.site.generated.resources.IBMPlexSansCondensed_Italic
import dev.ozansan.site.generated.resources.IBMPlexSansCondensed_Medium
import dev.ozansan.site.generated.resources.IBMPlexSansCondensed_Regular
import dev.ozansan.site.generated.resources.IBMPlexSansCondensed_SemiBold
import org.jetbrains.compose.resources.Font

private val LightColors = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.White,
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF212121),
    surface = Color(0xFFFAFAFA),
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFEDEDED),
    onSurfaceVariant = Color(0xFF5F6368),
    outline = Color(0xFFE0E0E0),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF6CB0FF),
    onPrimary = Color(0xFF00325A),
    background = Color(0xFF1B1B1B),
    onBackground = Color(0xFFDADADA),
    surface = Color(0xFF1B1B1B),
    onSurface = Color(0xFFDADADA),
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFF9AA0A6),
    outline = Color(0xFF383838),
)

@Composable
private fun ibmPlexSansCondensed(): FontFamily = FontFamily(
    Font(Res.font.IBMPlexSansCondensed_Regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.IBMPlexSansCondensed_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.IBMPlexSansCondensed_Medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.IBMPlexSansCondensed_SemiBold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.IBMPlexSansCondensed_Bold, FontWeight.Bold, FontStyle.Normal),
)

@Composable
private fun siteTypography(family: FontFamily): Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 46.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 30.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
)

@Composable
fun SiteTheme(dark: Boolean, content: @Composable () -> Unit) {
    val target = if (dark) DarkColors else LightColors
    MaterialTheme(
        colorScheme = animatedScheme(target),
        typography = siteTypography(ibmPlexSansCondensed()),
        content = content,
    )
}

/**
 * Animates the colors we actually use so switching between light and dark is a
 * smooth fade instead of an instant flip.
 */
@Composable
private fun animatedScheme(target: ColorScheme): ColorScheme {
    val spec = tween<Color>(durationMillis = 350, easing = FastOutSlowInEasing)
    return target.copy(
        primary = animateColorAsState(target.primary, spec, label = "primary").value,
        onPrimary = animateColorAsState(target.onPrimary, spec, label = "onPrimary").value,
        background = animateColorAsState(target.background, spec, label = "background").value,
        onBackground = animateColorAsState(target.onBackground, spec, label = "onBackground").value,
        surface = animateColorAsState(target.surface, spec, label = "surface").value,
        onSurface = animateColorAsState(target.onSurface, spec, label = "onSurface").value,
        surfaceVariant = animateColorAsState(target.surfaceVariant, spec, label = "surfaceVariant").value,
        onSurfaceVariant = animateColorAsState(target.onSurfaceVariant, spec, label = "onSurfaceVariant").value,
        outline = animateColorAsState(target.outline, spec, label = "outline").value,
    )
}