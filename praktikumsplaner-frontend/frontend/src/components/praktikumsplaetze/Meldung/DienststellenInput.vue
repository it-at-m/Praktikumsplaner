<template>
    <v-text-field
        v-model="stelle.dienststelle"
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

interface Props {
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}
const props = withDefaults(defineProps<Props>(), {
    requiredSymbol: "*",
});

const emits = defineEmits<{
    (e: "update:modelValue", dienststelle: Praktikumsstelle): void;
}>();

const label = "Konkrete Dienststelle";
const conditionalRequiredLabel = computed(() => {
    return props.isRequired ? label + props.requiredSymbol : label;
});

const dienststelleRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        10,
        "Die Dienststelle darf nicht länger als 10 Zeichen sein."
    ),
];
const conditionalRequiredRules = computed(() => {
    return props.isRequired ? dienststelleRule : undefined;
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
