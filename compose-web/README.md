# ozansan.dev — Compose for Web

This is the **live site**: [ozansan.dev](https://ozansan.dev) is built with
**Compose Multiplatform for Web** (Kotlin/Wasm).

An earlier Astro implementation is kept at the repository root as an
alternative.

## Stack

- Kotlin 2.4.21 (Kotlin/Wasm)
- Compose Multiplatform 1.12.2 (Material 3)
- Gradle 9.5.0

## Commands

```bash
# Dev server (opens a browser)
./gradlew wasmJsBrowserDevelopmentRun

# Production build
./gradlew wasmJsBrowserDistribution
```

The production output is written to:

```
build/dist/wasmJs/productionExecutable/
```

Serve that directory over HTTP with `application/wasm` as the MIME type for
`.wasm` files. The app needs a browser with WebAssembly GC support
(Chrome 119+, Firefox 120+, Safari 18+).

## Pages

Routing is hash-based, so the app stays a single static bundle:

| Route           | Page         |
| :-------------- | :----------- |
| `/#/`           | Home         |
| `/#/posts`      | Blog list    |
| `/#/posts/test` | Post detail  |
| `/#/about`      | About / CV   |

## Deployment

Pushes to `main` build this directory and deploy the output to GitHub Pages via
`.github/workflows/deploy.yml`. The custom domain (`ozansan.dev`) is added to
the artifact during the workflow.

## Known trade-offs

Compose for Web renders into a single `<canvas>` through Skia:

- **SEO** — crawlers see almost no HTML, so content is not indexed.
- **Text selection / find-in-page** — not available by default.
- **Accessibility** — improving, but far behind semantic HTML.
- **Initial payload** — roughly 11 MB uncompressed (Skia + Wasm) before the
  first frame; it compresses much smaller but is still heavier than a plain
  static site.

## Content note

The Astro site's only post (`test`) is still a draft and is not published. This
Compose version includes it as a sample entry so the blog and post routes can be
demonstrated.
