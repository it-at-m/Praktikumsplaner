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

interface Props {
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}
const props = withDefaults(defineProps<Props>(), {
    requiredSymbol: "*",
});

const emits = defineEmits<{
    (e: "update:modelValue", dringlichkeit: Praktikumsstelle): void;
}>();

const label = "Dringlichkeit";
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
