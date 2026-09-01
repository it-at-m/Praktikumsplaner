<template>
  <v-radio-group
    v-model="value"
    class="radios custom-label"
    inline
    :rules="conditionalRequiredRules"
    :disabled="disabled"
    :data-test="testIds.praktikumsstelle.programmierkenntnisseRadio"
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
    />
  </v-radio-group>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { testIds } from "@/testIds";
import { YesNoBoolean } from "@/types/YesNoBoolean.ts";

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
const value = defineModel<boolean>();

const label = "Programmierkenntnisse";
const conditionalRequiredLabel = computed(() => {
  return properties.isRequired ? label + properties.requiredSymbol : label;
});

const requiredRule = [
  validationRules.notEmptyBooleanRule("Darf nicht leer sein."),
];
const conditionalRequiredRules = computed(() => {
  return properties.isRequired ? requiredRule : undefined;
});
</script>
