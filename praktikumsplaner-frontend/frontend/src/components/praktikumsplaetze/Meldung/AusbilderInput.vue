<template>
    <v-text-field
        v-model="stelle.oertlicheAusbilder"
        :rules="conditionalRequiredRules"
        :label="conditionalRequiredLabel"
        variant="outlined"
        :clearable="!isRequired"
        :disabled="disabled"
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
    disabled?: boolean;
}
const properties = withDefaults(defineProps<Properties>(), {
    requiredSymbol: "*",
    disabled: false,
});

const emits = defineEmits<{
    (e: "update:modelValue", oertlicheAusbilder: Praktikumsstelle): void;
}>();

const label = "Name örtliche*r Ausbilder*in";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const oertlAusbilderRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        255,
        "Örtliche Ausbilder*in darf nicht länger als 255 Zeichen sein."
    ),
];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? oertlAusbilderRule : undefined;
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
