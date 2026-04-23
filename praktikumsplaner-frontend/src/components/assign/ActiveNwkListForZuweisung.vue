<template>
  <v-container v-if="properties.modelValue && modelValue.length > 0">
    <v-list v-model:selected="selectedNwks">
      <v-list-item
        v-for="nwk in properties.modelValue"
        :key="nwk.id"
        :model-value="nwk"
        draggable="true"
        @dragstart="dragStart($event, nwk)"
      >
        <nwk-card :nwk="nwk" />
      </v-list-item>
    </v-list>
  </v-container>
  <v-container
    v-else
    class="d-flex justify-center align-center"
  >
    <v-row justify="center">
      <v-col
        cols="auto"
        class="d-flex align-center justify-center"
      >
        <v-icon
          color="blue"
          size="large"
          class="mr-3"
          :icon="mdiInformationOutline"
        />
        <span>Es sind noch keine Nachwuchskräfte vorhanden.</span>
      </v-col>
    </v-row>
  </v-container>
</template>
<script setup lang="ts">
import { mdiInformationOutline } from "@mdi/js";
import { onMounted, onUnmounted, ref } from "vue";

import NwkCard from "@/components/assign/NwkCard.vue";
import emitter from "@/stores/eventBus";
import Nwk from "@/types/Nwk";

const properties = defineProps<{
  modelValue: Nwk[];
}>();

const emits = defineEmits<(e: "input", nwks: Nwk[]) => void>();

const selectedNwks = ref<Nwk[]>([]);

const assignedNwkEventHandler = (value: unknown) =>
  value instanceof Nwk && removeNwkFromList(value);
const unassignedNwkEventHandler = (value: unknown) =>
  value instanceof Nwk && addNwkToList(value);
onMounted(() => {
  emitter.on("assignedNwk", assignedNwkEventHandler);
  emitter.on("unassignedNwk", unassignedNwkEventHandler);
});
onUnmounted(() => {
  emitter.off("assignedNwk", assignedNwkEventHandler);
  emitter.off("unassignedNwk", unassignedNwkEventHandler);
});

function dragStart(event: DragEvent, nwk: Nwk) {
  event.dataTransfer?.setData("application/json", JSON.stringify(nwk));
}

function removeNwkFromList(nwk: Nwk) {
  const nwksInternal = properties.modelValue;
  let index = -1;

  const nwkToRemove: Nwk | undefined = nwksInternal.find(
    (n) => n.id === nwk.id
  );

  if (nwkToRemove !== undefined) index = nwksInternal.indexOf(nwkToRemove, 0);

  if (index > -1) {
    nwksInternal.splice(index, 1);
    emits("input", nwksInternal);
  }
}

function addNwkToList(nwk: Nwk) {
  const nwksInternal = properties.modelValue;
  nwksInternal.push(nwk);
  nwksInternal.sort((a, b) => a.nachname.localeCompare(b.nachname));
  emits("input", nwksInternal);
}
</script>
<style scoped>
[v-cloak] [draggable="true"] {
  cursor: grab;
}
</style>
