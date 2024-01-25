<template>
    <v-form
        ref="form"
        lazy-validation
    >
        <page-title
            back-button-url="/praktikumsplaetze"
            page-header-text="Praktikumsplatz für Studierende"
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
                            v-model="praktikumsstelle.dienststelle"
                        ></DienststellenInput>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <ReferatSelect
                            v-model="praktikumsstelle.referat"
                        ></ReferatSelect>
                    </v-col>
                    <v-col cols="1" />
                </v-row>
                <v-row>
                    <v-col>
                        <DringlichkeitSelect
                            v-model="praktikumsstelle.dringlichkeit"
                            :rules="requiredRule"
                        ></DringlichkeitSelect>
                    </v-col>
                    <v-col cols="2">
                        <DringlichkeitTooltip></DringlichkeitTooltip>
                    </v-col>
                    <v-col>
                        <v-text-field
                            v-model="zuweisungsZeitraum"
                            label="Zeitraum Nwk"
                            outlined
                            disabled
                            filled
                            background-color="grey"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="1" />
                </v-row>
                <v-row>
                    <v-col>
                        <PlanstelleRadioGroup
                            v-model="praktikumsstelle.planstelleVorhanden"
                        >
                        </PlanstelleRadioGroup>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <NamentlicheAnforderungInput
                            v-model="praktikumsstelle.namentlicheAnforderung"
                        >
                        </NamentlicheAnforderungInput>
                    </v-col>
                    <v-col cols="1">
                        <AnforderungTooltip></AnforderungTooltip>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col>
                        <TaetigkeitenInput
                            v-model="praktikumsstelle.taetigkeiten"
                        >
                        </TaetigkeitenInput>
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
                        <v-select
                            v-model="praktikumsstelle.studiengang"
                            label="Studienrichtung*"
                            :items="Studiengang"
                            item-value="value"
                            item-text="name"
                            :rules="requiredRule"
                            :menu-props="customMenuProps"
                            outlined
                            @change="changeVorrZuweisungsZeitraum"
                        >
                        </v-select>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <v-select
                            v-model="praktikumsstelle.studiensemester"
                            label="Studiensemester*"
                            :items="Studiensemester"
                            item-value="value"
                            item-text="name"
                            :rules="requiredRule"
                            :menu-props="customMenuProps"
                            outlined
                            @change="changeVorrZuweisungsZeitraum"
                        >
                        </v-select>
                    </v-col>
                    <v-col cols="1" />
                </v-row>
                <v-row>
                    <v-col>
                        <ProgrammierKenntnisseSelect
                            v-model="praktikumsstelle.programmierkenntnisse"
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
                            v-model="praktikumsstelle.oertlicheAusbilder"
                        >
                        </AusbilderInput>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <AusbilderEmailInput
                            v-model="praktikumsstelle.email"
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
                            v-model="praktikumsstelle.meldezeitraumID"
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
import { useRules } from "@/composables/rules";
import { useZeitraeume } from "@/composables/voraussichtlicherZuweisungsZeitraum";
import { Studiengang } from "@/types/Studiengang";
import { Studiensemester } from "@/types/Studiensemester";
import MeldungService from "@/api/PraktikumsstellenService";
import router from "@/router";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import PageTitle from "@/components/common/PageTitle.vue";
import "@/directives/security";
import { useUserStore } from "@/stores/user";
import { APP_SECURITY } from "@/constants";
import Meldezeitraum from "@/types/Meldezeitraum";
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import DringlichkeitSelect from "@/components/praktikumsplaetze/Meldung/DringlichkeitSelect.vue";
import DringlichkeitTooltip from "@/components/praktikumsplaetze/Meldung/DringlichkeitTooltip.vue";
import AnforderungTooltip from "@/components/praktikumsplaetze/Meldung/AnforderungTooltip.vue";
import ReferatSelect from "@/components/praktikumsplaetze/Meldung/ReferatSelect.vue";
import DienststellenInput from "@/components/praktikumsplaetze/Meldung/DienststellenInput.vue";
import PlanstelleRadioGroup from "@/components/praktikumsplaetze/Meldung/PlanstelleRadioGroup.vue";
import NamentlicheAnforderungInput from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungInput.vue";
import TaetigkeitenInput from "@/components/praktikumsplaetze/Meldung/TaetigkeitenInput.vue";
import ProgrammierKenntnisseSelect from "@/components/praktikumsplaetze/Meldung/ProgrammierKenntnisseSelect.vue";
import AusbilderInput from "@/components/praktikumsplaetze/Meldung/AusbilderInput.vue";
import AusbilderEmailInput from "@/components/praktikumsplaetze/Meldung/AusbilderEmailInput.vue";

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const zeitraeueme = useZeitraeume();
const zuweisungsZeitraum = ref<string>("");
const isAusbildungsleitung = ref<boolean>(false);
const userStore = useUserStore();
const validationRules = useRules();
const customMenuProps = {
    offsetY: true,
};
const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

const form = ref<HTMLFormElement>();
const meldezeitraeume = computed(() => {
    return [
        currentMeldezeitraum.value,
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

function changeVorrZuweisungsZeitraum() {
    zuweisungsZeitraum.value = zeitraeueme.studiumsZeitraum(
        praktikumsstelle.value.studiengang,
        praktikumsstelle.value.studiensemester
    );
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
</style>