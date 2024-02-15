<template>
    <v-row>
        <v-col>
            <v-row>
                <v-col cols="12">
                    <v-text-field
                        v-model="range.startZeitpunkt"
                        dense
                        outlined
                        type="date"
                        :rules="startZeitpunktRules"
                        :label="'Beginn des ' + props.label + 's'"
                    >
                    </v-text-field>
                </v-col>
                <v-col cols="12">
                    <v-text-field
                        v-model="range.endZeitpunkt"
                        dense
                        outlined
                        type="date"
                        :label="'Ende des ' + props.label + 's'"
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
import Zeitraum from "@/types/Zeitraum";

const props = defineProps<{
    value: Zeitraum;
    label: string;
}>();

const validationRules = useRules();

const range = computed(() => props.value);

const isStartBeforeEnd = computed(() => {
    return (
        range.value.isStartBeforeEnd ||
        "Der Beginn des " + props.label + "s muss vor dem Ende liegen."
    );
});

const isEndAfterStart = computed(() => {
    return (
        range.value.isStartBeforeEnd ||
        "Das Ende des " + props.label + "s darf nicht vor dem Beginn liegen."
    );
});

const endZeitpunktRules = computed(() => {
    return [
        validationRules.notEmptyDateRule(
            "Es muss ein Endzeitpunkt angegeben werden"
        ),
        isEndAfterStart.value,
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