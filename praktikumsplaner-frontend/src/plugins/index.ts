import type { App } from "vue";

import i18n from "@/plugins/i18n";
import pinia from "./pinia";
import router from "./router.ts";
import vuetify from "./vuetify";

export function registerPlugins(app: App) {
  app.use(router).use(pinia).use(i18n).use(vuetify);
}
