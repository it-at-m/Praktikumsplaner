<template>
    <v-text-field
        v-model="nwk.jahrgang"
        label="Jahrgang"
        :rules="jahrgangRule"
        outlined
    ></v-text-field>
</template>
<script setup lang="ts">
import { useRules } from "@/composables/rules";
import NwkCreate from "@/types/NwkCreate";
import { computed } from "vue";

const validationRules = useRules();

const jahrgangRule = [
    validationRules.notEmptyRule("Der Jahrgang darf nicht leer sein."),
    validationRules.regexRule(
        /^([0-9]{2})\/([0-9]{2})$/,
        "Der Jahrgang muss im Format XX/XX angegeben werden."
    ),
];

const props = defineProps<{
    value: NwkCreate;
}>();

const emits = defineEmits<{
    (e: "updated", v: NwkCreate): void;
}>();

const nwk = computed({
    get: () => props.value,
    set: (v) => emits("updated", v),
});
</script>
