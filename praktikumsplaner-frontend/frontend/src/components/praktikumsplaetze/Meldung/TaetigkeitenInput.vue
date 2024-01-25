<template>
    <v-textarea
        v-model="taetigkeiten"
        label="Aufgaben am Praktikumsplatz*"
        :rules="taetigkeitenRule"
        outlined
    ></v-textarea>
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

const taetigkeitenRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        5000,
        "Tätigkeiten dürfen nicht länger als 5000 Zeichen sein."
    ),
];

const taetigkeiten = computed({
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