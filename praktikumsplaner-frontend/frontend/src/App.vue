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
                        <v-img
                            max-height="50"
                            max-width="50"
                            contain
                            src="src/assets/logo.png"
                        ></v-img>
                    </router-link>
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
                    v-if="security.isAusbildungsleitung()"
                    :to="{ path: '/nachwuchskraefte' }"
                >
                    <v-list-item-content>
                        <v-list-item-title>Nachwuchskräfte</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item
                    v-if="security.isAusbildungsleitung()"
                    :to="{ path: '/meldezeitraum' }"
                >
                    <v-list-item-content>
                        <v-list-item-title>Meldezeitraum</v-list-item-title>
                    </v-list-item-content>
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
                    <v-list-item-content>
                        <v-list-item-title>Praktikumsplätze</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item
                    v-if="security.isAusbildungsleitung()"
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
import "@/directives/security";
import { useSecurity } from "@/composables/security";

const drawer = ref(true);
const query = ref("");
const userStore = useUserStore();
const route = useRoute();
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
