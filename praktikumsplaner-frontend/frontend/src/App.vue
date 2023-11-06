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
                            {{ header }}</v-toolbar-title
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
            <v-list>
                <v-list-item :to="{ path: '/getstarted' }">
                    <v-list-item-content>
                        <v-list-item-title>Get started</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item :to="{ path: '/excelimport' }">
                    <v-list-item-content>
                        <v-list-item-title
                            >Excel Datei hochladen</v-list-item-title
                        >
                    </v-list-item-content>
                </v-list-item>
                <v-list-item :to="{ path: '/meldezeitraum' }">
                    <v-list-item-content>
                        <v-list-item-title>Meldezeitraum</v-list-item-title>
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
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router/composables";
import { useSnackbarStore } from "@/stores/snackbar";
import TheSnackbar from "@/components/TheSnackbar.vue";
import { Store } from "@/Store";

const drawer = ref(true);
const query = ref("");
const route = useRoute();
const snackbarStore = useSnackbarStore();
const header = ref<string>("Praktikumsplaner");

onMounted(() => {
    /* eslint-disable  @typescript-eslint/no-explicit-any */
    query.value = route.params.query;
    InfoService.getInfo().catch((error) => {
        snackbarStore.showMessage(error);
    });
    /* eslint-enable  @typescript-eslint/no-explicit-any */
    changeHeader("Praktikumsplaner");
});

watch(
    () => route.params.query,
    (q: string) => {
        if (query.value !== q) {
            query.value = q;
        }
    }
);

function changeHeader(text: string) {
    header.value = text;
}

Store.$on("changeAppHeader", (payLoad: string) => {
    changeHeader(payLoad);
});
</script>

<style>
</style>
