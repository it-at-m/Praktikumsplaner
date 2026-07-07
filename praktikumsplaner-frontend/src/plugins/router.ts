import { createRouter, createWebHistory } from "vue-router";
import {
  routes as fileBasedRoutes,
  handleHotUpdate,
} from "vue-router/auto-routes";

import { ROUTES_HOME } from "@/constants";
import AccessDeniedView from "@/views/AccessDeniedView.vue";
import assignView from "@/views/AssignView.vue";
import Main from "@/views/MainView.vue";
import Meldezeitraeume from "@/views/MeldezeitraeumeView.vue";
import NachwuchskraefteView from "@/views/nachwuchskraefte/NachwuchskraefteView.vue";
import PraktikumsplaetzeMeldung from "@/views/praktikumsplaetze/PraktikumsplaetzeMeldung.vue";
import PraktikumsplaetzeView from "@/views/praktikumsplaetze/PraktikumsplaetzeView.vue";

const routes = [
  ...fileBasedRoutes,
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
      requiresRole: ["AUSBILDUNGSLEITUNG"],
    },
  },
  {
    path: "/meldezeitraum",
    name: "meldezeitraum",
    component: Meldezeitraeume,
    meta: {
      requiresRole: ["AUSBILDUNGSLEITUNG"],
    },
  },
  {
    path: "/praktikumsplaetze",
    name: "praktikumsplätze",
    component: PraktikumsplaetzeView,
    meta: {
      requiresRole: ["AUSBILDUNGSLEITUNG", "AUSBILDER"],
    },
  },
  {
    path: "/praktikumsplaetze/meldung",
    name: "Meldung",
    component: PraktikumsplaetzeMeldung,
    meta: {
      requiresRole: ["AUSBILDUNGSLEITUNG", "AUSBILDER"],
    },
  },
  {
    path: "/zuweisung",
    name: "Zuweisung",
    component: assignView,
    meta: {
      requiresRole: ["AUSBILDUNGSLEITUNG"],
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
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return {
      top: 0,
      left: 0,
    };
  },
});

if (import.meta.hot) {
  handleHotUpdate(router);
}

export default router;
