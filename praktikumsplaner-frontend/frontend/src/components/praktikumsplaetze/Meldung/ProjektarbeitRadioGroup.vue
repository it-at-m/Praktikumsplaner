<template>
    <v-radio-group
        v-model="stelle.projektarbeit"
        class="radios custom-label"
        inline
        :rules="conditionalRequiredRules"
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

interface Props {
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}
const props = withDefaults(defineProps<Props>(), {
    requiredSymbol: "*",
});

const emits = defineEmits<{
    (e: "update:modelValue", projektarbeit: Praktikumsstelle): void;
}>();

const label = "Projektarbeit";
const conditionalRequiredLabel = computed(() => {
    return props.isRequired ? label + props.requiredSymbol : label;
});

const booleanRule = [
    validationRules.notEmptyBooleanRule("Darf nicht leer sein."),
];
const conditionalRequiredRules = computed(() => {
    return props.isRequired ? booleanRule : undefined;
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

<style scoped>
.custom-label {
    font-size: 18px !important;
}
</style>
