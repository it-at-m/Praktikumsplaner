import Vue, { VNode } from "vue";
import Vuetify from "./plugins/vuetify";
import App from "./App.vue";
import createRouter, { addNavigationGuard } from "@/router";
import moment from "moment";
import { createPinia, PiniaVuePlugin } from "pinia";

Vue.config.productionTip = false;
Vue.use(PiniaVuePlugin);

const pinia = createPinia();
moment.locale(window.navigator.language);

new Vue({
    pinia,
    vuetify: Vuetify,
    router: createRouter,
    render: (h): VNode => h(App),
}).$mount("#app");

addNavigationGuard();
