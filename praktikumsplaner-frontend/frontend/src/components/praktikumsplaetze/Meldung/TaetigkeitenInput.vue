<template>
    <v-textarea
        v-model="stelle.taetigkeiten"
        :label="conditionalRequiredLabel"
        :rules="conditionalRequiredRules"
        variant="outlined"
    ></v-textarea>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", dienststelle: Praktikumsstelle): void;
}>();

const label = "Aufgaben am Praktikumsplatz";
const conditionalRequiredLabel = computed(() => {
    return props.isRequired ? label + props.requiredSymbol : label;
});

const taetigkeitenRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        5000,
        "Tätigkeiten dürfen nicht länger als 5000 Zeichen sein."
    ),
];
const conditionalRequiredRules = computed(() => {
    return props.isRequired ? taetigkeitenRule : undefined;
});

const stelle = computed({
    // getter
    get() {
        return props.modelValue;
    },
    // setter
    set(newValue) {
        emits("update:modelValue", newValue);
    },
});
</script>
