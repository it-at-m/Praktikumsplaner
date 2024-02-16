<template>
    <v-text-field
        v-model="stelle.namentlicheAnforderung"
        label="Anforderung bestimmter NWK"
        :rules="namentlicheAnforderungRule"
        variant="outlined"
    ></v-text-field>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", dienststelle: Praktikumsstelle): void;
}>();

const namentlicheAnforderungRule = [
    validationRules.maxLengthRule(
        255,
        "Der Name der angeforderte NWK darf nicht länger als 255 Zeichen sein."
    ),
];

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
