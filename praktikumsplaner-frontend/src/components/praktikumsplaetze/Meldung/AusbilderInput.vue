<template>
  <v-text-field
    v-model="model"
    :rules="conditionalRequiredRules"
    :label="conditionalRequiredLabel"
    variant="outlined"
    :clearable="!isRequired"
    :disabled="disabled"
    :data-test="testIds.praktikumsstelle.ausbilderInput"
  ></v-text-field>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { testIds } from "@/testIds";

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
const model = defineModel<string>();

const label = "Name örtliche*r Ausbilder*in";
const conditionalRequiredLabel = computed(() => {
  return properties.isRequired ? label + properties.requiredSymbol : label;
});

const oertlAusbilderRule = [
  validationRules.notEmptyRule("Darf nicht leer sein."),
  validationRules.maxLengthRule(
    255,
    "Örtliche Ausbilder*in darf nicht länger als 255 Zeichen sein."
  ),
];
const conditionalRequiredRules = computed(() => {
  return properties.isRequired ? oertlAusbilderRule : undefined;
});
</script>
