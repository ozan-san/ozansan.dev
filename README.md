# ozansan.dev

Personal website of Ozan Şan — [ozansan.dev](https://ozansan.dev).

The live site is built with **Compose Multiplatform for Web** (Kotlin/Wasm) and
lives in [`compose-web/`](./compose-web). Pushes to `main` build it and deploy
it to GitHub Pages.

An earlier **Astro** implementation is kept at the repository root as an
alternative: it is fast, static and SEO-friendly. It is no longer the deployed
site, but it still builds.

## Live site — Compose for Web

```bash
cd compose-web

./gradlew wasmJsBrowserDevelopmentRun   # dev server at localhost:8080
./gradlew wasmJsBrowserDistribution     # production build
```

Output: `compose-web/build/dist/wasmJs/productionExecutable/`

## Alternative — Astro

```bash
npm install
npm run dev      # localhost:4321
npm run build    # ./dist
```

## Deployment

`.github/workflows/deploy.yml` builds `compose-web` and publishes
`compose-web/build/dist/wasmJs/productionExecutable` to GitHub Pages on every
push to `main`.
