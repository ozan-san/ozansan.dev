package dev.ozansan.site

const val SITE_TITLE = "Ozan Şan"
const val SITE_INFO = "Android & Backend Developer"
const val SITE_SINCE = 2019
const val SITE_YEAR = 2026

const val GITHUB_URL = "https://github.com/ozan-san/"
const val LINKEDIN_URL = "https://linkedin.com/in/ozan-san/"

data class Post(
    val slug: String,
    val title: String,
    val date: String,
    val body: List<String>,
)

/**
 * The Astro site's only post is still a draft. This Compose copy includes it as
 * a sample entry so the blog and post routes can be demonstrated.
 */
val POSTS = listOf(
    Post(
        slug = "test",
        title = "Test",
        date = "January 11, 2026",
        body = listOf(
            "This is bold text, and this is emphasized text.",
            "Visit the Hugo website!",
        ),
    ),
)