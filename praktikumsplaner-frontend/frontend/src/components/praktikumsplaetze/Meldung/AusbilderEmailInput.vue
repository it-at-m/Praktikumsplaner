<template>
    <v-text-field
        v-model="stelle.email"
        :label="conditionalRequiredLabel"
        :rules="conditionalRequiredRules"
        variant="outlined"
        :clearable="!isRequired"
    ></v-text-field>
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
    (e: "update:modelValue", email: Praktikumsstelle): void;
}>();

const label = "E-mail örtliche*r Ausbilder*in";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.regexRule(
        /^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,63}\.[A-Za-z]{1,63}$/,
        "Keine gültige Email."
    ),
    validationRules.maxLengthRule(
        255,
        "Die Email darf nicht länger als 255 Zeichen sein."
    ),
];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? emailRule : undefined;
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
