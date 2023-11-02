<template>
    <v-row>
        <v-col cols="6">
            <v-text-field
                ref="endDate"
                v-model="range.startZeitpunkt"
                type="date"
                :rules="startZeitpunktRules"
                prepend-icon="mdi-calendar-start"
                label="Startzeitpunkt"
            >
            </v-text-field>
        </v-col>
        <v-col cols="6">
            <v-text-field
                ref="startDate"
                v-model="range.endZeitpunkt"
                type="date"
                prepend-icon="mdi-calendar-end"
                label="Endzeitpunkt"
                :rules="endZeitpunktRules"
            >
            </v-text-field>
        </v-col>
    </v-row>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRules } from "@/composables/rules";
import Meldezeitraum from "@/types/Meldezeitraum";

const props = defineProps<{
    value: Meldezeitraum;
}>();
const emits = defineEmits<{
    (e: "input", v: Meldezeitraum): void;
}>();

const validationRules = useRules();

const range = computed(() => props.value);

const isStartBeforeEnd = computed(() => {
    return (
        range.value.isStartBeforeEnd ||
        "Das Startdatum muss vor dem Enddatum liegen."
    );
});

const isEndBeforeStart = computed(() => {
    return (
        range.value.isStartBeforeEnd ||
        "Das Enddatum muss nach dem Startdatum liegen."
    );
});

const endZeitpunktRules = computed(() => {
    return [
        validationRules.notEmptyDateRule(
            "Es muss ein Endzeitpunkt angegeben werden"
        ),
        isEndBeforeStart.value,
    ];
});

const startZeitpunktRules = computed(() => {
    return [
        validationRules.notEmptyDateRule(
            "Es muss ein Startzeitpunkt angegeben werden."
        ),
        isStartBeforeEnd.value,
    ];
});
</script>