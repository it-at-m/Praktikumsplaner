<template>
  <v-autocomplete
    v-model="model"
    label="Richtung"
    :items="items"
    item-title="name"
    variant="outlined"
    clearable
    :disabled="props.disabled"
    :rules="conditionalRequiredRules"
    :data-test="testIds.nwk.richtungSelect"
  ></v-autocomplete>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules.ts";
import { testIds } from "@/testIds";
import {
  Ausbildungsrichtungen,
  BildungsrichtungKey,
  Studienrichtungen,
} from "@/types/Bildungsrichtung";

const validationRules = useRules();

export interface Props {
  isRequired: boolean;
  disabled?: boolean;
}

const model = defineModel<BildungsrichtungKey | null | undefined>();
const props = withDefaults(defineProps<Props>(), {
  disabled: false,
});
const items = computed(() => [
  { type: "subheader", name: "Ausbildung" },
  ...Ausbildungsrichtungen,
  { type: "subheader", name: "Studium" },
  ...Studienrichtungen,
]);
const conditionalRequiredRules = computed(() => {
  return props.isRequired
    ? [validationRules.notEmptyRule("Darf nicht leer sein.")]
    : undefined;
});
</script>
