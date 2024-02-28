<template>
    <v-text-field
        v-model="stelle.namentlicheAnforderung"
        :label="conditionalRequiredLabel"
        :rules="conditionalRequiredRules"
        variant="outlined"
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
    (e: "update:modelValue", dienststelle: Praktikumsstelle): void;
}>();

const label = "Anforderung bestimmter NWK";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const namentlicheAnforderungRule = [
    validationRules.maxLengthRule(
        255,
        "Der Name der angeforderte NWK darf nicht länger als 255 Zeichen sein."
    ),
];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? namentlicheAnforderungRule : undefined;
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
