<template>
    <v-container
        @drop="drop($event, modelValue)"
        @dragover.prevent
        @dragenter.prevent
    >
        <yes-no-dialog-without-activator
            v-model="warningDialog"
            :dialogtitle="warningDialogTitle"
            :dialogtext="warningDialogText"
            @no="resetWarningDialog"
            @yes="assignNwk"
            value
        ></yes-no-dialog-without-activator>

        <yes-no-dialog-without-activator
            v-model="unassignConfirmDialog"
            :dialogtitle="unassignDialogTitle"
            :dialogtext="unassignDialogContent"
            @no="resetUnassign"
            @yes="unassignNwk"
            value
        ></yes-no-dialog-without-activator>
        <v-card
            class="full-width-card card"
            :class="{
                'custom-card-active': assignedNwk,
                spacer: true,
            }"
            elevation="6"
            :ripple="false"
            @click="show = !show"
        >
            <v-card-title
                >Stelle bei {{ props.modelValue.dienststelle }}</v-card-title
            >
            <v-card-subtitle v-if="props.modelValue.namentlicheAnforderung">
                Namentliche Anforderung:
                {{ props.modelValue.namentlicheAnforderung }}
            </v-card-subtitle>
          <v-icon
                  v-if="props.modelValue.planstelleVorhanden"
                  x-large
                  class="icon-top-right-position"
                  icon="mdi-account-star"
          ></v-icon>
            <v-card-text class="pt-0 mt-0 mb-0 pb-0">
                <p style="white-space: pre-line">
                    {{ getCardText(props.modelValue) }}
                </p></v-card-text
            >
            <v-col>
                <v-skeleton-loader
                    v-if="loading"
                    type="chip"
                ></v-skeleton-loader>
                <v-chip
                    v-if="assignedNwk && !loading"
                    :color="getNwkColor(assignedNwk)"
                    variant="flat"
                    >{{
                        `${assignedNwk.vorname} ${assignedNwk.nachname}`
                    }}
                  <template #close>
                    <v-icon icon="mdi-close-circle" @click.stop="openConfirmationDialog(modelValue)" />
                  </template>
                </v-chip
                ></v-col
            >
            <v-btn
                :icon="show ? 'mdi-chevron-up' : 'mdi-chevron-down'"
                class="icon-bottom-right-position"
                elevation="0"
                @click.stop="show = !show"
            ></v-btn>
            <v-expand-transition>
                <div v-show="show">
                    <v-divider></v-divider>
                    <v-card-text>
                        <p style="white-space: pre-line">
                            {{ getCardDetailText(props.modelValue) }}
                        </p>
                    </v-card-text>
                </div>
            </v-expand-transition>
        </v-card>
    </v-container>
</template>
<script setup lang="ts">
import { ref } from "vue";

import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import { useTextGenerator } from "@/composables/textGenerator";
import { useWarnings } from "@/composables/warningGenerator";
import emitter from "@/stores/eventBus";
import { findAusbildungsrichtungColorByValue } from "@/types/Ausbildungsrichtung";
import Nwk from "@/types/Nwk";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { findStudiengangColorByValue } from "@/types/Studiengang";

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();

const generator = useTextGenerator();
const warningsGenerator = useWarnings();

const show = ref<boolean>(false);
const loading = ref<boolean>(false);
const unassignDialogContent = ref<string>("");
const unassignDialogTitle = ref<string>("Zuweisung aufheben?");
const unassignConfirmDialog = ref<boolean>(false);
const warningDialog = ref<boolean>(false);
const nwkToAssignUnassing = ref<Nwk>();
const warningDialogTitle = ref<string>(
    "Warnung. Wollen sie wirklich fortfahren?"
);
const warningDialogText = ref<string>("");
const assignedNwk = ref(props.modelValue.assignedNwk);

let stelleToAssignUnassign: Praktikumsstelle | undefined;

function getCardText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardText(stelle);
}

function getCardDetailText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardDetailText(stelle);
}

