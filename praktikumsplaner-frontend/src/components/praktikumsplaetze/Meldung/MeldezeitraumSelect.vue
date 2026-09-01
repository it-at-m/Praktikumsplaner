<template>
  <v-select
    v-model="model"
    :label="conditionalRequiredLabel"
    item-value="id"
    item-title="zeitraumName"
    :items="properties.meldezeitraueme"
    :rules="conditionalRequiredRules"
    variant="outlined"
    :clearable="!isRequired"
    :disabled="disabled"
    :data-test="testIds.praktikumsstelle.meldezeitraumSelect"
    :loading="loading"
  >
    <template #item="{ props, item }">
      <v-list-item v-bind="props">
        <v-list-item-title>
          {{ formatter.formatDateFromString(item.raw.zeitraum.startZeitpunkt) }}
          -
          {{ formatter.formatDateFromString(item.raw.zeitraum.endZeitpunkt) }}
        </v-list-item-title>
      </v-list-item>
    </template>
    <template #selection="{ item }">
      {{ item.raw.zeitraumName }} :
      {{ formatter.formatDateFromString(item.raw.zeitraum.startZeitpunkt) }}
      -
      {{ formatter.formatDateFromString(item.raw.zeitraum.endZeitpunkt) }}
    </template>
  </v-select>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useFormatter } from "@/composables/formatter";
import { useRules } from "@/composables/rules";
import { testIds } from "@/testIds";
import Meldezeitraum from "@/types/Meldezeitraum";

const validationRules = useRules();

interface Properties {
  meldezeitraueme: Meldezeitraum[];
  isRequired: boolean;
  requiredSymbol?: string;
  disabled?: boolean;
  loading?: boolean;
}
const properties = withDefaults(defineProps<Properties>(), {
  requiredSymbol: "*",
  disabled: false,
  loading: false,
});
const model = defineModel<string | null>();

const label = "Meldezeitraum";
const conditionalRequiredLabel = computed(() => {
  return properties.isRequired ? label + properties.requiredSymbol : label;
});

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];
const conditionalRequiredRules = computed(() => {
  return properties.isRequired ? requiredRule : undefined;
});

const formatter = useFormatter();
</script>
