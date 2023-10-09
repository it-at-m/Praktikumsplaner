# Praktikumsplaner Docs

## With local Node

```bash
npm install
npm run dev
```

## Without local Node

Execute once to get local Node / NPM on your machine and install required modules:
```bash
mvn initialize -Pinstall-node-and-npm
```

For the local development you can use: 
```bash
mvn -Pdocs -Pdev package
```
Open http://localhost:8099/ to serve the docs with life preview.



## Document new features and bugfixes

You should add new features to [src/features/index.md](src/features/index.md) with a title and a description 
that users can see all capabilities of the Praktikumsplaner application at once.

### Adding new pages

If you want new pages to show up in the sidebar you have to add them in the [src/.vuepress/config.js](src/.vuepress/config.js).
See the [vuepress docs](https://v1.vuepress.vuejs.org/theme/default-theme-config.html#sidebar) for additional information. 
