<template>
    <v-select
        v-model="stelle.programmierkenntnisse"
        :label="conditionalRequiredLabel"
        :items="Programmierkenntnisse"
        :rules="conditionalRequiredRules"
        item-value="value"
        item-title="name"
        variant="outlined"
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Programmierkenntnisse } from "@/types/YesNoEgalBoolean";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", programmierkenntnisse: Praktikumsstelle): void;
}>();

const label = "Programmierkenntnisse";
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
