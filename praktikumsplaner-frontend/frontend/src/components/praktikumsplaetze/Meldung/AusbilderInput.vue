<template>
    <v-text-field
        v-model="oertlicheAusbilder"
        label="Name örtliche Ausbilder*in*"
        :rules="oertlAusbilderRule"
        outlined
    ></v-text-field>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRules } from "@/composables/rules";

const validationRules = useRules();

const props = defineProps<{
    value: string;
}>();
const emits = defineEmits<{
    (e: "input", oertlicheAusbilder: string): void;
}>();

const oertlAusbilderRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        255,
        "Örtliche Ausbilder*in darf nicht länger als 255 Zeichen sein."
    ),
];

const oertlicheAusbilder = computed({
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