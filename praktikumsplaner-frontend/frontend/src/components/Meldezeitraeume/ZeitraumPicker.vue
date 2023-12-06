<template>
    <v-row>
        <v-col>
            <v-row>
                <v-col cols="12">
                    <v-text-field
                        ref="endDate"
                        v-model="range.startZeitpunkt"
                        dense
                        outlined
                        type="date"
                        :rules="startZeitpunktRules"
                        :append-icon="calendarIcon"
                        label="Beginn des Meldezeitraums"
                    >
                    </v-text-field>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="12">
                    <v-text-field
                        ref="startDate"
                        v-model="range.endZeitpunkt"
                        dense
                        outlined
                        type="date"
                        :append-icon="calendarIcon"
                        label="Ende des Meldezeitraums"
                        :rules="endZeitpunktRules"
                    >
                    </v-text-field>
                </v-col>
            </v-row>
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

const validationRules = useRules();
const calendarIcon = "mdi-calendar";

const range = computed(() => props.value);

const isStartBeforeEnd = computed(() => {
    return (
        range.value.isStartBeforeEnd ||
        "Der Beginn des Meldezeitraums muss vor dem Ende liegen."
    );
});

const isEndBeforeStart = computed(() => {
    return (
        range.value.isStartBeforeEnd ||
        "Das Ende des Meldezeitraums muss vor dem Beginn liegen."
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