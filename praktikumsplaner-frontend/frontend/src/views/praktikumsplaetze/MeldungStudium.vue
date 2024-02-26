<template>
    <v-container>
        <page-title
            back-button-url="/praktikumsplaetze"
            page-header-text="Praktikumsplatz für Studierende"
        ></page-title>
        <v-container v-if="loadingSite">
            <v-skeleton-loader type="image"> </v-skeleton-loader>
            <v-spacer />
            <v-skeleton-loader type="image"> </v-skeleton-loader>
            <v-spacer />
            <v-skeleton-loader type="image"> </v-skeleton-loader>
            <v-spacer />
            <v-row>
                <v-col cols="10" />
                <v-col>
                    <v-skeleton-loader type="button"> </v-skeleton-loader>
                </v-col>
            </v-row>
        </v-container>
        <v-container v-else>
            <div v-if="!activeMeldezeitraum"></div>
            <v-form
                v-if="canStellenBeSubmitted()"
                ref="form"
                lazy-validation
            >
                <v-container class="box">
                    <v-row>
                        <v-col>
                            <span class="text-h6">Stellenbeschreibung</span>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <dienststellen-input
                                v-model="praktikumsstelle"
                            ></dienststellen-input>
                        </v-col>
                        <v-col cols="2" />
                        <v-col>
                            <referat-select
                                v-model="praktikumsstelle"
                                :is-required="false"
                            ></referat-select>
                        </v-col>
                        <v-col cols="1" />
                    </v-row>
                    <v-row>
                        <v-col>
                            <dringlichkeit-select
                                v-model="praktikumsstelle"
                            ></dringlichkeit-select>
                        </v-col>
                        <v-col cols="2">
                            <dringlichkeit-tooltip></dringlichkeit-tooltip>
                        </v-col>
                        <v-col>
                            <namentliche-anforderung-input
                                v-model="praktikumsstelle"
                            ></namentliche-anforderung-input>
                        </v-col>
                        <v-col cols="1">
                            <namentliche-anforderung-tooltip></namentliche-anforderung-tooltip>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <planstelle-radio-group
                                v-model="praktikumsstelle"
                            ></planstelle-radio-group>
                        </v-col>
                        <v-col cols="2" />
                        <v-col> </v-col>
                        <v-col cols="1" />
                    </v-row>
                    <v-row>
                        <v-col>
                            <taetigkeiten-input
                                v-model="praktikumsstelle"
                                :is-required="true"
                                :required-symbol="requiredFieldSymbol"
                            >
                            </taetigkeiten-input>
                        </v-col>
                        <v-col cols="1" />
                    </v-row>
                </v-container>
                <v-container class="box">
                    <v-row>
                        <v-col>
                            <span class="text-h6">Nachwuchskraft</span>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <studienrichtung-select
                                v-model="praktikumsstelle"
                                :is-required="true"
                                :required-symbol="requiredFieldSymbol"
                            ></studienrichtung-select>
                        </v-col>
                        <v-col cols="2" />
                        <v-col>
                            <semester-select
                                v-model="praktikumsstelle"
                                :is-required="true"
                                :required-symbol="requiredFieldSymbol"
                            ></semester-select>
                        </v-col>
                        <v-col cols="1" />
                    </v-row>
                    <v-row>
                        <v-col>
                            <programmier-kenntnisse-select
                                v-model="praktikumsstelle"
                                :is-required="true"
                                :required-symbol="requiredFieldSymbol"
                            ></programmier-kenntnisse-select>
                        </v-col>
                        <v-col />
                        <v-col cols="3" />
                    </v-row>
                </v-container>
                <v-container class="box">
                    <v-row>
                        <v-col>
                            <span class="text-h6">örtliche*r Ausbilder*in</span>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <ausbilder-input
                                v-model="praktikumsstelle"
                            ></ausbilder-input>
                        </v-col>
                        <v-col cols="2" />
                        <v-col>
                            <ausbilder-email-input
                                v-model="praktikumsstelle"
                            ></ausbilder-email-input>
                        </v-col>
                        <v-col cols="1" />
                    </v-row>
                </v-container>
                <v-container
                    v-if="isAusbildungsleitung"
                    class="box"
                >
                    <v-row>
                        <v-col>
                            <span class="text-h6">Meldezeitraum Auswahl</span>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <meldezeitraum-select
                                v-model="praktikumsstelle"
                                :meldezeitraueme="meldezeitraeume"
                            ></meldezeitraum-select>
                        </v-col>
                        <v-col cols="2" />
                        <v-col> </v-col>
                        <v-col cols="1" />
                    </v-row>
                </v-container>
                <v-container>
                    <v-row>
                        <v-col cols="10" />
                        <v-col>
                            <v-btn
                                color="primary"
                                @click="uploadPraktikumsstelle"
                            >
                                speichern
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-container>
            </v-form>
            <kein-meldezeitraum-message v-else></kein-meldezeitraum-message>
        </v-container>
        <progress-circular-overlay
            :loading="loading"
        ></progress-circular-overlay>
    </v-container>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import MeldungService from "@/api/PraktikumsstellenService";
