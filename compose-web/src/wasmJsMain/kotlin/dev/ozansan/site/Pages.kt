package dev.ozansan.site

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import dev.ozansan.site.generated.resources.Res
import dev.ozansan.site.generated.resources.avatar
import dev.ozansan.site.generated.resources.metu
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomePage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(Res.drawable.avatar),
                contentDescription = "Portrait of Ozan Şan",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
            )
            Text(
                text = SITE_TITLE,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp),
            )
            Text(
                text = SITE_INFO,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(20.dp))
            SocialLinks()
        }
    }
}

@Composable
fun BlogPage(onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        PageTitle("Blog")
        if (POSTS.isEmpty()) {
            MutedParagraph("No posts yet.")
        } else {
            POSTS.forEach { post ->
                PostRow(post, onNavigate)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
private fun PostRow(post: Post, onNavigate: (String) -> Unit) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val wide = maxWidth > 600.dp
        if (wide) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate("/posts/${post.slug}") }
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = post.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(180.dp),
                )
                Spacer(Modifier.width(24.dp))
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate("/posts/${post.slug}") }
                    .padding(vertical = 16.dp),
            ) {
                Text(
                    text = post.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun PostPage(post: Post) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 48.dp),
        )
        Text(
            text = post.date,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
        )
        post.body.forEach { Paragraph(it) }
    }
}

@Composable
fun AboutPage() {
    Column(Modifier.fillMaxWidth()) {
        PageTitle("About")

        Paragraph("Hi! I'm Ozan Şan.")
        Paragraph("I'm a software engineer, primarily working with:")
        Bullet("Android development, and:")
        Bullet("Backend development.")

        SectionHeading("Education")
        EducationItem(
            university = "Middle East Technical University (ODTÜ)",
            major = "Department of Computer Engineering - Ankara, Türkiye",
            description = "BSc in Computer Engineering. Graduated 18th in class, with a cGPA of 3.46/4.00.",
        )

        SectionHeading("Past experience")
        ExperienceItem(
            role = "Backend Software Engineer II",
            company = "Atlassian",
            companyUrl = "https://atlassian.com",
            companyBrand = FaBrand.Atlassian,
            period = "September 2024 - September 2025",
            bullets = listOf(
                "Worked on backend services of large-scale projects in Jira Service Management, Jira and other products.",
                "Performed on-call duties.",
                "Used technologies: Spring Boot (Java), AWS (DynamoDB, SQS, CloudWatch), Splunk.",
            ),
        )
        ExperienceItem(
            role = "Mobile Software Engineer II (Android)",
            company = "Atlassian",
            companyBrand = FaBrand.Atlassian,
            period = "August 2023 - September 2024",
            bullets = listOf(
                "Built Atlassian's next level mobile experiences in Android.",
                "Worked mainly in the flagship app for Atlassian: Jira Cloud Android.",
                "Overseen deployments and releases to Play Store.",
                "Overseen post-deployment processes, monitoring, and regression-preventions.",
                "Skills utilized: Kotlin, Jetpack Compose, Android Development.",
            ),
        )
        ExperienceItem(
            role = "Mobile Software Engineer I (Android)",
            company = "Atlassian",
            companyBrand = FaBrand.Atlassian,
            period = "July 2022 - August 2023",
            bullets = listOf(
                "Worked on Atlassian's Incident Management platform Opsgenie's Android app.",
                "Independently overseen deployments and releases to Play Store.",
                "Independently overseen post-deployment processes, monitoring, and regression-preventions.",
                "Skills utilized: Kotlin, Android Jetpack (XML), Jetpack Compose, Android Development.",
            ),
        )
        ExperienceItem(
            role = "Android Developer (Part Time)",
            company = "Sociable, Inc.",
            companyBrand = FaBrand.Android,
            period = "February 2022 - May 2022",
            bullets = listOf(
                "Worked on developing Sociable Inc.'s mobile apps.",
                "Used technologies: Kotlin, Gradle, Dagger, Android Studio.",
            ),
        )
        ExperienceItem(
            role = "Android Developer (Part Time)",
            company = "Pyde Technologies",
            companyBrand = FaBrand.Android,
            period = "September 2020 - February 2021",
            bullets = listOf(
                "Worked on Pyde Technologies' Android applications, PokeMate and PokeTrade.",
            ),
        )

        SectionHeading("Internships")
        Bullet("Atlassian, July 2021 - September 2021. Android Software Engineering summer intern.")
        Bullet("Czech Technical University, AI Center: August 2020 - September 2020.")
        Bullet("JotForm, June 2019 - July 2019.")

        SectionHeading("Skills")
        Skill("Programming Languages", "Java, Kotlin, Python.", FaBrand.Java)
        Skill("Mobile Frameworks", "Android (XML & Jetpack Compose).", FaBrand.Android)
        Skill("Backend Frameworks", "Spring Boot w/ Java.", FaBrand.Java)
        Skill("Tools and Other", "Git, Jira, JSM / Opsgenie, AWS.", FaBrand.Aws)
        Skill("Languages", "English (full professional proficiency), Turkish (Native).", null)
    }
}

@Composable
private fun EducationItem(
    university: String,
    major: String,
    description: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Image(
            painter = painterResource(Res.drawable.metu),
            contentDescription = "METU logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = university,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = major,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp),
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun ExperienceItem(
    role: String,
    company: String,
    period: String,
    bullets: List<String>,
    companyUrl: String? = null,
    companyBrand: FaBrand? = null,
) {
    Column(Modifier.fillMaxWidth().padding(top = 18.dp)) {
        Text(
            text = role,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        when {
            companyBrand != null -> BrandedText(companyBrand, company)
            companyUrl != null -> LinkText(company, companyUrl)
            else -> Text(
                text = company,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = period,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp),
        )
        bullets.forEach { Bullet(it) }
    }
}

@Composable
private fun Skill(label: String, value: String, brand: FaBrand?) {
    Row(Modifier.fillMaxWidth().padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
        if (brand != null) {
            FaBrandIcon(
                brand = brand,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                size = 18.dp,
            )
            Spacer(Modifier.width(8.dp))
        }
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun NotFoundPage(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("404", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("Page not found", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Go back home",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onNavigate("/") }
                .padding(8.dp),
        )
    }
}