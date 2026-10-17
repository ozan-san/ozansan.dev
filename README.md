# ozansan.dev

Personal website of Ozan Şan, built with [Astro](https://astro.build).

## Stack

- **Astro** — static site output
- **Content collections** — blog posts and standalone pages
- **Plain CSS** — light/dark theming, no UI framework

## Commands

| Command           | Action                                        |
| :---------------- | :-------------------------------------------- |
| `npm install`     | Install dependencies                          |
| `npm run dev`     | Start the dev server at `localhost:4321`      |
| `npm run build`   | Build the production site to `./dist`         |
| `npm run preview` | Preview the production build locally          |

## Project structure

```
public/                 Static assets (avatar, favicon, CNAME)
src/
  components/           Header, Footer, ThemeToggle, Social, FormattedDate
  content/
    blog/               Blog posts (Markdown)
    pages/              Standalone pages (e.g. about)
  layouts/              BaseLayout, PostLayout
  pages/                Routes: /, /posts/, /posts/[slug]/, /about/, 404
  styles/global.css     Global styles and theme tokens
  consts.ts             Site metadata, navigation and social links
  content.config.ts     Content collection schemas
astro.config.mjs        Astro configuration
```

## Content

- **Blog posts** live in `src/content/blog/`. Frontmatter: `title`, `description`,
  `date`, `draft`, `tags`, `categories`, `externalLink`.
- **Pages** live in `src/content/pages/`.
- Posts marked `draft: true` are excluded from production builds.

## Deployment

Pushing to `main` builds the site and deploys it to GitHub Pages via
`.github/workflows/deploy.yml`.