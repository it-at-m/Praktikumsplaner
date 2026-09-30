<template>
  <v-row>
    <v-col>
      <v-row>
        <v-col cols="12">
          <v-date-input
            ref="startInput"
            v-model="startDatum"
            density="compact"
            variant="outlined"
            :label="`Beginn des ${label}s`"
            :rules="startZeitpunktRules"
            :data-test="testIds.meldezeitraum.startInput"
            @update:model-value="model.endZeitpunkt && endInput?.validate()"
          />
        </v-col>
        <v-col cols="12">
          <v-date-input
            ref="endInput"
            v-model="endDatum"
            density="compact"
            variant="outlined"
            :label="`Ende des ${label}s`"
            :rules="endZeitpunktRules"
            :data-test="testIds.meldezeitraum.endInput"
            @update:model-value="model.startZeitpunkt && startInput?.validate()"
          />
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import { computed, useTemplateRef } from "vue";

import { useRules } from "@/composables/rules";
import { testIds } from "@/testIds";
import Zeitraum from "@/types/Zeitraum";

const props = defineProps<{ label: string }>();
const model = defineModel<Zeitraum>({ required: true });

const validationRules = useRules();
const startInput = useTemplateRef("startInput");
const endInput = useTemplateRef("endInput");

const toLocalDate = (value: string | undefined): Date | undefined => {
  if (!value) {
    return undefined;
  }

  const [y, m, d] = value.split("-").map(Number);
  return y && m && d ? new Date(y, m - 1, d) : undefined;
};

const toLocalDateString = (
  value: Date | null | undefined
): string | undefined => {
  if (!value) {
    return undefined;
  }

  return [
    value.getFullYear(),
    String(value.getMonth() + 1).padStart(2, "0"),
    String(value.getDate()).padStart(2, "0"),
  ].join("-");
};

const startDatum = computed({
  get: (): Date | undefined => toLocalDate(model.value.startZeitpunkt),
  set: (val: Date | null | undefined): void => {
    model.value.startZeitpunkt = toLocalDateString(val);

    if (model.value.endZeitpunkt) {
      endInput.value?.validate();
    }
  },
});

const endDatum = computed({
  get: () => toLocalDate(model.value.endZeitpunkt),
  set: (val) => {
    model.value.endZeitpunkt = toLocalDateString(val);
  },
});

const startZeitpunktRules = [
  validationRules.notEmptyDateRule(
    "Es muss ein Startzeitpunkt angegeben werden."
  ),
  () =>
    model.value.isStartBeforeEnd ||
    `Der Beginn des ${props.label}s muss vor dem Ende liegen.`,
];

const endZeitpunktRules = [
  validationRules.notEmptyDateRule(
    "Es muss ein Endzeitpunkt angegeben werden."
  ),
  () =>
    model.value.isStartBeforeEnd ||
    `Das Ende des ${props.label}s darf nicht vor dem Beginn liegen.`,
];
</script>
