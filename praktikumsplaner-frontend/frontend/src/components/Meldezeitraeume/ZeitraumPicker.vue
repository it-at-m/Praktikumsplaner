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
                @change="startZeitpunktChange"
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
                @change="endZeitpunktChange"
            >
            </v-text-field>
        </v-col>
    </v-row>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRules } from "@/composables/rules";
import Zeitraum from "@/types/Zeitraum";

const props = defineProps<{
    value: Zeitraum;
}>();
const emits = defineEmits<{
    (e: "input", v: Zeitraum): void;
}>();

const validationRules = useRules();

const range = computed(() => {
    return props.value;
});

const startRef = computed(() => {
    return props.value.startZeitpunkt;
});
const endRef = computed(() => {
    return props.value.endZeitpunkt;
});

const isStartBeforeEnd = computed(() => {
    if (endRef.value == undefined) return true;
    return (
        (startRef.value && new Date(startRef.value) < new Date(endRef.value)) ||
        "Das Startdatum muss vor dem Enddatum liegen."
    );
});

const isEndBeforeStart = computed(() => {
    if (startRef.value == undefined) return true;
    return (
        (endRef.value && new Date(startRef.value) < new Date(endRef.value)) ||
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

function startZeitpunktChange() {
    emits(
        "input",
        new Zeitraum(range.value.startZeitpunkt, range.value.endZeitpunkt)
    );
}

function endZeitpunktChange() {
    emits(
        "input",
        new Zeitraum(range.value.startZeitpunkt, range.value.endZeitpunkt)
    );
}
</script>