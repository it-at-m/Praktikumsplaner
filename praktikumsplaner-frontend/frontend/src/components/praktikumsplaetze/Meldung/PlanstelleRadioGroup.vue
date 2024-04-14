<template>
    <v-radio-group
        v-model="stelle.planstelleVorhanden"
        class="radios custom-label"
        inline
        :rules="conditionalRequiredRules"
        :disabled="disabled"
    >
        <template #label>
            <span class="custom-label">{{ conditionalRequiredLabel }}:</span>
        </template>
        <v-radio
            v-for="item in YesNoBoolean"
            :key="item.name"
            :label="item.name"
            :value="item.value"
            class="ml-5"
        ></v-radio>
    </v-radio-group>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { YesNoBoolean } from "@/types/YesNoBoolean";

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
    (e: "update:modelValue", planstelleVorhanden: Praktikumsstelle): void;
}>();

const label = "Planstelle vorhanden";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const booleanRule = [
    validationRules.notEmptyBooleanRule("Darf nicht leer sein."),
];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? booleanRule : undefined;
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

<style scoped>
.custom-label {
    font-size: 18px !important;
}
</style>
