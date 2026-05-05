<template>
  <v-btn
    :icon="mdiDelete"
    color="error"
    @click.stop="openDialog"
  />
  <yes-no-dialog-without-activator
    v-model="visible"
    :dialogtitle="'Stelle löschen?'"
    :dialogtext="'Wollen Sie die Praktikumsstelle wirklich unwiderruflich löschen?'"
    :loading="loading"
    @no="visible = false"
    @yes="performDelete"
  />
</template>

<script setup lang="ts">
import type Praktikumsstelle from "@/types/Praktikumsstelle";

import { mdiDelete } from "@mdi/js";
import { ref } from "vue";

import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";

const props = defineProps<{
  stelle: Praktikumsstelle;
}>();

const emit = defineEmits<(e: "deleted") => void>();

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
