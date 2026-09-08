<template>
  <v-radio-group
    v-model="model"
    class="radios custom-label"
    inline
    :rules="conditionalRequiredRules"
    :disabled="disabled"
    :data-test="testIds.praktikumsstelle.minderjaehrigRadio"
  >
    <template #label>
      <span class="custom-label">{{ conditionalRequiredLabel }}:</span>
    </template>
    <v-radio
      v-for="item in YesNoBoolean"
      :key="item.name"
      :label="item.name"
      :value="item.value"
      class="ml-5"
    ></v-radio>
  </v-radio-group>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { testIds } from "@/testIds";
import { YesNoBoolean } from "@/types/YesNoBoolean";

const validationRules = useRules();

interface Properties {
  isRequired: boolean;
  requiredSymbol?: string;
  disabled?: boolean;
}
const properties = withDefaults(defineProps<Properties>(), {
  requiredSymbol: "*",
  disabled: false,
});
const model = defineModel<boolean>();

const label = "Betreuung minderjährige NWK möglich?";
const conditionalRequiredLabel = computed(() => {
  return properties.isRequired ? label + properties.requiredSymbol : label;
});

const booleanRule = [
  validationRules.notEmptyBooleanRule("Darf nicht leer sein."),
];
const conditionalRequiredRules = computed(() => {
  return properties.isRequired ? booleanRule : undefined;
});
</script>

<style scoped>
.custom-label {
  font-size: 18px !important;
}
</style>
