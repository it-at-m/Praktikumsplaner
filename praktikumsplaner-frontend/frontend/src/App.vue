<template>
    <v-app>
        <the-snackbar />
        <v-app-bar
            app
            clipped-left
            dark
            color="primary"
        >
            <v-row align="center">
                <v-col
                    cols="3"
                    class="d-flex align-center justify-start"
                >
                    <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
                    <router-link to="/">
                        <v-toolbar-title class="white--text">
                            Praktikumsplaner</v-toolbar-title
                        >
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
        <v-navigation-drawer
            v-model="drawer"
            app
            clipped
        >
            <v-list nav>
                <v-list-item
                    v-security.allow="['ROLE_AUSBILDUNGSLEITUNG']"
                    :to="{ path: '/nachwuchskraefte' }"
                >
                    <v-list-item-content>
                        <v-list-item-title>Nachwuchskräfte</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item
                    v-security.allow="['ROLE_AUSBILDUNGSLEITUNG']"
                    :to="{ path: '/meldezeitraum' }"
                >
                    <v-list-item-content>
                        <v-list-item-title>Meldezeitraum</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item
                    v-security.restrict="['ROLE_NWK']"
                    :to="{ path: '/praktikumsplaetze' }"
                >
                    <v-list-item-content>
                        <v-list-item-title>Praktikumsplätze</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item
                    v-security.allow="['ROLE_AUSBILDUNGSLEITUNG']"
                    :to="{ path: '/zuweisung' }"
                >
                    <v-list-item-content>
                        <v-list-item-title>Zuweisung</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <v-main>
            <v-container fluid>
                <v-fade-transition mode="out-in">
                    <router-view />
                </v-fade-transition>
            </v-container>
        </v-main>
    </v-app>
</template>

<script setup lang="ts">
import InfoService from "@/api/InfoService";
import { onBeforeMount, onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router/composables";
import { useSnackbarStore } from "@/stores/snackbar";
import TheSnackbar from "@/components/TheSnackbar.vue";
import { UserService } from "@/api/UserService";
import { useUserStore } from "@/stores/user";
import "@/directives/Security";

const drawer = ref(true);
const query = ref("");
const route = useRoute();
const snackbarStore = useSnackbarStore();
const userService = new UserService();
const userStore = useUserStore();

onBeforeMount(() => {
    userService.getPermissions().then((userinfo) => {
        userStore.setUsername(userinfo.name);
        if (userinfo.user_roles) {
            userStore.setRoles(userinfo.user_roles);
        }
    });
});

onMounted(() => {
    /* eslint-disable  @typescript-eslint/no-explicit-any */
    query.value = route.params.query;
    InfoService.getInfo().catch((error) => {
        snackbarStore.showMessage(error);
    });
    /* eslint-enable  @typescript-eslint/no-explicit-any */
});

watch(
    () => route.params.query,
    (q: string) => {
        if (query.value !== q) {
            query.value = q;
        }
    }
);
</script>

<style>
</style>
