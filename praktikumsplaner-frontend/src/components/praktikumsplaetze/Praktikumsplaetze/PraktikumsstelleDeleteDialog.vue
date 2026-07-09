<template>
  <v-btn
    :icon="mdiDelete"
    color="error"
    alt-text="Löschen"
    :data-test="testIds.praktikumsstelle.deleteBtn"
    @click.stop="openDialog"
  />
  <yes-no-dialog-without-activator
    v-model="visible"
    :dialogtitle="'Stelle löschen?'"
    :dialogtext="'Wollen Sie die Praktikumsstelle wirklich unwiderruflich löschen?'"
    @no="visible = false"
    @yes="performDelete"
  />
  <progress-circular-overlay :loading="loading"></progress-circular-overlay>
</template>

<script setup lang="ts">
import type Praktikumsstelle from "@/types/Praktikumsstelle";

import { mdiDelete } from "@mdi/js";
import { ref } from "vue";

import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import { testIds } from "@/testIds";

const props = defineProps<{
  stelle: Praktikumsstelle;
}>();

const emit = defineEmits<{ deleted: [] }>();

const visible = ref(false);
const loading = ref(false);

function openDialog() {
  visible.value = true;
}

function performDelete() {
  PraktikumsstellenService.deletePraktikumsstelle(props.stelle, loading).then(
    () => {
      visible.value = false;
      emit("deleted");
    }
  );
}
</script>