import PageTitle from "@/components/common/PageTitle.vue";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import AusbilderEmailInput from "@/components/praktikumsplaetze/Meldung/AusbilderEmailInput.vue";
import AusbilderInput from "@/components/praktikumsplaetze/Meldung/AusbilderInput.vue";
import DienststellenInput from "@/components/praktikumsplaetze/Meldung/DienststellenInput.vue";
import DringlichkeitSelect from "@/components/praktikumsplaetze/Meldung/DringlichkeitSelect.vue";
import DringlichkeitTooltip from "@/components/praktikumsplaetze/Meldung/DringlichkeitTooltip.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import NamentlicheAnforderungInput from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungInput.vue";
import NamentlicheAnforderungTooltip from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungTooltip.vue";
import PlanstelleRadioGroup from "@/components/praktikumsplaetze/Meldung/PlanstelleRadioGroup.vue";
import ProgrammierKenntnisseSelect from "@/components/praktikumsplaetze/Meldung/ProgrammierKenntnisseSelect.vue";
import ReferatSelect from "@/components/praktikumsplaetze/Meldung/ReferatSelect.vue";
import StudienrichtungSelect from "@/components/praktikumsplaetze/Meldung/StudienrichtungSelect.vue";
import SemesterSelect from "@/components/praktikumsplaetze/Meldung/StudiensemesterSelect.vue";
import TaetigkeitenInput from "@/components/praktikumsplaetze/Meldung/TaetigkeitenInput.vue";
import { APP_SECURITY } from "@/constants";
import index from "@/router";
import { useUserStore } from "@/stores/user";
import Meldezeitraum from "@/types/Meldezeitraum";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const requiredFieldSymbol = "*";

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(new Praktikumsstelle());
const loadingSite = ref<boolean>(true);
const isAusbildungsleitung = computed(
    () =>
        userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
        APP_SECURITY !== "true"
);
const loading = ref<boolean>(false);
const userStore = useUserStore();

const form = ref<HTMLFormElement>();
const meldezeitraeume = computed(() => {
    let list: Meldezeitraum[] = [];
    if (currentMeldezeitraum.value) {
        list = [currentMeldezeitraum.value];
    }
    return [
        ...list,
        ...upcomingMeldezeitraeume.value,
        ...passedMeldezeitraeume.value,
    ];
});
const currentMeldezeitraum = ref<Meldezeitraum>();
const upcomingMeldezeitraeume = ref<Meldezeitraum[]>([]);
const passedMeldezeitraeume = ref<Meldezeitraum[]>([]);

onMounted(() => {
    MeldezeitraumService.getCurrentMeldezeitraum()
        .then((zeitraueme) => {
            currentMeldezeitraum.value = zeitraueme[0];
        })
        .finally(() => {
            loadingSite.value = false;
        });

    if (isAusbildungsleitung.value) {
        getUpcomingMeldezeitraeume();
        getPassedMeldezeitraeume();
    }
});

function canStellenBeSubmitted() {
    return isAusbildungsleitung.value || currentMeldezeitraum.value;
}

function getUpcomingMeldezeitraeume() {
    MeldezeitraumService.getUpcomingMeldezeitraueme().then((zeitraeume) => {
        upcomingMeldezeitraeume.value = zeitraeume;
    });
}

function getPassedMeldezeitraeume() {
    MeldezeitraumService.getPassedMeldezeitraueme().then((zeitraeume) => {
        passedMeldezeitraeume.value = zeitraeume;
    });
}

function resetForm() {
    form.value?.reset();
    index.push("/praktikumsplaetze");
}

function uploadPraktikumsstelle() {
    form.value?.validate();
    if (!form.value?.isValid) return;
    loading.value = true;
    if (isAusbildungsleitung.value) {
        MeldungService.uploadStudiumsPraktikumsstelleWithMeldezeitraum(
            praktikumsstelle.value
        ).finally(() => {
            loading.value = false;
            resetForm();
        });
    } else {
        MeldungService.uploadStudiumsPraktikumsstelle(
            praktikumsstelle.value
        ).finally(() => {
            loading.value = false;
            resetForm();
        });
    }
}
</script>
<style>
.box {
    margin: 3%;
    border: 2px solid #0000001a;
    border-radius: 5px;
}
</style>
