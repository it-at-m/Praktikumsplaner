<template>
    <v-container>
        <v-container v-if="loadingSite">
            <v-row>
                <v-col cols="1">
                    <v-skeleton-loader type="button"> </v-skeleton-loader>
                </v-col>
                <v-col cols="2">
                    <v-skeleton-loader type="text"> </v-skeleton-loader>
                </v-col>
                <v-col cols="9" />
            </v-row>
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
            <page-title
                back-button-url="/praktikumsplaetze"
                page-header-text="Praktikumsplatz für Studierende"
            ></page-title>
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
                            <DienststellenInput
                                v-model="praktikumsstelle"
                            ></DienststellenInput>
                        </v-col>
                        <v-col cols="2" />
                        <v-col>
                            <ReferatSelect
                                v-model="praktikumsstelle"
                            ></ReferatSelect>
                        </v-col>
                        <v-col cols="1" />
                    </v-row>
                    <v-row>
                        <v-col>
                            <DringlichkeitSelect
                                v-model="praktikumsstelle"
                            ></DringlichkeitSelect>
                        </v-col>
                        <v-col cols="2">
                            <DringlichkeitTooltip></DringlichkeitTooltip>
                        </v-col>
                        <v-col>
                            <NamentlicheAnforderungInput
                                v-model="praktikumsstelle"
                            ></NamentlicheAnforderungInput>
                        </v-col>
                        <v-col cols="1">
                            <NamentlicheAnforderungTooltip></NamentlicheAnforderungTooltip>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <PlanstelleRadioGroup
                                v-model="praktikumsstelle"
                            ></PlanstelleRadioGroup>
                        </v-col>
                        <v-col cols="2" />
                        <v-col> </v-col>
                        <v-col cols="1" />
                    </v-row>
                    <v-row>
                        <v-col>
                            <TaetigkeitenInput
                                v-model="praktikumsstelle"
                            ></TaetigkeitenInput>
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
                            <StudienrichtungSelect
                                v-model="praktikumsstelle"
                            ></StudienrichtungSelect>
                        </v-col>
                        <v-col cols="2" />
                        <v-col>
                            <SemesterSelect
                                v-model="praktikumsstelle"
                            ></SemesterSelect>
                        </v-col>
                        <v-col cols="1" />
                    </v-row>
                    <v-row>
                        <v-col>
                            <ProgrammierKenntnisseSelect
                                v-model="praktikumsstelle"
                            ></ProgrammierKenntnisseSelect>
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
                            <AusbilderInput
                                v-model="praktikumsstelle"
                            ></AusbilderInput>
                        </v-col>
                        <v-col cols="2" />
                        <v-col>
                            <AusbilderEmailInput
                                v-model="praktikumsstelle"
                            ></AusbilderEmailInput>
                        </v-col>
                        <v-col cols="1" />
                    </v-row>
                </v-container>
                <v-container
                    v-show="isAusbildungsleitung"
                    class="box"
                >
                    <v-row>
                        <v-col>
                            <span class="text-h6">Meldezeitraum Auswahl</span>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <MeldezeitraumSelect
                                v-model="praktikumsstelle"
                                :meldezeitraueme="meldezeitraeume"
                            ></MeldezeitraumSelect>
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
            <KeinMeldezeitraumMessage v-else></KeinMeldezeitraumMessage>
        </v-container>
    </v-container>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import MeldungService from "@/api/PraktikumsstellenService";
import PageTitle from "@/components/common/PageTitle.vue";
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

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const loadingSite = ref<boolean>(true);
const isAusbildungsleitung = computed(
    () =>
        userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
        APP_SECURITY !== "true"
);
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

    if (isAusbildungsleitung) {
        getUpcomingMeldezeitraeume();
        getPassedMeldezeitraeume();
    }
});

function canStellenBeSubmitted() {
    return (
        userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
        APP_SECURITY !== "true" ||
        currentMeldezeitraum.value
    );
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
    if (!form.value?.validate()) return;
    if (
        userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
        APP_SECURITY !== "true"
    ) {
        MeldungService.uploadStudiumsPraktikumsstelleWithMeldezeitraum(
            praktikumsstelle.value
        ).finally(() => {
            resetForm();
        });
    } else {
        MeldungService.uploadStudiumsPraktikumsstelle(
            praktikumsstelle.value
        ).finally(() => {
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
.v-tooltip-spacing {
    margin-top: 10px;
}
.radios {
    margin: 2%;
}
</style>
