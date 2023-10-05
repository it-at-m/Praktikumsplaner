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
                        <v-toolbar-title class="font-weight-bold">
                            <span class="white--text">RefArch-</span>
                            <span class="secondary--text">Kick</span>
                            <span class="white--text">Starter</span>
                        </v-toolbar-title>
                    </router-link>
                </v-col>
                <v-col
                    cols="6"
                    class="d-flex align-center justify-center"
                >
                    <v-text-field
                        id="suchfeld"
                        v-model="query"
                        flat
                        solo-inverted
                        hide-details
                        label="Suche"
                        clearable
                        prepend-inner-icon="mdi-magnify"
                        color="black"
                        @keyup.enter="search"
                    />
                </v-col>
                <v-col>
                    <ExcelImportNWK></ExcelImportNWK>
                </v-col>
                <v-col
                    cols="1"
                    class="d-flex align-center justify-end"
                >
                    <v-btn
                        text
                        fab
                    >
                        <v-icon class="white--text">
                            mdi-account-circle
                        </v-icon>
                    </v-btn>
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
import ExcelImportNWK from "@/components/ExcelImportNWK.vue";

const drawer = ref(true);
const query = ref("");
const route = useRoute();
const snackbarStore = useSnackbarStore();

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

//Navigiert zur Seite mit den Suchergebnissen und sendet ein Event zum Auslösen weiterer Suchen.
async function search(): Promise<void> {
    if (query.value !== "" && query.value !== null) {
        snackbarStore.showMessage({
            message: "Sie haben nach " + query.value + " gesucht. ;)",
        });
    }
}
</script>

<style>

</style>
