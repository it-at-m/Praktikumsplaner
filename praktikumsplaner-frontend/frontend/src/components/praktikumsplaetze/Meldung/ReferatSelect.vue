<template>
    <v-select
        v-model="stelle.referat"
        :items="Referat"
        item-value="value"
        item-title="name"
        :label="conditionalRequiredLabel"
        :rules="conditionalRequiredRules"
        variant="outlined"
    ></v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Referat } from "@/types/Referat";

const props = defineProps<{
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

const label = "Referat";
const conditionalRequiredLabel = computed(() => {
    return props.isRequired ? label + props.requiredSymbol : label;
});

const validationRules = useRules();
const notEmptyRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];
const conditionalRequiredRules = computed(() => {
    return props.isRequired ? notEmptyRule : undefined;
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
