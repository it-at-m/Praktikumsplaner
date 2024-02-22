<template>
    <v-select
        v-model="stelle.studiengang"
        label="Studienrichtung*"
        :items="Studiengang"
        item-value="value"
        item-title="name"
        :rules="requiredRule"
        variant="outlined"
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Studiengang } from "@/types/Studiengang";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", studiengang: Praktikumsstelle): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

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
