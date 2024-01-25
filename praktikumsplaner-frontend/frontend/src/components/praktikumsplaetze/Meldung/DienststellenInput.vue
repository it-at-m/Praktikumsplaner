<template>
    <v-text-field
        v-model="dienststelle"
        label="Konkrete Dienststelle*"
        :rules="dienststelleRule"
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
    (e: "input", dienststelle: string): void;
}>();

const dienststelleRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.maxLengthRule(
        10,
        "Die Dienststelle darf nicht länger als 10 Zeichen sein."
    ),
];

const dienststelle = computed({
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