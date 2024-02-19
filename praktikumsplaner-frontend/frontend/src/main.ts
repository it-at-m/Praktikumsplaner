import { createApp } from "vue";

import { registerPlugins } from "@/plugins";
import App from "./App.vue";
import createRouter, { addNavigationGuard } from "@/router";
import moment from "moment";
import { createPinia, PiniaVuePlugin } from "pinia";

const app = createApp(App);

registerPlugins(app);

app.mount("#app");

addNavigationGuard();
