<template>
    <v-select
        v-model="stelle.ausbildungsrichtung"
        label="Ausbildungsrichtung*"
        :items="Ausbildungsrichtung"
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
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", ausbildungsrichtung: Praktikumsstelle): void;
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
