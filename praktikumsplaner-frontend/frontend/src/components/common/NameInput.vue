<script setup lang="ts">
import { useRules } from "@/composables/rules";
import NwkCreate from "@/types/NwkCreate";
import { computed } from "vue";

const validationRules = useRules();

const nameRule = [
    validationRules.minLengthRule(
        2,
        "Der Name muss mindestens 2 Zeichen lang sein."
    ),
    validationRules.maxLengthRule(
        255,
        "Der Name darf maximal 255 Zeichen lang sein."
    ),
    validationRules.notEmptyRule("Der Name darf nicht leer sein."),
];

const props = defineProps<{
    nwk: NwkCreate;
    type: string;
}>();

const emits = defineEmits<{
    (e: "updated", v: NwkCreate): void;
}>();

const propHandler = computed({
    get: () => props.nwk,
    set: (v) => emits("updated", v),
});
</script>

<template>
    <v-text-field
        v-if="type == 'vorname'"
        v-model="propHandler.vorname"
        label="Vorname"
        :rules="nameRule"
        outlined
    ></v-text-field>
    <v-text-field
        v-else-if="type == 'nachname'"
        v-model="propHandler.nachname"
        label="Nachname"
        :rules="nameRule"
        outlined
    ></v-text-field>
</template>