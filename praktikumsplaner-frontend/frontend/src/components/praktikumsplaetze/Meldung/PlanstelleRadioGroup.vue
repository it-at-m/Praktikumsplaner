<template>
    <v-radio-group
        v-model="stelle.planstelleVorhanden"
        class="radios custom-label"
        inline
        :rules="booleanRule"
    >
        <template #label>
            <span class="custom-label">Planstelle vorhanden*:</span>
        </template>
        <v-radio
            v-for="item in YesNoBoolean"
            :key="item.name"
            :label="item.name"
            :value="item.value"
            class="ml-5"
        ></v-radio>
    </v-radio-group>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { YesNoBoolean } from "@/types/YesNoBoolean";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();

const emits = defineEmits<{
    (e: "update:modelValue", planstelleVorhanden: Praktikumsstelle): void;
}>();

const booleanRule = [
    validationRules.notEmptyBooleanRule("Darf nicht leer sein."),
];

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

<style scoped>
.custom-label {
    font-size: 18px !important;
}
</style>
