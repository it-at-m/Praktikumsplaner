<template>
    <v-select
        v-model="stelle.programmierkenntnisse"
        :label="conditionalRequiredLabel"
        :items="Programmierkenntnisse"
        :rules="conditionalRequiredRules"
        item-value="value"
        item-title="name"
        variant="outlined"
        :clearable="!isRequired"
        :disabled="disabled"
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Programmierkenntnisse } from "@/types/YesNoEgalBoolean";

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
    (e: "update:modelValue", programmierkenntnisse: Praktikumsstelle): void;
}>();

const label = "Programmierkenntnisse";
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
