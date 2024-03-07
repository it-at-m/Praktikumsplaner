<template>
    <v-container>
        <page-title
            back-button-url="/"
            page-header-text="Praktikumsplätze"
        ></page-title>
        <v-container v-if="loadingSite">
            <v-row>
                <v-col cols="10"></v-col>
                <v-col cols="2">
                    <v-skeleton-loader type="button"> </v-skeleton-loader>
                </v-col>
            </v-row>
        </v-container>
        <v-container v-else>
            <div v-if="canStellenBeSubmitted()">
                <v-row>
                    <v-col cols="10"></v-col>
                    <v-col cols="2">
                        <two-choice-dialog-cards
                            v-model="twoChoiceDialogVisible"
                            buttontext="Hinzufügen"
                            icontext="mdi-plus"
                            dialogtitle="Praktikumsplatz melden"
                            dialogsubtitle="Welche Art von Praktikumsplatz möchtest du melden?"
                            choice-one-title="Studium"
                            choice-one-subtitle="Praktikumsplatz für Studierende "
                            choice-two-title="Ausbildung"
                            choice-two-subtitle="Praktikumsplatz für Auszubildende"
                            @choice-one="toStudium"
                            @choice-two="toAusbildung"
                        ></two-choice-dialog-cards>
                    </v-col>
                </v-row>
                <v-row></v-row>
                <v-skeleton-loader
                    v-if="security.isAusbildungsleitung() && loadingUebersicht"
                    type="image"
                >
                </v-skeleton-loader>
                <v-row
                    v-if="security.isAusbildungsleitung() && !loadingUebersicht"
                >
                    <v-container
                        v-if="!mapIsEmpty"
                        class="box"
                    >
                        <span> Übersicht aus dem aktuellen Meldezeitraum </span>
                        <praktikumsstellen-list
                            :praktikumsstellen-map="praktikumsstellenMap"
                        ></praktikumsstellen-list>
                    </v-container>
                    <v-container
                        v-else
                        class="box"
                    >
                        <v-row class="align-center">
                            <v-col
                                cols="auto"
                                class="d-flex align-center"
                            >
                                <v-icon
                                    color="blue"
                                    size="large"
                                    >mdi-information-outline</v-icon
                                >
                            </v-col>
                            <v-col class="d-flex align-center">
                                <p>
                                    Es wurden für den aktuellen Zeitraum noch
                                    keine Praktikumsstellen gemeldet.
                                </p>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-row>
            </div>
            <div v-else>
                <kein-meldezeitraum-message></kein-meldezeitraum-message>
            </div>
        </v-container>
    </v-container>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import PageTitle from "@/components/common/PageTitle.vue";
import TwoChoiceDialogCards from "@/components/common/TwoChoiceDialogCards.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import PraktikumsstellenList from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstellenList.vue";
import { useSecurity } from "@/composables/security";
import router from "@/router";
import emitter from "@/stores/eventBus";
import { useUserStore } from "@/stores/user";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const userStore = useUserStore();
const activeMeldezeitraum = ref<boolean>(false);
const loadingUebersicht = ref<boolean>(false);
const loadingSite = ref<boolean>(true);
const security = useSecurity();
const twoChoiceDialogVisible = ref<boolean>(false);
const praktikumsstellenMap = ref<Map<string, Praktikumsstelle[]>>(
    new Map<string, Praktikumsstelle[]>()
);

const mapIsEmpty = computed(() => {
    return praktikumsstellenMap.value.size <= 0 || false;
});
const route = router.currentRoute.value;

onMounted(() => {
    loadingUebersicht.value = true;
    MeldezeitraumService.getCurrentMeldezeitraum(loadingUebersicht).then(
        (zeitraueme) => {
            activeMeldezeitraum.value = zeitraueme.length > 0;
        }
    );
    getAllPraktikumsstellenInCurrentMeldezeitraum();

    if (userStore.username !== undefined) {
        redirectIfUnauthorized();
    } else {
        // This Watcher is responsible for redirecting the user to the AccessDenied view if his roles do not suffice
        watch(
            () => userStore.roles,
            () => {
                redirectIfUnauthorized();
            }
        );
    }
});

function redirectIfUnauthorized() {
    const requiresRoles =
        route.meta.requiresRole != undefined
            ? (route.meta.requiresRole as string[])
            : undefined;
    const security = useSecurity();
    if (
        requiresRoles !== undefined &&
        !security.checkForAnyRole(requiresRoles)
    ) {
        router.push("/AccessDenied");
    }
}

emitter.on("nwkDeleted", getAllPraktikumsstellenInCurrentMeldezeitraum);

function canStellenBeSubmitted() {
    return security.isAusbildungsleitung() || activeMeldezeitraum.value;
}
function toAusbildung(): void {
    router.push("/praktikumsplaetze/meldungAusbildung");
}
function toStudium(): void {
    router.push("/praktikumsplaetze/meldungStudium");
}

function getAllPraktikumsstellenInCurrentMeldezeitraum() {
    const helperMap = new Map<string, Praktikumsstelle[]>();
    PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
        "current",
        loadingSite
    ).then((fetchedStellen) => {
        for (const [key, value] of Object.entries(fetchedStellen)) {
            helperMap.set(key, value);
        }
        praktikumsstellenMap.value = helperMap;
    });
}
</script>
<style>
.box {
    margin: 1%;
    border: 2px solid #0000001a;
}
</style>
