<template>
    <v-dialog
        v-model="visible"
        persistent
        max-width="1000"
    >
        <template #activator>
            <template v-if="!iconOnly">
                <v-btn
                    prepend-icon="mdi-pencil-outline"
                    color="primary"
                    variant="outlined"
                    @click.stop="openDialog()"
                    >Bearbeiten
                </v-btn>
            </template>
            <template v-else>
                <v-btn
                    icon="mdi-pencil"
                    variant="text"
                    @click.stop="openDialog()"
                ></v-btn>
            </template>
        </template>
        <v-form ref="form">
            <v-card>
                <v-card-title class="text-h5 font-weight-bold"
                    >Praktikumsstelle bearbeiten
                </v-card-title>
                <v-container>
                    <v-container class="box">
                        <v-row>
                            <v-col>
                                <span class="text-h6">Stellenbeschreibung</span>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                                <dienststellen-input
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                ></dienststellen-input>
                            </v-col>
                            <v-col cols="2" />
                            <v-col>
                                <referat-select
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="false"
                                    :disabled="hasAssignedNwk"
                                ></referat-select>
                            </v-col>
                            <v-col cols="1" />
                        </v-row>
                        <v-row>
                            <v-col>
                                <dringlichkeit-select
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                    :disabled="hasAssignedNwk"
                                ></dringlichkeit-select>
                            </v-col>
                            <v-col cols="2">
                                <dringlichkeit-tooltip></dringlichkeit-tooltip>
                            </v-col>
                            <v-col>
                                <namentliche-anforderung-input
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="false"
                                    :disabled="hasAssignedNwk"
                                ></namentliche-anforderung-input>
                            </v-col>
                            <v-col cols="1">
                                <namentliche-anforderung-tooltip></namentliche-anforderung-tooltip>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                                <planstelle-radio-group
                                    v-model="praktikumsstelleToSubmit"
                                    :disabled="hasAssignedNwk"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                ></planstelle-radio-group>
                            </v-col>
                            <v-col cols="2" />
                            <v-col>
                                <projektarbeit-radio-group
                                    v-model="praktikumsstelleToSubmit"
                                    :disabled="hasAssignedNwk"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                ></projektarbeit-radio-group>
                            </v-col>
                            <v-col cols="1">
                                <projektarbeit-tooltip></projektarbeit-tooltip>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                                <taetigkeiten-input
                                    v-model="praktikumsstelleToSubmit"
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
                                <ausbildungsrichtung-select
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                    :disabled="hasAssignedNwk"
                                ></ausbildungsrichtung-select>
                            </v-col>
                            <v-col cols="2" />
                            <v-col>
                                <ausbildungs-jahr-select
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                    :disabled="hasAssignedNwk"
                                ></ausbildungs-jahr-select>
                            </v-col>
                            <v-col cols="1" />
                        </v-row>
                        <v-row>
                            <v-col>
                                <programmier-kenntnisse-select
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="false"
                                    :disabled="hasAssignedNwk"
                                ></programmier-kenntnisse-select>
                            </v-col>
                            <v-col />
                            <v-col cols="3" />
                        </v-row>
                    </v-container>
                    <v-container class="box">
                        <v-row>
                            <v-col>
                                <span class="text-h6"
                                    >örtliche*r Ausbilder*in</span
                                >
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                                <ausbilder-input
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                ></ausbilder-input>
                            </v-col>
                            <v-col cols="2" />
                            <v-col>
                                <ausbilder-email-input
                                    v-model="praktikumsstelleToSubmit"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                ></ausbilder-email-input>
                            </v-col>
                            <v-col cols="1" />
                        </v-row>
                    </v-container>
                    <v-container class="box">
                        <v-row>
                            <v-col>
                                <span class="text-h6"
                                    >Meldezeitraum Auswahl</span
                                >
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                                <meldezeitraum-select
                                    v-model="praktikumsstelleToSubmit"
                                    :meldezeitraueme="meldezeitraeume"
                                    :is-required="true"
                                    :required-symbol="requiredFieldSymbol"
                                    :disabled="hasAssignedNwk"
                                ></meldezeitraum-select>
                            </v-col>
                            <v-col cols="2" />
                            <v-col></v-col>
                            <v-col cols="1" />
                        </v-row>
                    </v-container>
                </v-container>
                <v-card-actions>
                    <v-spacer />
                    <v-btn
                        color="primary"
                        variant="outlined"
                        @click="closeDialog()"
                    >
                        Abbrechen
                    </v-btn>
                    <v-btn
                        color="primary"
                        variant="flat"
                        @click="updatePraktikumsstelle()"
                    >
                        Akzeptieren
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-form>
    </v-dialog>
    <progress-circular-overlay :loading="loading"></progress-circular-overlay>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import AusbilderEmailInput from "@/components/praktikumsplaetze/Meldung/AusbilderEmailInput.vue";
