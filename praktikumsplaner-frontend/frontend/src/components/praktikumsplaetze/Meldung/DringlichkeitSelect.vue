<template>
    <v-select
        v-model="stelle.dringlichkeit"
        :label="conditionalRequiredLabel"
        :items="Dringlichkeit"
        item-value="value"
        item-title="name"
        :rules="conditionalRequiredRules"
        variant="outlined"
    ></v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { Dringlichkeit } from "@/types/Dringlichkeit";
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
    (e: "update:modelValue", dringlichkeit: Praktikumsstelle): void;
}>();

const label = "Dringlichkeit";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? requiredRule : undefined;
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
