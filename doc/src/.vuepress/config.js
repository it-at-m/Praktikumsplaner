const { description } = require("../../package.json");

const title = "Praktikumsplaner";

module.exports = {
  base: "/Praktikumsplaner/",
  /**
   * Ref：https://v1.vuepress.vuejs.org/config/#title
   */
  title: title,
  /**
   * Ref：https://v1.vuepress.vuejs.org/config/#description
   */
  description: description,
  /**
   * Output directory
   */
  dest: "dist",
  /**
   * port to run dev server
   */
  port: 8099,

  /**
   * Extra tags to be injected to the page HTML `<head>`
   *
   * ref：https://v1.vuepress.vuejs.org/config/#head
   */
  head: [
    ["meta", { name: "theme-color", content: "#333333" }],
    ["meta", { name: "apple-mobile-web-app-capable", content: "yes" }],
    [
      "meta",
      { name: "apple-mobile-web-app-status-bar-style", content: "black" },
    ],
    ['link', { rel: 'icon', href: 'logo.png' }]
  ],
  locales: {
    "/": {
      lang: "en-US", // this will be set as the lang attribute on <html>
      title: title,
      description: "",
    },
  },

  /**
   * Theme configuration, here is the default theme configuration for VuePress.
   *
   * ref：https://v1.vuepress.vuejs.org/theme/default-theme-config.html
   */
  themeConfig: {
    repo: "https://github.com/it-at-m/praktikumsplaner",
    logo: "/logo.png",
    editLinks: false,
    docsDir: "",
    editLinkText: "",
    lastUpdated: false,
    locales: {
      "/": {
        selectText: "Language",
        label: "English",
        ariaLabel: "Language",
        nav: [
          {
            text: "About",
            link: "/about/",
          },
          {
            text: "Features",
            link: "/features/",
          },
          {
            text: "Technical Documentation",
            link: "/documentation/",
          },
        ],
        sidebar: {
          "/documentation/": [
            {
              title: "Tools",
              path: "TOOLS.md",
            },
            {
              title: "Configuration",
              path: "architecture/configuration.md",
            },
            {
              title: "Architecture",
              collapsable: true,
              children: ["architecture/adrs", "architecture/security/", "architecture/user_interface/", "architecture/colorpalette/"],
            },
            {
              title: "Guides",
              collapsable: true,
              children: ["guides/database-migration/", "guides/local-development/"],
            },
          ],
          "/features/": [
            {
              title: 'Nachwuchskräfte',
              path: 'Nachwuchskraefte.md',
            },
            {
              title: 'Meldezeitraum',
              path: 'meldezeitraum.md',
            },
            {
              title: 'Praktikumsplätze',
              path: 'Praktikumsplaetze.md',
            },
            {
              title: 'Assignment',
              path: 'Zuweisung.md',
            }
          ]
        },
      },
    },
  },

  /**
   * markdown extension
   */
  markdown: {
    lineNumbers: true,
    extendMarkdown: (md) => {
      md.use(require("markdown-it-footnote"));
    },
  },

  /**
   * Apply plugins，ref：https://v1.vuepress.vuejs.org/plugin/
   */
  plugins: [
    "@vuepress/plugin-back-to-top",
    "@vuepress/plugin-medium-zoom",
    "vuepress-plugin-mermaidjs",
  ],
};