function drop(event: DragEvent, stelle: Praktikumsstelle) {
    try {
        const draggedNwk: Nwk = JSON.parse(
            event.dataTransfer?.getData("application/json") as string
        );
        nwkToAssignUnassing.value = new Nwk(
            draggedNwk.id,
            draggedNwk.vorname,
            draggedNwk.nachname,
            draggedNwk.jahrgang,
            draggedNwk.vorlesungstage,
            draggedNwk.isActive,
            draggedNwk.studiengang,
            draggedNwk.ausbildungsrichtung
        );
    } catch (e) {
        return;
    }

    if (
        !stelle ||
        !stelle.id ||
        stelle.assignedNwk ||
        nwkToAssignUnassing.value.id === ""
    ) {
        return;
    }

    let warnings = warningsGenerator.getBeforeAssignmentWarnings(
        stelle,
        nwkToAssignUnassing.value
    );
    warnings.forEach((w) => (warningDialogText.value += w.message + "\n"));

    stelleToAssignUnassign = stelle;
    if (warningDialogText.value == "") {
        assignNwk();
    } else {
        warningDialog.value = true;
    }
}

function assignNwk() {
    if (!stelleToAssignUnassign || !stelleToAssignUnassign.id) {
        nwkToAssignUnassing.value = undefined;
        return;
    }
    loading.value = true;

    stelleToAssignUnassign.assignedNwk = nwkToAssignUnassing.value;
    PraktikumsstellenService.assignNwk(
        stelleToAssignUnassign.id || "",
        stelleToAssignUnassign.assignedNwk?.id
    ).finally(() => {
        loading.value = false;
    });
    assignedNwk.value = nwkToAssignUnassing.value;
    emitter.emit("assignedNwk", stelleToAssignUnassign.assignedNwk);
    resetWarningDialog();
}

function resetWarningDialog() {
    warningDialogText.value = "";
    warningDialog.value = false;
}

function unassignNwk() {
    if (stelleToAssignUnassign?.id) {
        loading.value = true;
        PraktikumsstellenService.unassignNwk(stelleToAssignUnassign.id).finally(
            () => {
                loading.value = false;
            }
        );
        emitter.emit("unassignedNwk", stelleToAssignUnassign.assignedNwk);
        stelleToAssignUnassign.assignedNwk = undefined;
        assignedNwk.value = undefined;
    }
    resetUnassign();
}

function openConfirmationDialog(stelle: Praktikumsstelle) {
    unassignConfirmDialog.value = true;
    stelleToAssignUnassign = stelle;
    unassignDialogContent.value = `Möchten sie die Zuweisung von ${stelle.assignedNwk?.vorname} ${stelle.assignedNwk?.nachname} wirklich aufheben?`;
}

function resetUnassign() {
    stelleToAssignUnassign = undefined;
    unassignConfirmDialog.value = false;
}

function getNwkColor(nwk: Nwk): string {
    let color = "primary";
    if (nwk.studiengang && nwk.ausbildungsrichtung == undefined) {
        color = findStudiengangColorByValue(nwk.studiengang);
    } else if (nwk.ausbildungsrichtung && nwk.studiengang == undefined) {
        color = findAusbildungsrichtungColorByValue(nwk.ausbildungsrichtung);
    }
    return color;
}
</script>
<style scoped>
.custom-card-active {
    border-color: #cfcfcf;
    background-color: #cfcfcf;
}

.card {
    padding-right: 45px;
}
.custom-card-title {
    margin-bottom: 5px;
    padding-bottom: 5px;
}

.custom-card-text {
    margin-bottom: 5px;
    padding-bottom: 5px;
    padding-top: 1px;
}

.custom-card-actions {
    margin-top: 5px;
    padding-top: 1px;
}
.custom-card-title {
    margin-bottom: 5px;
    padding-bottom: 5px;
}

.custom-card-text {
    margin-bottom: 5px;
    padding-bottom: 5px;
    padding-top: 1px;
}
.icon-top-right-position {
    position: absolute;
    top: 20px;
    right: 20px;
}
.icon-bottom-right-position {
    position: absolute;
    bottom: 10px;
    right: 10px;
}
</style>
