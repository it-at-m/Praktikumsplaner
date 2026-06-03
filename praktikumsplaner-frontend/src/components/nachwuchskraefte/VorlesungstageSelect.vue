<template>
  <v-select
    v-model="vorlesungstageSortWrapper"
    :items="days"
    color="primary"
    label="Vorlesungstage"
    item-value="weekDay"
    item-title="germanWeekDay"
    variant="outlined"
    multiple
  >
  </v-select>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

import Day from "@/types/Day";
import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";
import NwkCreate from "@/types/NwkCreate";

const germanWeekdayMapper = new GermanWeekdayMapper();

const days = ref<Day[]>([
  new Day("MONDAY", "Montag"),
  new Day("TUESDAY", "Dienstag"),
  new Day("WEDNESDAY", "Mittwoch"),
  new Day("THURSDAY", "Donnerstag"),
  new Day("FRIDAY", "Freitag"),
]);
const properties = defineProps<{
  modelValue: NwkCreate;
}>();
const emits = defineEmits<(e: "input", nwk: NwkCreate) => void>();

const nwk = computed({
  get: () => properties.modelValue,
  set: (newValue) => emits("input", newValue),
});

const vorlesungstageSortWrapper = computed({
  get() {
    return germanWeekdayMapper.normalizeAndSort(nwk.value.vorlesungstage);
  },
  set(newValue) {
    nwk.value.vorlesungstage = germanWeekdayMapper.normalizeAndSort(newValue);
  },
});
</script>
