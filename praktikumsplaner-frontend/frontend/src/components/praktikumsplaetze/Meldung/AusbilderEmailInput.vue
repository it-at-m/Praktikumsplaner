<template>
    <v-text-field
        v-model="email"
        label="E-mail örtliche Ausbilder*in*"
        :rules="emailRule"
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
    (e: "input", email: string): void;
}>();

const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.emailRule("Keine gültige Email."),
    validationRules.maxLengthRule(
        255,
        "Die Email darf nicht länger als 255 Zeichen sein."
    ),
];

const email = computed({
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