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
                    v-if="isAusbildungsleitung && loadingUebersicht"
                    type="image"
                >
                </v-skeleton-loader>
                <v-row v-if="isAusbildungsleitung && !loadingUebersicht">
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
import { computed, onMounted, ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import PageTitle from "@/components/common/PageTitle.vue";
import TwoChoiceDialogCards from "@/components/common/TwoChoiceDialogCards.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import PraktikumsstellenList from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstellenList.vue";
import { APP_SECURITY } from "@/constants";
import index from "@/router";
import { useUserStore } from "@/stores/user";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const userStore = useUserStore();
const activeMeldezeitraum = ref<boolean>(false);
const loadingUebersicht = ref<boolean>(false);
const loadingSite = ref<boolean>(true);
const isAusbildungsleitung = computed(() => {
    return (
        userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
        APP_SECURITY !== "true"
    );
});
const twoChoiceDialogVisible = ref<boolean>(false);
const praktikumsstellenMap = ref<Map<string, Praktikumsstelle[]>>(
    new Map<string, Praktikumsstelle[]>()
);

const mapIsEmpty = computed(() => {
    return praktikumsstellenMap.value.size <= 0 || false;
});

onMounted(() => {
    loadingUebersicht.value = true;
    MeldezeitraumService.getCurrentMeldezeitraum()
        .then((zeitraueme) => {
            activeMeldezeitraum.value = zeitraueme.length > 0;
        })
        .finally(() => {
            loadingSite.value = false;
        });
    getAllPraktikumsstellenInCurrentMeldezeitraum();
});

function canStellenBeSubmitted() {
    return isAusbildungsleitung.value || activeMeldezeitraum.value;
}
function toAusbildung(): void {
    index.push("/praktikumsplaetze/meldungAusbildung");
}
function toStudium(): void {
    index.push("/praktikumsplaetze/meldungStudium");
}

function getAllPraktikumsstellenInCurrentMeldezeitraum() {
    const helperMap = new Map<string, Praktikumsstelle[]>();
    PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
        "current"
    )
        .then((fetchedStellen) => {
            for (const [key, value] of Object.entries(fetchedStellen)) {
                helperMap.set(key, value);
            }
            praktikumsstellenMap.value = helperMap;
        })
        .finally(() => {
            loadingUebersicht.value = false;
        });
}
</script>
<style>
.box {
    margin: 1%;
    border: 2px solid #0000001a;
}
</style>
