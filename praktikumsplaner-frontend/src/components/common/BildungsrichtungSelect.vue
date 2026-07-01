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
  BildungsrichtungKey,
  Studienrichtungen,
} from "@/types/Bildungsrichtung";

const model = defineModel<BildungsrichtungKey | undefined>();
const items = computed(() => [
  { type: "subheader", name: "Ausbildung" },
  ...Ausbildungsrichtungen,
  { type: "subheader", name: "Studium" },
  ...Studienrichtungen,
]);
</script>
