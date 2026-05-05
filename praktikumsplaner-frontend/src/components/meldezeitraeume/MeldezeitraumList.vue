<template>
  <slot name="header"></slot>
  <v-list v-if="properties.value.length > 0">
    <v-card
      v-for="meldezeitraum in properties.value"
      :key="meldezeitraum.id"
      border
      elevation="0"
      class="mt-1"
    >
      <v-list-item lines="two">
        <v-list-item-title class="justify-center">
          {{ meldezeitraum.zeitraumName }}
        </v-list-item-title>
        <v-list-item-subtitle>
          <v-icon :icon="mdiCalendarStart" />
          {{
            useFormatter().formatDateFromString(
              meldezeitraum.zeitraum.startZeitpunkt
            )
          }}

          <v-icon :icon="mdiCalendarEnd" />
          {{
            useFormatter().formatDateFromString(
              meldezeitraum.zeitraum.endZeitpunkt
            )
          }}
        </v-list-item-subtitle>
        <template #append>
          <v-btn-group density="comfortable">
            <v-btn
              color="error"
              :icon="mdiDelete"
              @click="warnBeforeDelete(meldezeitraum)"
            >
            </v-btn>
          </v-btn-group>
        </template>
      </v-list-item>
    </v-card>
  </v-list>
  <slot
    v-else
    name="notfoundmessage"
  ></slot>
  <yes-no-dialog-without-activator
    :dialogtitle="deleteWarningDialogTitle"
    :dialogtext="deleteWarningDialogText"
    :model-value="showDeleteWarningDialog"
    @no="resetDeleteWarningDialog"
    @yes="deleteMeldezeitraum"
  ></yes-no-dialog-without-activator>
  <progress-circular-overlay :loading="loading"></progress-circular-overlay>
</template>

<script setup lang="ts">
import { mdiCalendarEnd, mdiCalendarStart, mdiDelete } from "@mdi/js";
import { ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import { useFormatter } from "@/composables/formatter";
import Meldezeitraum from "@/types/Meldezeitraum";

const showDeleteWarningDialog = ref(false);
const loading = ref(false);
const meldezeitraumToDelete = ref<Meldezeitraum>();
const deleteWarningDialogTitle = ref("ACHTUNG!");
const deleteWarningDialogText = ref(
  "Wenn Sie den Meldezeitraum löschen, werden auch ALLE zugehörigen Praktikumsplätze gelöscht. Wollen Sie den Meldezeitraum wirklich löschen?"
);

const properties = defineProps<{
  value: Meldezeitraum[];
}>();

const emits =
  defineEmits<
    (e: "deleted", meldezeitraum: Meldezeitraum | undefined) => void
  >();

function warnBeforeDelete(meldezeitraum: Meldezeitraum) {
  showDeleteWarningDialog.value = true;
  meldezeitraumToDelete.value = meldezeitraum;
}

function resetDeleteWarningDialog() {
  showDeleteWarningDialog.value = false;
  meldezeitraumToDelete.value = undefined;
}

function deleteMeldezeitraum() {
  if (!meldezeitraumToDelete.value) {
    return;
  }
  MeldezeitraumService.deleteMeldezeitraumById(
    meldezeitraumToDelete.value.id,
    loading
  )
    .then(() => {
      emits("deleted", meldezeitraumToDelete.value);
    })
    .finally(() => {
      resetDeleteWarningDialog();
    });
}
</script>
