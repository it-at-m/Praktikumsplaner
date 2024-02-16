// Composables
import { createRouter, createWebHistory } from "vue-router";

import { useSecurity } from "@/composables/security";
import { ROUTES_HOME } from "@/constants";
import AccessDeniedView from "@/views/AccessDeniedView.vue";
import assignView from "@/views/AssignView.vue";
import NachwuchskraefteView from "@/views/nachwuchskraefte/NachwuchskraefteView.vue";
import MeldungAusbildung from "@/views/praktikumsplaetze/MeldungAusbildung.vue";
import MeldungStudium from "@/views/praktikumsplaetze/MeldungStudium.vue";
import PraktikumsplaetzeView from "@/views/praktikumsplaetze/PraktikumsplaetzeView.vue";
import Main from "../views/MainView.vue";
import Meldezeitraeume from "../views/MeldezeitraeumeView.vue";

const routes = [
    {
        path: "/",
        name: ROUTES_HOME,
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
    { path: "/:catchAll(.*)*", redirect: "/" }, // CatchAll route
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
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
