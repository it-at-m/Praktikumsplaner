<template>
    <v-container
        @drop="drop($event, praktikumsstelle)"
        @dragover.prevent
        @dragenter.prevent
    >
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
                >Stelle bei
                {{ properties.praktikumsstelle.dienststelle }}</v-card-title
            >
            <v-card-subtitle
                v-if="properties.praktikumsstelle.namentlicheAnforderung"
            >
                Namentliche Anforderung:
                {{ properties.praktikumsstelle.namentlicheAnforderung }}
            </v-card-subtitle>
            <v-icon
                v-if="properties.praktikumsstelle.planstelleVorhanden"
                size="x-large"
                class="icon-top-right-position"
                icon="mdi-account-star"
            ></v-icon>
            <v-card-text class="pt-0 mt-0 mb-0 pb-0">
                <p style="white-space: pre-line">
                    {{ getCardText(properties.praktikumsstelle) }}
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
                    class="chip"
                >
                    <span class="text-truncate chip-text">
                        {{ `${assignedNwk.vorname} ${assignedNwk.nachname}` }}
                    </span>

                    <template #close>
                        <v-icon
                            icon="mdi-close-circle"
                            @click.stop="openConfirmationDialog(praktikumsstelle)"
                        />
                    </template> </v-chip
            ></v-col>
            <v-btn
                :icon="show ? 'mdi-chevron-up' : 'mdi-chevron-down'"
                :class="{ 'custom-card-active': assignedNwk }"
                class="icon-bottom-right-position"
                elevation="0"
                @click.stop="show = !show"
            ></v-btn>
            <v-expand-transition>
                <div v-show="show">
                    <v-divider></v-divider>
                    <v-card-text>
                        <p style="white-space: pre-line">
                            {{ getCardDetailText(properties.praktikumsstelle) }}
                        </p>
                    </v-card-text>
                    <v-card-actions>
                        <ausbildungs-praktikumsstelle-update-dialog
                            v-if="isAusbildungsPraktikumsstelle"
                            v-model="praktikumsstelle"
                            :icon-only="true"
                        ></ausbildungs-praktikumsstelle-update-dialog>
                        <studiums-praktikumsstelle-update-dialog
                            v-else-if="isStudiumsPraktikumsstelle"
                            v-model="praktikumsstelle"
                            :icon-only="true"
                        ></studiums-praktikumsstelle-update-dialog>
                    </v-card-actions>
                </div>
            </v-expand-transition>
        </v-card>
        <yes-no-dialog-without-activator
            v-model="warningDialog"
            :dialogtitle="warningDialogTitle"
            :dialogtext="warningDialogText"
            value
            @no="resetWarningDialog"
            @yes="assignNwk"
        ></yes-no-dialog-without-activator>

        <yes-no-dialog-without-activator
            v-model="unassignConfirmDialog"
            :dialogtitle="unassignDialogTitle"
            :dialogtext="unassignDialogContent"
            value
            @no="resetUnassign"
            @yes="unassignNwk"
        ></yes-no-dialog-without-activator>
    </v-container>
</template>
<script setup lang="ts">
import { computed, ref } from "vue";

import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import AusbildungsPraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/AusbildungsPraktikumsstelleUpdateDialog.vue";
import StudiumsPraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/StudiumsPraktikumsstelleUpdateDialog.vue";
import { useTextGenerator } from "@/composables/textGenerator";
import { useWarnings } from "@/composables/warningGenerator";
import emitter from "@/stores/eventBus";
import { findAusbildungsrichtungColorByValue } from "@/types/Ausbildungsrichtung";
import Nwk from "@/types/Nwk";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { findStudiengangColorByValue } from "@/types/Studiengang";

const generator = useTextGenerator();
const warningsGenerator = useWarnings();

const show = ref<boolean>(false);
const loading = ref<boolean>(false);
const unassignDialogContent = ref<string>("");
const unassignDialogTitle = ref<string>("Zuweisung aufheben?");
const unassignConfirmDialog = ref<boolean>(false);
const warningDialog = ref<boolean>(false);
const nwkToAssignUnassing = ref<Nwk>();

const properties = defineProps<{
    praktikumsstelle: Praktikumsstelle;
}>();

const emits = defineEmits<{
    (e: "update:modelValue", praktikumsstelleToUpdate: Praktikumsstelle): void;
}>();

const praktikumsstelle = computed({
    get: () => properties.praktikumsstelle,
    set: (newValue) => emits("update:modelValue", newValue),
});

const warningDialogTitle = ref<string>(
    "Warnung. Wollen sie wirklich fortfahren?"
);
const warningDialogText = ref<string>("");
const assignedNwk = ref(properties.praktikumsstelle.assignedNwk);

let stelleToAssignUnassign: Praktikumsstelle | undefined;

const isAusbildungsPraktikumsstelle = ref<boolean>(
    PraktikumsstellenService.isAusbildungsPraktikumsstelle(
        praktikumsstelle.value
    )
);
const isStudiumsPraktikumsstelle = ref<boolean>(
    PraktikumsstellenService.isStudiumsPraktikumsstelle(praktikumsstelle.value)
);

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

    const warnings = warningsGenerator.getBeforeAssignmentWarnings(
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

    stelleToAssignUnassign.assignedNwk = nwkToAssignUnassing.value;
    PraktikumsstellenService.assignNwk(
        stelleToAssignUnassign.id || "",
        stelleToAssignUnassign.assignedNwk?.id,
        loading
    );
    assignedNwk.value = nwkToAssignUnassing.value;
    if (stelleToAssignUnassign.assignedNwk)
        emitter.emit("assignedNwk", stelleToAssignUnassign.assignedNwk);
    resetWarningDialog();
}

function resetWarningDialog() {
    warningDialogText.value = "";
    warningDialog.value = false;
}

function unassignNwk() {
    if (stelleToAssignUnassign?.id) {
        PraktikumsstellenService.unassignNwk(
            stelleToAssignUnassign.id,
            loading
        );
        if (stelleToAssignUnassign.assignedNwk)
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

/*
This code can be updated when the Firefox version is updated.
In some browsers the pseudo-class :has() is not yet supported.  A possible implementation could look like this.

.v-chip__content:has(> .chip-text) {
    overflow: hidden;
}
 */
@media only screen and (max-width: 1000px) {
    .chip-text {
        max-width: 10vw !important;
    }
}
@media only screen and (max-width: 1900px) and (min-width: 1000px) {
    .chip-text {
        max-width: 15vw !important;
    }
}
.chip-text {
    max-width: 25vw;
}
</style>
