// .vitepress/theme/index.ts
import type { Theme } from "vitepress";
import DefaultTheme from "vitepress/theme";
import GlobalLayout from "../theme/layouts/GlobalLayout.vue";

import status from "../components/adr/status.vue";

export default {
  extends: DefaultTheme,
  Layout: GlobalLayout,
  enhanceApp({ app }) {
    // register your custom global components
    app.component("adrStatus", status);
  },
} satisfies Theme;
