<template>
    <v-select
        v-model="nwk.vorlesungstage"
        :items="days"
        label="Vorlesungstage"
        item-value="weekDay"
        item-text="germanWeekDay"
        outlined
        multiple
        @change="sortVorlesungstage"
    >
    </v-select>
</template>

<script setup lang="ts">
import NwkCreate from "@/types/NwkCreate";
import { computed, ref } from "vue";
import Day from "@/types/Day";
import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";

const germanWeekdayMapper = new GermanWeekdayMapper();

const days = ref<Day[]>([
    new Day("MONDAY", "Montag"),
    new Day("TUESDAY", "Dienstag"),
    new Day("WEDNESDAY", "Mittwoch"),
    new Day("THURSDAY", "Donnerstag"),
    new Day("FRIDAY", "Freitag"),
]);
const props = defineProps<{
    value: NwkCreate;
}>();
const emits = defineEmits<{
    (e: "input", nwk: NwkCreate): void;
}>();

const nwk = computed({
    get: () => props.value,
    set: (newValue) => emits("input", newValue),
});

function sortVorlesungstage() {
    germanWeekdayMapper.getGermanDays(nwk.value.vorlesungstage).sort();
}
</script>

<style scoped>

</style>