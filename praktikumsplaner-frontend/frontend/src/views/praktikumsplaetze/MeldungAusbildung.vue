<template>
    <v-form
        ref="form"
        lazy-validation
    >
        <page-title
            back-button-url="/praktikumsplaetze"
            page-header-text="Praktikumsplatz für Auszubildende"
        ></page-title>
        <div v-if="!activeMeldezeitraum">
            <KeinMeldezeitraumMessage></KeinMeldezeitraumMessage>
        </div>
        <div v-else>
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
                    <v-col> </v-col>
                    <v-col cols="1" />
                </v-row>
                <v-row>
                    <v-col>
                        <PlanstelleRadioGroup
                            v-model="praktikumsstelle"
                        ></PlanstelleRadioGroup>
                    </v-col>
                    <v-col cols="2" />
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
                        <ProjektarbeitRadioGroup
                            v-model="praktikumsstelle"
                        ></ProjektarbeitRadioGroup>
                    </v-col>
                    <v-col cols="1">
                        <ProjektarbeitTooltip></ProjektarbeitTooltip>
                    </v-col>
                    <v-col cols="2"></v-col>
                    <v-col></v-col>
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
                        <AusbildungsrichtungSelect
                            v-model="praktikumsstelle"
                        ></AusbildungsrichtungSelect>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <AusbildungsJahrSelect
                            v-model="praktikumsstelle"
                        ></AusbildungsJahrSelect>
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
        </div>
    </v-form>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import MeldungService from "@/api/PraktikumsstellenService";
import router from "@/router";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import PageTitle from "@/components/common/PageTitle.vue";
import { APP_SECURITY } from "@/constants";
import { useUserStore } from "@/stores/user";
import Meldezeitraum from "@/types/Meldezeitraum";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import DienststellenInput from "@/components/praktikumsplaetze/Meldung/DienststellenInput.vue";
import ReferatSelect from "@/components/praktikumsplaetze/Meldung/ReferatSelect.vue";
import DringlichkeitSelect from "@/components/praktikumsplaetze/Meldung/DringlichkeitSelect.vue";
import DringlichkeitTooltip from "@/components/praktikumsplaetze/Meldung/DringlichkeitTooltip.vue";
import PlanstelleRadioGroup from "@/components/praktikumsplaetze/Meldung/PlanstelleRadioGroup.vue";
import ProjektarbeitRadioGroup from "@/components/praktikumsplaetze/Meldung/ProjektarbeitRadioGroup.vue";
import ProjektarbeitTooltip from "@/components/praktikumsplaetze/Meldung/ProjektarbeitTooltip.vue";
import NamentlicheAnforderungInput from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungInput.vue";
import TaetigkeitenInput from "@/components/praktikumsplaetze/Meldung/TaetigkeitenInput.vue";
import AusbildungsrichtungSelect from "@/components/praktikumsplaetze/Meldung/AusbildungsrichtungSelect.vue";
import ProgrammierKenntnisseSelect from "@/components/praktikumsplaetze/Meldung/ProgrammierKenntnisseSelect.vue";
import AusbilderInput from "@/components/praktikumsplaetze/Meldung/AusbilderInput.vue";
import AusbilderEmailInput from "@/components/praktikumsplaetze/Meldung/AusbilderEmailInput.vue";
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import AusbildungsJahrSelect from "@/components/praktikumsplaetze/Meldung/AusbildungsJahrSelect.vue";
import NamentlicheAnforderungTooltip from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungTooltip.vue";
import { useSecurity } from "@/composables/security";

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const isAusbildungsleitung = ref<boolean>(false);
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
const security = useSecurity();

onMounted(() => {
    MeldezeitraumService.getCurrentMeldezeitraum()
        .then((zeitraueme) => {
            activeMeldezeitraum.value = zeitraueme.length > 0;
            currentMeldezeitraum.value = zeitraueme[0];
        })
        .then(() => {
            if (
                userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
                APP_SECURITY !== "true"
            ) {
                isAusbildungsleitung.value = true;
                activeMeldezeitraum.value = true;
                getUpcomingMeldezeitraeume();
                getPassedMeldezeitraeume();
            }
        });
    console.log(security.checkRoles("ROLE_AUSBILDUNGSLEITUNG"));
});

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
    router.push("/praktikumsplaetze");
}
function uploadPraktikumsstelle() {
    if (!form.value?.validate()) return;
    if (
        userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
        APP_SECURITY !== "true"
    ) {
        MeldungService.uploadAusbildungsPraktikumsstelleWithMeldezeitraum(
            praktikumsstelle.value
        ).finally(() => {
            resetForm();
        });
    } else {
        MeldungService.uploadAusbildungsPraktikumsstelle(
            praktikumsstelle.value
        ).finally(() => {
            resetForm();
        });
    }
}
</script>
<style lang="scss">
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
.custom-label .v-label {
    font-size: 18px !important;
}
</style>