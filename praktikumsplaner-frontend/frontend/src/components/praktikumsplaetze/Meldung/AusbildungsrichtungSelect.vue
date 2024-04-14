<template>
    <v-select
        v-model="stelle.ausbildungsrichtung"
        :label="conditionalRequiredLabel"
        :items="Ausbildungsrichtung"
        item-value="value"
        item-title="name"
        :rules="conditionalRequiredRules"
        variant="outlined"
        :clearable="!isRequired"
        :disabled="disabled"
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

interface Properties {
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
    disabled: boolean;
}
const properties = withDefaults(defineProps<Properties>(), {
    requiredSymbol: "*",
    disabled: false,
});

const emits = defineEmits<{
    (e: "update:modelValue", ausbildungsrichtung: Praktikumsstelle): void;
}>();

const label = "Ausbildungsrichtung";
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
