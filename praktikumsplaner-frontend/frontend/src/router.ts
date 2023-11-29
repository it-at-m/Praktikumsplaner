import Vue from "vue";
import Router from "vue-router";
import Main from "./views/MainView.vue";
import { ROUTER_BASE } from "@/Constants";
import Meldezeitraeume from "./views/MeldezeitraeumeView.vue";
import ExcelImportNWK from "@/components/ExcelImportNWK.vue";
import MeldungStart from "@/views/MeldungPraktikumsstelle/MeldungStart.vue";
import MeldungAusbildung from "@/views/MeldungPraktikumsstelle/MeldungAusbildung.vue";
import MeldungStudium from "@/views/MeldungPraktikumsstelle/MeldungStudium.vue";
import gegenueberstellungView from "@/views/gegenueberstellungView.vue";
import MeldungSuperNWK from "@/views/MeldungPraktikumsstelle/MeldungSuperNWK.vue";

Vue.use(Router);

/*
 * Preventing "NavigationDuplicated" errors in console in Vue-router >= 3.1.0
 * https://github.com/vuejs/vue-router/issues/2881#issuecomment-520554378
 * */
/* eslint-disable @typescript-eslint/no-explicit-any */
const routerMethods = ["push", "replace"];
routerMethods.forEach((method: string) => {
    const originalCall = (Router.prototype as any)[method];
    (Router.prototype as any)[method] = function (
        location: any,
        onResolve: any,
        onReject: any
    ): Promise<any> {
        if (onResolve || onReject) {
            return originalCall.call(this, location, onResolve, onReject);
        }
        return originalCall.call(this, location).catch((err: any) => err);
    };
});
/* eslint-enable @typescript-eslint/no-explicit-any */

export default new Router({
    base: ROUTER_BASE,
    routes: [
        {
            path: "/",
            name: "home",
            component: Main,
            meta: {},
        },
        {
            path: "/excelimport",
            name: "excelimport",
            component: ExcelImportNWK,
        },
        {
            path: "/meldezeitraum",
            name: "meldezeitraum",
            component: Meldezeitraeume,
        },
        {
            path: "/meldungAusbilder",
            name: "meldungOertlAusbilder",
            component: MeldungStart,
        },
        {
            path: "/meldungAusbildung",
            name: "MeldungAusbildung",
            component: MeldungAusbildung,
        },
        {
            path: "/meldungStudium",
            name: "MeldungStudium",
            component: MeldungStudium,
        },
        {
            path: "/superNWK",
            name: "SuperNWK",
            component: MeldungSuperNWK,
        },
        {
            path: "/gegenueberstellung",
            name: "Gegenueberstellung",
            component: gegenueberstellungView,
        },
        { path: "*", redirect: "/" }, //Fallback 2
    ],
});
