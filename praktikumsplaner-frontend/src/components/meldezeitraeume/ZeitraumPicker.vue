<template>
  <v-row>
    <v-col>
      <v-row>
        <v-col cols="12">
          <v-date-input
            ref="startInput"
            v-model="model.startZeitpunkt"
            prepend-icon=""
            :append-inner-icon="mdiCalendarOutline"
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
            v-model="model.endZeitpunkt"
            prepend-icon=""
            :append-inner-icon="mdiCalendarOutline"
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
import { mdiCalendarOutline } from "@mdi/js";
import { useTemplateRef } from "vue";

import { useRules } from "@/composables/rules";
import { testIds } from "@/testIds";
import Zeitraum from "@/types/Zeitraum";

const props = defineProps<{
  label: string;
}>();

const model = defineModel<Zeitraum>({ required: true });

const validationRules = useRules();
const startInput = useTemplateRef("startInput");
const endInput = useTemplateRef("endInput");

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
