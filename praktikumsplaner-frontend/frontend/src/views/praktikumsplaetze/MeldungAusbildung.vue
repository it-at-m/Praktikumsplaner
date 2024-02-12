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
                page-header-text="Praktikumsplatz für Auszubildende"
            ></page-title>
            <div v-if="!activeMeldezeitraum">
                <v-row class="align-center">
                    <v-col
                        cols="auto"
                        class="d-flex align-center"
                    >
                        <v-icon
                            color="blue"
                            large
                            >mdi-information-outline</v-icon
                        >
                    </v-col>
                    <v-col class="d-flex align-center">
                        <p class="mt-5">
                            Ihre örtliche Ausbildungsleitung hat die Meldung von
                            Stellen noch nicht freigegeben, daher können aktuell
                            leider noch keine Praktikumsplätze gemeldet werden.
                        </p>
                    </v-col>
                </v-row>
            </div>
            <v-form
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
                            <v-select
                                v-model="praktikumsstelle.dringlichkeit"
                                label="Dringlichkeit*"
                                :items="Dringlichkeit"
                                :menu-props="customMenuProps"
                                item-value="value"
                                item-text="name"
                                :rules="requiredRule"
                                outlined
                            ></v-select>
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
                                    >Die Dringlichkeit gibt an, wie hoch
                                    priorisiert der Praktikumsplatz zu besetzen
                                    ist.</span
                                >
                            </v-tooltip>
                        </v-col>
                        <v-col>
                            <v-text-field
                                v-model="
                                    praktikumsstelle.namentlicheAnforderung
                                "
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
                                    >Bei Anforderung einer bestimmten NWK für
                                    die Stelle, hier den vollständigen Namen
                                    eintragen.</span
                                >
                            </v-tooltip>
                        </v-col>
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
                                    >Die Projektarbeit ist die Abschlussarbeit
                                    der Auszubildenden</span
                                >
                            </v-tooltip>
                        </v-col>
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
                            >
                            </v-select>
                        </v-col>
                        <v-col cols="2" />
                        <v-col>
                            <SelectMultipleAusbildungsjahr
                                :praktikumsstelle="praktikumsstelle"
                                @update:praktikumsstelle="
                                    (value) => (praktikumsstelle = value)
                                "
                            />
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
                            <v-select
                                v-model="praktikumsstelle.meldezeitraumID"
                                label="Meldezeitraum*"
                                :items="meldezeitraeume"
                                :menu-props="customMenuProps"
                                item-value="id"
                                item-text="zeitraumName"
                                outlined
                            >
                                <template #item="data">
                                    {{ data.item.zeitraumName }}:
                                    {{
                                        formatter.formatDateFromString(
                                            data.item.zeitraum.startZeitpunkt
                                        )
                                    }}
                                    -
                                    {{
                                        formatter.formatDateFromString(
                                            data.item.zeitraum.endZeitpunkt
                                        )
                                    }}
                                </template>
                            </v-select>
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
        </v-container>
    </v-container>
</template>

<script setup lang="ts">
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import { computed, onMounted, ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useRules } from "@/composables/rules";
import { Referat } from "@/types/Referat";
import { YesNoBoolean } from "@/types/YesNoBoolean";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import MeldungService from "@/api/PraktikumsstellenService";
import router from "@/router";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import PageTitle from "@/components/common/PageTitle.vue";
import { Programmierkenntnisse } from "@/types/YesNoEgalBoolean";
import { useFormatter } from "@/composables/formatter";
import { APP_SECURITY } from "@/Constants";
import { useUserStore } from "@/stores/user";
import Meldezeitraum from "@/types/Meldezeitraum";
import SelectMultipleAusbildungsjahr from "@/components/praktikumsplaetze/Meldung/AusbildungsJahrSelect.vue";

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const isAusbildungsleitung = ref<boolean>(false);
const loadingSite = ref<boolean>(true);
const userStore = useUserStore();
const validationRules = useRules();
const formatter = useFormatter();
const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];
const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.regexRule(
        /^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,63}\.[A-Za-z]{1,63}$/,
        "Keine gültige Email."
    ),
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
        })
        .finally(() => {
            loadingSite.value = false;
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