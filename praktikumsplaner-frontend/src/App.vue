<template>
  <v-app>
    <the-snackbar />
    <error-dialog />
    <v-app-bar color="primary">
      <v-row align="center">
        <v-col
          cols="3"
          class="d-flex align-center justify-start"
        >
          <v-app-bar-nav-icon @click.stop="toggleDrawer" />
          <router-link to="/">
            <img
              height="50"
              width="50"
              src="@/assets/logo.png"
              alt="Logo der Applikation"
              class="mt-1 mr-1"
            />
          </router-link>
          <router-link
            to="/"
            class="no-underline"
          >
            <v-toolbar-title class="font-weight-bold">
              <span class="text-white">Praktikumsplaner</span>
            </v-toolbar-title>
          </router-link>
        </v-col>
        <v-col
          cols="6"
          class="d-flex align-center justify-center"
        >
        </v-col>
        <v-col
          cols="1"
          class="d-flex align-center justify-end"
        >
        </v-col>
      </v-row>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer">
      <v-list nav>
        <v-list-item
          v-if="security.isAusbildungsleitung()"
          :to="{ path: '/nachwuchskraefte' }"
        >
          <v-list-item-title>Nachwuchskräfte</v-list-item-title>
        </v-list-item>
        <v-list-item
          v-if="security.isAusbildungsleitung()"
          :to="{ path: '/meldezeitraum' }"
        >
          <v-list-item-title>Meldezeitraum</v-list-item-title>
        </v-list-item>
        <v-list-item
          v-if="security.checkForAnyRole(['AUSBILDER', 'AUSBILDUNGSLEITUNG'])"
          :to="{ path: '/praktikumsplaetze' }"
        >
          <v-list-item-title>Praktikumsplätze</v-list-item-title>
        </v-list-item>
        <v-list-item
          v-if="security.isAusbildungsleitung()"
          :to="{ path: '/zuweisung' }"
        >
          <v-list-item-title>Zuweisung</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <v-container
        fluid
        class="pa-8"
      >
        <router-view v-slot="{ Component }">
          <v-fade-transition mode="out-in">
            <div>
              <component :is="Component" />
            </div>
          </v-fade-transition>
        </router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import { onBeforeMount, onMounted, ref } from "vue";

import { getInfo } from "@/api/InfoService";
import { UserService } from "@/api/UserService";
import TheSnackbar from "@/components/TheSnackbar.vue";
import ErrorDialog from "@/components/TheUserErrorDialog.vue";
import { useSecurity } from "@/composables/security";
import { useSnackbarStore } from "@/stores/snackbar";
import { useUserStore } from "@/stores/user";

const drawer = ref(true);
const userStore = useUserStore();
const snackbarStore = useSnackbarStore();
const userService = new UserService();
const security = useSecurity();

const toggleDrawer = () => {
  drawer.value = !drawer.value;
};

onBeforeMount(() => {
  userService.getPermissions().then((userinfo) => {
    userStore.setUsername(userinfo.name);
    userStore.setDepartment(userinfo.department);

    const resourceAccess = userinfo.resource_access ?? {};
    const praktikumsplanerKey = Object.keys(resourceAccess).find((key) =>
      key.startsWith("praktikumsplaner")
    );

    const praktikumsplanRoles = praktikumsplanerKey
      ? (resourceAccess[praktikumsplanerKey]?.roles ?? [])
      : [];

    if (praktikumsplanRoles.length > 0) {
      userStore.setRoles(praktikumsplanRoles.map((role) => role.toUpperCase()));
    }
  });
});

onMounted(() => {
  getInfo().catch((error) => {
    snackbarStore.showMessage(error);
  });
});
</script>

<style scoped>
.no-underline,
.no-underline:hover {
  text-decoration: none;
}
</style>
