<template>
    <v-text-field
        v-model="namentlicheAnforderung"
        label="Anforderung bestimmter NWK"
        :rules="namentlicheAnforderungRule"
        outlined
    ></v-text-field>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRules } from "@/composables/rules";

const validationRules = useRules();

const props = defineProps<{
    value: string | undefined;
}>();
const emits = defineEmits<{
    (e: "input", dienststelle: string | undefined): void;
}>();

const namentlicheAnforderungRule = [
    validationRules.maxLengthRule(
        255,
        "Der Name der angeforderte NWK darf nicht länger als 255 Zeichen sein."
    ),
];

const namentlicheAnforderung = computed({
    // getter
    get() {
        return props.value;
    },
    // setter
    set(newValue) {
        emits("input", newValue);
    },
});
</script>