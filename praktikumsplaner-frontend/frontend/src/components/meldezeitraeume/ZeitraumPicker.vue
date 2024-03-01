<template>
    <v-row>
        <v-col>
            <v-row>
                <v-col cols="12">
                    <v-text-field
                        ref="startZeitpunktInput"
                        v-model="range.startZeitpunkt"
                        density="compact"
                        variant="outlined"
                        type="date"
                        :label="'Beginn des ' + props.label + 's'"
                        :rules="startZeitpunktRules"
                    >
                    </v-text-field>
                </v-col>
                <v-col cols="12">
                    <v-text-field
                        ref="endZeitpunktInput"
                        v-model="range.endZeitpunkt"
                        density="compact"
                        variant="outlined"
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
import { computed, ref } from "vue";

import { useRules } from "@/composables/rules";
import Zeitraum from "@/types/Zeitraum";

const props = defineProps<{
    modelValue: Zeitraum;
    label: string;
}>();

const validationRules = useRules();
const startZeitpunktInput = ref<HTMLFormElement>();
const endZeitpunktInput = ref<HTMLFormElement>();

const range = computed(() => props.modelValue);

const isStartBeforeEnd = computed(() => {
    if (range.value.startZeitpunkt) startZeitpunktInput.value?.validate();
    return (
        range.value.isStartBeforeEnd ||
        "Der Beginn des " + props.label + "s muss vor dem Ende liegen."
    );
});

const isEndAfterStart = computed(() => {
    if (range.value.endZeitpunkt) endZeitpunktInput.value?.validate();
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
