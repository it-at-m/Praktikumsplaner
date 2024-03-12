import {type DefaultTheme, defineConfig} from 'vitepress'
import { withMermaid } from "vitepress-plugin-mermaid"

const title = "Praktikumsplaner";

// https://vitepress.dev/reference/site-config
export default withMermaid({
  title: title,
  description: "Documentation of the Praktikumsplaner app",
  head: [
      ['link', { rel: 'icon', type: 'image/png', href: '/logo.png' } ]
  ],
  locales: {
    "/": {
      lang: "en-US",
      title: title,
      description: ""
    }
  },
  themeConfig: {
    logo: { src: "/logo.png" },

    // https://vitepress.dev/reference/default-theme-config
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
      "/documentation/": { base: "/documentation/", items: sidebarDocumentation() },
      "/features/": { base: "/features/", items: sidebarFeatures() }
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/it-at-m/praktikumsplaner' }
    ]
  },
  mermaidPlugin: {
    class: "mermaid my-class", // set additional css classes for parent container
  }
})

function sidebarDocumentation(): DefaultTheme.SidebarItem[] {
  return [
    {
      text: "Tools",
      link: "TOOLS.md"
    }, {
      text: "Configuration",
      link: "architecture/configuration.md"
    }, {
      text: "Architecture",
      collapsed: true,
      items: [
        { text: "Architecture Decision Records (ADRs)", link: "architecture/adrs" },
        { text: "Security concept", link: "architecture/security/" },
        { text: "User interface", link: "architecture/user_interface/" },
        { text: "Color Palette", link: "architecture/colorpalette/" }
      ]
    }, {
      text: "Guides",
      collapsed: true,
      items: [
        {text: "Database Migration", link: "guides/database-migration/" },
        {text: "Local Development", link: "guides/local-development/" }
      ]
    }
  ]
}

function sidebarFeatures(): DefaultTheme.SidebarItem[] {
  return [
    { text: "Nachwuchskräfte", link: "Nachwuchskraefte.md" },
    { text: "Meldezeitraum", link: "meldezeitraum.md" },
    { text: "Praktikumsplätze", link: "Praktikumsplaetze.md" },
    { text: "Assignment", link: "Zuweisung.md" }
  ]
}
