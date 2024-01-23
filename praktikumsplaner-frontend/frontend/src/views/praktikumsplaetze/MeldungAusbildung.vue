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
                        <v-text-field
                            v-model="praktikumsstelle.dienststelle"
                            label="Konkrete Dienststelle*"
                            :rules="dienststelleRule"
                            outlined
                        ></v-text-field>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <v-select
                            v-model="praktikumsstelle.referat"
                            :items="Referat"
                            :menu-props="customMenuProps"
                            item-value="value"
                            item-text="name"
                            label="Referat"
                            outlined
                        ></v-select>
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
                        <v-tooltip right>
                            <template #activator="{ on, attrs }">
                                <v-icon
                                    color="blue"
                                    class="v-tooltip-spacing"
                                    v-bind="attrs"
                                    large
                                    v-on="on"
                                >
                                    mdi-information
                                </v-icon>
                            </template>
                            <span
                                >Die Dringlichkeit gibt an, wie hoch priorisiert
                                der Praktikumsplatz zu besetzen ist.</span
                            >
                        </v-tooltip>
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
                        <v-radio-group
                            v-model="praktikumsstelle.planstelleVorhanden"
                            class="radios custom-label"
                            row
                            :rules="booleanRule"
                        >
                            <template #label>
                                <span class="custom-label"
                                    >Planstelle vorhanden*:</span
                                >
                            </template>
                            <v-radio
                                v-for="item in YesNoBoolean"
                                :key="item.value"
                                :label="item.name"
                                :value="item.value"
                                class="ml-5"
                            ></v-radio>
                        </v-radio-group>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <v-radio-group
                            v-model="praktikumsstelle.projektarbeit"
                            class="radios custom-label"
                            row
                            :rules="booleanRule"
                        >
                            <template #label>
                                <span class="custom-label"
                                    >Projektarbeit*:</span
                                >
                            </template>
                            <v-radio
                                v-for="item in YesNoBoolean"
                                :key="item.value"
                                :label="item.name"
                                :value="item.value"
                                class="ml-5"
                            ></v-radio>
                        </v-radio-group>
                    </v-col>
                    <v-col cols="1">
                        <v-tooltip right>
                            <template #activator="{ on, attrs }">
                                <v-icon
                                    color="blue"
                                    class="v-tooltip-spacing"
                                    v-bind="attrs"
                                    large
                                    v-on="on"
                                >
                                    mdi-information
                                </v-icon>
                            </template>
                            <span
                                >Die Projektarbeit ist die Abschlussarbeit der
                                Auszubildenden</span
                            >
                        </v-tooltip>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col>
                        <v-text-field
                            v-model="praktikumsstelle.namentlicheAnforderung"
                            label="Anforderung bestimmter NWK"
                            :rules="namentlicheAnforderungRule"
                            outlined
                        ></v-text-field>
                    </v-col>
                    <v-col cols="1">
                        <v-tooltip right>
                            <template #activator="{ on, attrs }">
                                <v-icon
                                    color="blue"
                                    class="v-tooltip-spacing"
                                    v-bind="attrs"
                                    large
                                    v-on="on"
                                >
                                    mdi-information
                                </v-icon>
                            </template>
                            <span
                                >Bei Anforderung einer bestimmten NWK für die
                                Stelle, hier den vollständigen Namen
                                eintragen.</span
                            >
                        </v-tooltip>
                    </v-col>
                    <v-col cols="2"></v-col>
                    <v-col></v-col>
                </v-row>
                <v-row>
                    <v-col>
                        <v-textarea
                            v-model="praktikumsstelle.taetigkeiten"
                            label="Aufgaben am Praktikumsplatz*"
                            :rules="taetigkeitenRule"
                            outlined
                        ></v-textarea>
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
                            v-model="praktikumsstelle.ausbildungsrichtung"
                            label="Ausbildungsrichtung*"
                            :items="Ausbildungsrichtung"
                            item-value="value"
                            item-text="name"
                            :rules="requiredRule"
                            :menu-props="customMenuProps"
                            outlined
                            @change="
                                () => {
                                    changeVorrZuweisungsZeitraum();
                                }
                            "
                        >
                        </v-select>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <v-select
                            v-model="praktikumsstelle.ausbildungsjahr"
                            label="Ausbildungsjahr*"
                            :items="Ausbildungsjahr"
                            item-value="value"
                            item-text="name"
                            :rules="requiredRule"
                            :menu-props="customMenuProps"
                            outlined
                            @change="
                                () => {
                                    changeVorrZuweisungsZeitraum();
                                }
                            "
                        >
                        </v-select>
                    </v-col>
                    <v-col cols="1" />
                </v-row>
                <v-row>
                    <v-col>
                        <v-select
                            v-model="praktikumsstelle.programmierkenntnisse"
                            label="Programmierkenntnisse"
                            :items="Programmierkenntnisse"
                            :menu-props="customMenuProps"
                            item-value="value"
                            item-text="name"
                            outlined
                        >
                        </v-select>
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
                        <v-text-field
                            v-model="praktikumsstelle.oertlicheAusbilder"
                            label="Name örtliche Ausbilder*in*"
                            :rules="oertlAusbidlerRule"
                            outlined
                        ></v-text-field>
                    </v-col>
                    <v-col cols="2" />
                    <v-col>
                        <v-text-field
                            v-model="praktikumsstelle.email"
                            label="E-mail örtliche Ausbilder*in*"
                            :rules="emailRule"
                            outlined
                        ></v-text-field>
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
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import { onMounted, ref, computed } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useRules } from "@/composables/rules";
import { useZeitraeume } from "@/composables/voraussichtlicherZuweisungsZeitraum";
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";
import { Referat } from "@/types/Referat";
import { YesNoBoolean } from "@/types/YesNoBoolean";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import MeldungService from "@/api/PraktikumsstellenService";
import router from "@/router";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import PageTitle from "@/components/common/PageTitle.vue";
import { Programmierkenntnisse } from "@/types/YesNoEgalBoolean";
import { APP_SECURITY } from "@/constants";
import { useUserStore } from "@/stores/user";
import Meldezeitraum from "@/types/Meldezeitraum";
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const zeitraeueme = useZeitraeume();
const zuweisungsZeitraum = ref<string>("");
const isAusbildungsleitung = ref<boolean>(false);
const userStore = useUserStore();
const validationRules = useRules();
const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];
const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.emailRule("Keine gültige Email."),
    validationRules.maxLengthRule(
        255,
        "Die Email darf nicht länger als 255 Zeichen sein."
    ),
];
const booleanRule = [
    validationRules.notEmptyBooleanRule("Darf nicht leer sein."),
];
const dienststelleRule = [
    validationRules.notEmptyRule("Darf nicht leer sein"),
    validationRules.maxLengthRule(
        10,
        "Die Dienststelle darf nicht länger als 10 Zeichen sein."
    ),
];
const oertlAusbidlerRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        255,
        "Örtliche Ausbilder*in darf nicht länger als 255 Zeichen sein."
    ),
];
const namentlicheAnforderungRule = [
    validationRules.maxLengthRule(
        255,
        "Der Name der angeforderte Nwk darf nicht länger als 255 Zeichen sein."
    ),
];
const taetigkeitenRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        5000,
        "Tätigkeiten dürfen nicht länger als 5000 Zeichen sein."
    ),
];

const customMenuProps = {
    offsetY: true,
};
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
    zuweisungsZeitraum.value = zeitraeueme.ausbildungsZeitraum(
        praktikumsstelle.value.ausbildungsrichtung,
        praktikumsstelle.value.ausbildungsjahr
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