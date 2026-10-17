package dev.ozansan.site

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ozansan.site.generated.resources.Res
import dev.ozansan.site.generated.resources.fa_brands_400
import dev.ozansan.site.generated.resources.fa_regular_400
import dev.ozansan.site.generated.resources.fa_solid_900
import org.jetbrains.compose.resources.Font

enum class FaBrand(val icon: String) {
    GitHub("\uF09B"),
    LinkedIn("\uF08C"),
    Android("\uF17B"),
    Java("\uF4E4"),
    Python("\uF3E2"),
    Amazon("\uF270"),
    Aws("\uF375"),
    Git("\uF1D3"),
    GitAlt("\uF841"),
    Jira("\uF7B1"),
    Slack("\uF198"),
    Atlassian("\uF77B"),
    Docker("\uF395"),
    Linux("\uF17C"),
    Windows("\uF17A"),
    Apple("\uF179"),
    Google("\uF1A0"),
}

@Composable
private fun brandsFamily(): FontFamily = FontFamily(
    Font(Res.font.fa_brands_400, FontWeight.Normal),
)

@Composable
private fun solidFamily(): FontFamily = FontFamily(
    Font(Res.font.fa_solid_900, FontWeight.Bold),
)

@Composable
private fun regularFamily(): FontFamily = FontFamily(
    Font(Res.font.fa_regular_400, FontWeight.Normal),
)

/**
 * Font Awesome glyphs carry large ascender/line-height metrics, so a bare [Text]
 * renders the glyph visually high relative to neighboring copy. Wrapping it in a
 * centered, square [Box] keeps every icon optically aligned with its label.
 */
@Composable
private fun IconGlyph(
    glyph: String,
    family: FontFamily,
    size: Dp,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = glyph,
            fontFamily = family,
            fontSize = (size.value * 0.82f).sp,
            lineHeight = (size.value * 0.82f).sp,
            color = color,
        )
    }
}

@Composable
fun FaBrandIcon(
    brand: FaBrand,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    size: Dp = 16.dp,
) {
    IconGlyph(brand.icon, brandsFamily(), size, color, modifier)
}

@Composable
fun FaSolidIcon(
    icon: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    size: Dp = 16.dp,
) {
    IconGlyph(icon, solidFamily(), size, color, modifier)
}

@Composable
fun FaRegularIcon(
    icon: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    size: Dp = 16.dp,
) {
    IconGlyph(icon, regularFamily(), size, color, modifier)
}

/**
 * A label with a leading brand glyph, used for the company / tech rows on the
 * About page.
 */
@Composable
fun BrandedText(
    brand: FaBrand,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FaBrandIcon(brand = brand, color = color, size = 18.dp)
        Spacer(Modifier.width(8.dp))
        Text(text = text, color = color, style = MaterialTheme.typography.bodyMedium)
    }
}