import AusbilderInput from "@/components/praktikumsplaetze/Meldung/AusbilderInput.vue";
import AusbildungsJahrSelect from "@/components/praktikumsplaetze/Meldung/AusbildungsJahrSelect.vue";
import AusbildungsrichtungSelect from "@/components/praktikumsplaetze/Meldung/AusbildungsrichtungSelect.vue";
import DienststellenInput from "@/components/praktikumsplaetze/Meldung/DienststellenInput.vue";
import DringlichkeitSelect from "@/components/praktikumsplaetze/Meldung/DringlichkeitSelect.vue";
import DringlichkeitTooltip from "@/components/praktikumsplaetze/Meldung/DringlichkeitTooltip.vue";
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import NamentlicheAnforderungInput from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungInput.vue";
import NamentlicheAnforderungTooltip from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungTooltip.vue";
import PlanstelleRadioGroup from "@/components/praktikumsplaetze/Meldung/PlanstelleRadioGroup.vue";
import ProgrammierKenntnisseSelect from "@/components/praktikumsplaetze/Meldung/ProgrammierKenntnisseSelect.vue";
import ProjektarbeitRadioGroup from "@/components/praktikumsplaetze/Meldung/ProjektarbeitRadioGroup.vue";
import ProjektarbeitTooltip from "@/components/praktikumsplaetze/Meldung/ProjektarbeitTooltip.vue";
import ReferatSelect from "@/components/praktikumsplaetze/Meldung/ReferatSelect.vue";
import TaetigkeitenInput from "@/components/praktikumsplaetze/Meldung/TaetigkeitenInput.vue";
import emitter from "@/stores/eventBus";
import Meldezeitraum from "@/types/Meldezeitraum";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Zeitraum from "@/types/Zeitraum";

const visible = ref<boolean>(false);
const loading = ref<boolean>(false);
const form = ref<HTMLFormElement>();
const requiredFieldSymbol = "*";

interface Properties {
    modelValue: Praktikumsstelle;
    iconOnly?: boolean;
}

const properties = withDefaults(defineProps<Properties>(), {
    iconOnly: false,
});

const praktikumsstelleToSubmit = ref<Praktikumsstelle>(
    JSON.parse(JSON.stringify(properties.modelValue))
);

const hasAssignedNwk = computed(() => {
    return properties.modelValue.assignedNwk != undefined;
});

const meldezeitraeume = ref<Meldezeitraum[]>([
    new Meldezeitraum("", new Zeitraum(), ""),
]);

const emits = defineEmits<{
    (e: "update:modelValue", praktikumsstelleToUpdate: Praktikumsstelle): void;
}>();

const praktikumsstelle = computed({
    get: () => properties.modelValue,
    set: (newValue) => emits("update:modelValue", newValue),
});

function closeDialog() {
    visible.value = false;
}

function openDialog() {
    MeldezeitraumService.getAllMeldezeitraeume(loading).then((zeitraume) => {
        meldezeitraeume.value = zeitraume;
        if (praktikumsstelle.value.meldezeitraumID == null) {
            MeldezeitraumService.getCurrentMeldezeitraum(undefined).then(
                (zeitraum) => {
                    praktikumsstelle.value.meldezeitraumID = zeitraum[0].id;
                }
            );
        }
        visible.value = true;
    });
}

function updatePraktikumsstelle() {
    form.value?.validate().then((validation: { valid: boolean }) => {
        if (!validation.valid) return;

        closeDialog();
        PraktikumsstellenService.updatePraktikumsstelle(
            praktikumsstelleToSubmit.value,
            loading
        ).then(() => {
            emits("update:modelValue", praktikumsstelleToSubmit.value);
            emitter.emit("praktikumsstelleUpdated");
        });
    });
}
</script>
