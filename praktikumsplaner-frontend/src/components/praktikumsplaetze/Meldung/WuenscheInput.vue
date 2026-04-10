<template>
    <v-textarea
        v-model="stelle.wuensche"
        :label="conditionalRequiredLabel"
        :rules="conditionalRequiredRules"
        variant="outlined"
        :clearable="!isRequired"
        :disabled="disabled"
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
    disabled?: boolean;
}

const properties = withDefaults(defineProps<Properties>(), {
    requiredSymbol: "*",
    disabled: false,
});

const emits =
    defineEmits<(e: "update:modelValue", model: Praktikumsstelle) => void>();

const label = "Wünsche";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});
const lengthRule = validationRules.maxLengthRule(
    5000,
    "Wünsche dürfen nicht länger als 5000 Zeichen sein."
);
const requiredRules = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    lengthRule,
];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? requiredRules : [lengthRule];
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
