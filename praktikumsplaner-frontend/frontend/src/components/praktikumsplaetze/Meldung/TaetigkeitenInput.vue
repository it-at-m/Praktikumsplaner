<template>
    <v-textarea
        v-model="stelle.taetigkeiten"
        :label="conditionalRequiredLabel"
        :rules="conditionalRequiredRules"
        variant="outlined"
        :clearable="!isRequired"
    ></v-textarea>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

interface Properties {
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}

const properties = withDefaults(defineProps<Properties>(), {
    requiredSymbol: "*",
});

const emits = defineEmits<{
    (e: "update:modelValue", dienststelle: Praktikumsstelle): void;
}>();

const label = "Aufgaben am Praktikumsplatz";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const taetigkeitenRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        5000,
        "Tätigkeiten dürfen nicht länger als 5000 Zeichen sein."
    ),
];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? taetigkeitenRule : undefined;
});

const stelle = computed({
    // getter
    get() {
        return properties.modelValue;
    },
    // setter
    set(newValue) {
        emits("update:modelValue", newValue);
    },
});
</script>
