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
                            v-model="praktikumsstelle.studiengang"
                            label="Studienrichtung*"
                            :items="Studiengang"
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
                            v-model="praktikumsstelle.studiensemester"
                            label="Studiensemester*"
                            :items="Studiensemester"
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
                            label="Programmierkenntnisse*"
                            :items="Programmierkenntnisse"
                            :menu-props="customMenuProps"
                            :rules="requiredRule"
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
                v-show="isAusbidungsleitung"
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
                        ></v-select>
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
import { onMounted, ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useRules } from "@/composables/rules";
import { useZeitraeume } from "@/composables/voraussichtlicherZuweisungsZeitraum";
import { Referat } from "@/types/Referat";
import { YesNoBoolean } from "@/types/YesNoBoolean";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import { Studiengang } from "@/types/Studiengang";
import { Studiensemester } from "@/types/Studiensemester";
import MeldungService from "@/api/PraktikumsstellenService";
import router from "@/router";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import PageTitle from "@/components/common/PageTitle.vue";
import { Programmierkenntnisse } from "@/types/YesNoEgalBoolean";
import "@/directives/Security";
import { useFormatter } from "@/composables/formatter";
import { useUserStore } from "@/stores/user";
import { APP_SECURITY } from "@/Constants";

const activeMeldezeitraum = ref<boolean>(false);

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const zeitraeueme = useZeitraeume();
const zuweisungsZeitraum = ref<string>("");
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
    validationRules.notEmptyRule("Darf nicht leer sein."),
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
        "Der Name der angeforderte NWK darf nicht länger als 255 Zeichen sein."
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
const formatter = useFormatter();
const meldezeitraeume = ref<object[]>([]);
const isAusbidungsleitung = ref<boolean>(false);

onMounted(() => {
    MeldezeitraumService.getCurrentMeldezeitraum()
        .then((zeitraueme) => {
            activeMeldezeitraum.value = zeitraueme.length > 0;
        })
        .then(() => {
            if (
                userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG") ||
                APP_SECURITY !== "true"
            ) {
                isAusbidungsleitung.value = true;
                activeMeldezeitraum.value = true;
                MeldezeitraumService.getAllMeldezeitraeume().then(
                    (zeitraeume) => {
                        meldezeitraeume.value = zeitraeume.map((zeitraum) => {
                            return {
                                id: zeitraum.id,
                                zeitraumName: `${
                                    zeitraum.zeitraumName
                                }: ${formatter.formatDateFromString(
                                    zeitraum.zeitraum.startZeitpunkt
                                )} - ${formatter.formatDateFromString(
                                    zeitraum.zeitraum.endZeitpunkt
                                )}`,
                            };
                        });
                    }
                );
            }
        });
});

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