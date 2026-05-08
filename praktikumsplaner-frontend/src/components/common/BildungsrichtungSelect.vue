<template>
  <v-autocomplete
    v-model="model"
    label="Richtung"
    :items="items"
    item-title="name"
    variant="outlined"
    clearable
  ></v-autocomplete>
</template>

<script setup lang="ts">
import { computed } from "vue";

import {
  Ausbildungsrichtungen,
  Studienrichtungen,
} from "@/types/Bildungsrichtung.ts";

const model = defineModel<string>();
const items = computed(() => [
  { type: "subheader", name: "Ausbildung" },
  ...Ausbildungsrichtungen,
  { type: "subheader", name: "Studium" },
  ...Studienrichtungen,
]);
</script>
