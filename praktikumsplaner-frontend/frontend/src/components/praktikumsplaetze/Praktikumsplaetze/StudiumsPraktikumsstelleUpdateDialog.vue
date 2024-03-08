<template>
    <div>
        <v-btn
            v-if="!iconOnly"
            prepend-icon="mdi-pencil-outline"
            color="primary"
            variant="outlined"
            @click.stop="openDialog()"
            >Bearbeiten</v-btn
        >
        <v-btn
            v-else
            icon="mdi-pencil"
            variant="text"
            @click.stop="openDialog()"
        ></v-btn>
        <v-dialog
            v-model="visible"
            persistent
            max-width="1000"
        >
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >Praktikumsstelle bearbeiten</v-card-title
                    >
                    <v-container>
                        <v-container class="box">
                            <v-row>
                                <v-col>
                                    <span class="text-h6"
                                        >Stellenbeschreibung</span
                                    >
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col>
                                    <dienststellen-input
                                        v-model="praktikumsstelle"
                                        :is-required="true"
                                        :required-symbol="requiredFieldSymbol"
                                    ></dienststellen-input>
                                </v-col>
                                <v-col cols="2" />
                                <v-col>
                                    <referat-select
                                        v-model="praktikumsstelle"
                                        :disabled="hasAssignedNwk"
                                        :is-required="false"
                                    ></referat-select>
                                </v-col>
                                <v-col cols="1" />
                            </v-row>
                            <v-row>
                                <v-col>
                                    <dringlichkeit-select
                                        v-model="praktikumsstelle"
                                        :disabled="hasAssignedNwk"
                                        :is-required="true"
                                        :required-symbol="requiredFieldSymbol"
                                    ></dringlichkeit-select>
                                </v-col>
                                <v-col cols="2">
                                    <dringlichkeit-tooltip></dringlichkeit-tooltip>
                                </v-col>
                                <v-col>
                                    <namentliche-anforderung-input
                                        v-model="praktikumsstelle"
                                        :disabled="hasAssignedNwk"
                                        :is-required="false"
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
                                        :disabled="hasAssignedNwk"
                                        :is-required="true"
                                        :required-symbol="requiredFieldSymbol"
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
                                        :disabled="hasAssignedNwk"
                                        :is-required="true"
                                        :required-symbol="requiredFieldSymbol"
                                    ></studienrichtung-select>
                                </v-col>
                                <v-col cols="2" />
                                <v-col>
                                    <semester-select
                                        v-model="praktikumsstelle"
                                        :disabled="hasAssignedNwk"
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
                                        :disabled="hasAssignedNwk"
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
                                    <span class="text-h6"
                                        >örtliche*r Ausbilder*in</span
                                    >
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col>
                                    <ausbilder-input
                                        v-model="praktikumsstelle"
                                        :is-required="true"
                                        :required-symbol="requiredFieldSymbol"
                                    ></ausbilder-input>
                                </v-col>
                                <v-col cols="2" />
                                <v-col>
                                    <ausbilder-email-input
                                        v-model="praktikumsstelle"
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
                                        v-model="praktikumsstelle"
                                        :disabled="hasAssignedNwk"
                                        :meldezeitraueme="meldezeitraeume"
                                        :is-required="true"
                                        :required-symbol="requiredFieldSymbol"
                                    ></meldezeitraum-select>
                                </v-col>
                                <v-col cols="2" />
                                <v-col> </v-col>
                                <v-col cols="1" />
                            </v-row>
                        </v-container>
                    </v-container>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            variant="outlined"
                            @click="cancel()"
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
        <progress-circular-overlay
            :loading="loading"
        ></progress-circular-overlay>
    </div>
</template>

<script setup lang="ts">
import type Praktikumsstelle from "@/types/Praktikumsstelle";

import { computed, ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import AusbilderEmailInput from "@/components/praktikumsplaetze/Meldung/AusbilderEmailInput.vue";
import AusbilderInput from "@/components/praktikumsplaetze/Meldung/AusbilderInput.vue";
import DienststellenInput from "@/components/praktikumsplaetze/Meldung/DienststellenInput.vue";
import DringlichkeitSelect from "@/components/praktikumsplaetze/Meldung/DringlichkeitSelect.vue";
import DringlichkeitTooltip from "@/components/praktikumsplaetze/Meldung/DringlichkeitTooltip.vue";
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import NamentlicheAnforderungInput from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungInput.vue";
import NamentlicheAnforderungTooltip from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungTooltip.vue";
import PlanstelleRadioGroup from "@/components/praktikumsplaetze/Meldung/PlanstelleRadioGroup.vue";
import ProgrammierKenntnisseSelect from "@/components/praktikumsplaetze/Meldung/ProgrammierKenntnisseSelect.vue";
import ReferatSelect from "@/components/praktikumsplaetze/Meldung/ReferatSelect.vue";
import StudienrichtungSelect from "@/components/praktikumsplaetze/Meldung/StudienrichtungSelect.vue";
import SemesterSelect from "@/components/praktikumsplaetze/Meldung/StudiensemesterSelect.vue";
import TaetigkeitenInput from "@/components/praktikumsplaetze/Meldung/TaetigkeitenInput.vue";
import Meldezeitraum from "@/types/Meldezeitraum";
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

const meldezeitraeume = ref<Meldezeitraum[]>([
    new Meldezeitraum("", new Zeitraum(), ""),
]);

const emits = defineEmits<{
    (e: "updated", praktikumsstelleToUpdate: Praktikumsstelle): void;
}>();

const praktikumsstelle = computed({
    get: () => properties.modelValue,
    set: (newValue) => emits("updated", newValue),
});

const hasAssignedNwk = computed(() => {
    return properties.modelValue.assignedNwk !== undefined;
});

function cancel() {
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

        cancel();
        PraktikumsstellenService.updatePraktikumsstelle(
            praktikumsstelle.value,
            loading
        ).then(() => {
            emits("updated", praktikumsstelle.value);
        });
    });
}
</script>
