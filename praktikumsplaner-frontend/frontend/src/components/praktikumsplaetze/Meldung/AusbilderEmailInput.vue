<template>
    <v-text-field
        v-model="stelle.email"
        label="E-mail örtliche Ausbilder*in*"
        :rules="emailRule"
        outlined
    ></v-text-field>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    value: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "input", email: Praktikumsstelle): void;
}>();

const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein."),
    validationRules.regexRule(
        /^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,63}\.[A-Za-z]{1,63}$/,
        "Keine gültige Email."
    ),
    validationRules.maxLengthRule(
        255,
        "Die Email darf nicht länger als 255 Zeichen sein."
    ),
];

const stelle = computed({
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