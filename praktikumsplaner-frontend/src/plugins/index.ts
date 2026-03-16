import type { App } from "vue";

import i18n from "@/plugins/i18n";
import router from "../router";
import pinia from "./pinia";
import vuetify from "./vuetify";

export function registerPlugins(app: App) {
  app.use(router).use(pinia).use(i18n).use(vuetify);
}
