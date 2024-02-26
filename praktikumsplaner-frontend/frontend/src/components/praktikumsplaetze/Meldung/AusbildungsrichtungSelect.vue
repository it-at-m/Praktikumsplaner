<template>
    <v-select
        v-model="stelle.ausbildungsrichtung"
        :label="conditionalRequiredLabel"
        :items="Ausbildungsrichtung"
        item-value="value"
        item-title="name"
        :rules="conditionalRequiredRules"
        variant="outlined"
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", ausbildungsrichtung: Praktikumsstelle): void;
}>();

const label = "Ausbildungsrichtung";
const conditionalRequiredLabel = computed(() => {
    return props.isRequired ? label + props.requiredSymbol : label;
});

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];
const conditionalRequiredRules = computed(() => {
    return props.isRequired ? requiredRule : undefined;
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
