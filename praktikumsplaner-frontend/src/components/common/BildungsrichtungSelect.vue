<template>
  <v-autocomplete
    v-model="model"
    label="Richtung"
    :items="items"
    item-title="name"
    variant="outlined"
    clearable
    :data-test="testIds.nwk.richtungSelect"
  ></v-autocomplete>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { testIds } from "@/testIds";
import {
  Ausbildungsrichtungen,
  BildungsrichtungKey,
  Studienrichtungen,
} from "@/types/Bildungsrichtung";

const model = defineModel<BildungsrichtungKey | null | undefined>();
const items = computed(() => [
  { type: "subheader", name: "Ausbildung" },
  ...Ausbildungsrichtungen,
  { type: "subheader", name: "Studium" },
  ...Studienrichtungen,
]);
</script>
