<template>
  <v-card
    width="100%"
    border
    @click="show = !show"
    @drop="drop($event, praktikumsstelle)"
    @dragover.prevent
    @dragenter.prevent
  >
    <v-btn
      :icon="show ? mdiChevronUp : mdiChevronDown"
      :class="{ 'custom-card-active': assignedNwk }"
      class="position-absolute top-0 right-0 mr-1 mt-1"
      elevation="0"
      @click.stop="show = !show"
    ></v-btn>
    <v-card-title
      >Stelle bei
      {{ properties.praktikumsstelle.dienststelle }}
    </v-card-title>
    <v-card-subtitle v-if="properties.praktikumsstelle.namentlicheAnforderung">
      Namentliche Anforderung:
      {{ properties.praktikumsstelle.namentlicheAnforderung }}
    </v-card-subtitle>
    <v-card-text class="pt-0 mb-0">
      <p style="white-space: pre-line">
        {{ getCardText(properties.praktikumsstelle) }}
      </p></v-card-text
    >
    <v-col
      v-if="assignedNwk || loading"
      class="pt-0"
    >
      <v-skeleton-loader
        v-if="loading"
        type="chip"
      ></v-skeleton-loader>
      <v-chip
        v-else-if="assignedNwk"
        :color="getNwkColor(assignedNwk)"
        variant="flat"
      >
        <span class="text-truncate">
          {{ `${assignedNwk.vorname} ${assignedNwk.nachname}` }}
        </span>

        <template #close>
          <v-icon
            :icon="mdiCloseCircle"
            @click.stop="openConfirmationDialog(praktikumsstelle)"
          />
        </template>
      </v-chip>
    </v-col>
    <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>
        <v-card-text>
          <p style="white-space: pre-line">
            {{ getCardDetailText(properties.praktikumsstelle) }}
          </p>
        </v-card-text>
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
</template>

<script setup lang="ts">
import { mdiChevronDown, mdiChevronUp, mdiCloseCircle } from "@mdi/js";
import { computed, ref } from "vue";

import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
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

const emits =
  defineEmits<
    (e: "update:modelValue", praktikumsstelleToUpdate: Praktikumsstelle) => void
  >();

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
  } catch {
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
    PraktikumsstellenService.unassignNwk(stelleToAssignUnassign.id, loading);
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
