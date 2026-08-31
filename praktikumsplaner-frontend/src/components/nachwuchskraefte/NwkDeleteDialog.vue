<template>
  <v-btn
    :icon="mdiDelete"
    color="error"
    aria-label="Löschen"
    :data-test="testIds.nwk.deleteBtn"
    @click.stop="openDialog"
  />
  <yes-no-dialog-without-activator
    v-model="visible"
    :dialogtitle="'NWK löschen?'"
    :dialogtext="'Wollen Sie die NWK wirklich löschen?'"
    @no="visible = false"
    @yes="performDelete"
  />
  <progress-circular-overlay :loading="loading"></progress-circular-overlay>
</template>

<script setup lang="ts">
import { mdiDelete } from "@mdi/js";
import { ref } from "vue";

import NwkService from "@/api/NwkService.ts";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import { testIds } from "@/testIds";

const { nwkId } = defineProps<{
  nwkId: string;
}>();

const emit = defineEmits<{ deleted: [] }>();

const visible = ref(false);
const loading = ref(false);

function openDialog() {
  visible.value = true;
}

function performDelete() {
  loading.value = true;
  NwkService.deleteNwk(nwkId)
    .then(() => {
      visible.value = false;
      emit("deleted");
    })
    .finally(() => {
      loading.value = false;
    });
}
</script>
