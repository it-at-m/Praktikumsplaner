// Composables
import { createRouter, createWebHistory } from "vue-router";

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
        meta: {
            requiresRole: ["ROLE_AUSBILDUNGSLEITUNG"],
        },
    },
    {
        path: "/meldezeitraum",
        name: "meldezeitraum",
        component: Meldezeitraeume,
        meta: {
            requiresRole: ["ROLE_AUSBILDUNGSLEITUNG"],
        },
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
        meta: {
            requiresRole: ["ROLE_AUSBILDUNGSLEITUNG", "ROLE_AUSBILDER"],
        },
    },
    {
        path: "/praktikumsplaetze/meldungStudium",
        name: "MeldungStudium",
        component: MeldungStudium,
        meta: {
            requiresRole: ["ROLE_AUSBILDUNGSLEITUNG", "ROLE_AUSBILDER"],
        },
    },
    {
        path: "/zuweisung",
        name: "Zuweisung",
        component: assignView,
        meta: {
            requiresRole: ["ROLE_AUSBILDUNGSLEITUNG"],
        },
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

export default router;
