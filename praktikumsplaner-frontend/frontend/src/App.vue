<template>
    <v-app>
        <the-snackbar />
        <v-app-bar color="primary">
            <v-row align="center">
                <v-col
                    cols="3"
                    class="d-flex align-center justify-start"
                >
                    <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
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
                    v-if="
                        security.checkForAnyRole([
                            'ROLE_AUSBILDER',
                            'ROLE_AUSBILDUNGSLEITUNG',
                        ])
                    "
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
            <v-container fluid>
                <router-view v-slot="{ Component }">
                    <v-fade-transition mode="out-in">
                        <component :is="Component" />
                    </v-fade-transition>
                </router-view>
            </v-container>
        </v-main>
    </v-app>
</template>

<script setup lang="ts">
import { onBeforeMount, onMounted, ref } from "vue";

import InfoService from "@/api/InfoService";
import { UserService } from "@/api/UserService";
import TheSnackbar from "@/components/TheSnackbar.vue";
import { useSecurity } from "@/composables/security";
import { useSnackbarStore } from "@/stores/snackbar";
import { useUserStore } from "@/stores/user";

const drawer = ref(true);
const userStore = useUserStore();
const snackbarStore = useSnackbarStore();
const userService = new UserService();
const security = useSecurity();

onBeforeMount(() => {
    userService.getPermissions().then((userinfo) => {
        userStore.setUsername(userinfo.name);
        if (userinfo.user_roles) {
            userStore.setRoles(userinfo.user_roles);
        }
    });
});

onMounted(() => {
    InfoService.getInfo().catch((error) => {
        snackbarStore.showMessage(error);
    });
});
</script>

<style scoped>
.v-navigation-drawer .v-list-item-title {
    font-size: 16px;
    padding-bottom: 1px;
}
.no-underline,
.no-underline:hover {
    text-decoration: none;
}
</style>
