<template>
    <v-text-field
        v-model="nwk.jahrgang"
        label="Jahrgang"
        :rules="jahrgangRule"
        variant="outlined"
    ></v-text-field>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import NwkCreate from "@/types/NwkCreate";

const validationRules = useRules();

const jahrgangRule = [
    validationRules.notEmptyRule("Der Jahrgang darf nicht leer sein."),
    validationRules.regexRule(
        /^([0-9]{2})\/([0-9]{2})$/,
        "Der Jahrgang muss im Format XX/XX angegeben werden."
    ),
];

const properties = defineProps<{
    modelValue: NwkCreate;
}>();

const emits = defineEmits<{
    (e: "updated", v: NwkCreate): void;
}>();

const nwk = computed({
    get: () => properties.modelValue,
    set: (v) => emits("updated", v),
});
</script>
