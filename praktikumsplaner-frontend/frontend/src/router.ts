import Vue from "vue";
import Router from "vue-router";
import Main from "./views/MainView.vue";
import { ROUTER_BASE } from "@/constants";
import Meldezeitraeume from "./views/MeldezeitraeumeView.vue";
import MeldungAusbildung from "@/views/praktikumsplaetze/MeldungAusbildung.vue";
import MeldungStudium from "@/views/praktikumsplaetze/MeldungStudium.vue";
import assignView from "@/views/AssignView.vue";
import NachwuchskraefteView from "@/views/nachwuchskraefte/NachwuchskraefteView.vue";
import PraktikumsplaetzeView from "@/views/praktikumsplaetze/PraktikumsplaetzeView.vue";
import { useSecurity } from "@/composables/security";
import AccessDeniedView from "@/views/AccessDeniedView.vue";

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

const router = new Router({
    base: ROUTER_BASE,
    routes: [
        {
            path: "/",
            name: "home",
            component: Main,
            meta: {},
        },
        {
            path: "/nachwuchskraefte",
            name: "nachwuchskraefte",
            component: NachwuchskraefteView,
        },
        {
            path: "/meldezeitraum",
            name: "meldezeitraum",
            component: Meldezeitraeume,
        },
        {
            path: "/praktikumsplaetze",
            name: "praktikumsplätze",
            component: PraktikumsplaetzeView,
            meta: {
                requiresRole: ["ROLE_AUSBILDUNGSLEITUNG", "ROLE_AUSBILDER"],
            },
        },
        {
            path: "/praktikumsplaetze/meldungAusbildung",
            name: "MeldungAusbildung",
            component: MeldungAusbildung,
        },
        {
            path: "/praktikumsplaetze/meldungStudium",
            name: "MeldungStudium",
            component: MeldungStudium,
        },
        {
            path: "/zuweisung",
            name: "Zuweisung",
            component: assignView,
        },
        {
            path: "/accessDenied",
            name: "AccessDenied",
            component: AccessDeniedView,
        },
        { path: "*", redirect: "/" }, //Fallback 2
    ],
});

router.beforeEach((to, from, next) => {
    const requiresRoles = to.meta?.requiresRole ?? undefined;
    const security = useSecurity();
    if (
        requiresRoles !== undefined &&
        security.checkForAnyRole(requiresRoles)
    ) {
        next();
    } else if (requiresRoles !== undefined) {
        next({ name: "AccessDenied" });
    } else {
        next();
    }
});

export default router;
