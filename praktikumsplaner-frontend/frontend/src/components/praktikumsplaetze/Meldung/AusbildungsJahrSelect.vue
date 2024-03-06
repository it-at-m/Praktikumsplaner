<template>
    <v-select
        v-model="praktikumsstelle.ausbildungsjahr"
        :items="Ausbildungsjahr"
        :label="conditionalRequiredLabel"
        multiple
        variant="outlined"
        item-title="name"
        item-value="value"
        :rules="conditionalRequiredRules"
        @update:model-value="sortAusbildungsjahre"
    >
        <template #prepend-item>
            <v-checkbox
                v-model="selectAll"
                label="Egal"
                hide-details
                base-color="primary"
                color="primary"
                :false-icon="ausbildungsjahrIcon"
                :true-icon="ausbildungsjahrIcon"
                :value="allAusbildungsjahreSelected"
                @click="selectAllAusbildungsjahre"
            >
                <template #label>
                    <v-list-item>
                        <v-list-item-title> Egal </v-list-item-title>
                    </v-list-item>
                </template>
            </v-checkbox>
            <v-divider class="mt-2"></v-divider>
        </template>
        <template #item="{ item, props }">
            <v-list-item v-bind="props">
                <v-list-item-subtitle
                    v-if="praktikumsstelle.ausbildungsrichtung === 'FISI'"
                >
                    {{ item.raw.zeitraumFISI }}
                </v-list-item-subtitle>
            </v-list-item>
        </template>
    </v-select>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

interface Properties {
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}
const properties = withDefaults(defineProps<Properties>(), {
    requiredSymbol: "*",
});

const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

const label = "Ausbildungsjahr";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const selectAll = true;

const praktikumsstelle = computed({
    // getter
    get() {
        return properties.modelValue;
    },
    // setter
    set(newValue) {
        emits("update:modelValue", newValue);
    },
});

const requiredArrayRule = [
    validationRules.notEmptyArrayRule("Darf nicht leer sein."),
];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? requiredArrayRule : undefined;
});

const allAusbildungsjahreSelected = computed(() => {
    return (
        praktikumsstelle.value.ausbildungsjahr?.length ===
        Ausbildungsjahr.length
    );
});

const someAusbildungsjahreSelected = computed(() => {
    return (
        praktikumsstelle.value.ausbildungsjahr?.length !== 0 &&
        praktikumsstelle.value.ausbildungsjahr?.length !== undefined &&
        !allAusbildungsjahreSelected.value
    );
});

const ausbildungsjahrIcon = computed(() => {
    if (allAusbildungsjahreSelected.value) return "mdi-checkbox-marked";
    if (someAusbildungsjahreSelected.value) return "mdi-minus-box";
    return "mdi-checkbox-blank-outline";
});

function selectAllAusbildungsjahre() {
    if (allAusbildungsjahreSelected.value) {
        praktikumsstelle.value.ausbildungsjahr = [];
    } else {
        praktikumsstelle.value.ausbildungsjahr = Ausbildungsjahr.map(
            (ausbildungsjahr) => ausbildungsjahr.value
        );
    }
}

function sortAusbildungsjahre() {
    praktikumsstelle.value.ausbildungsjahr?.sort((a, b) => a.localeCompare(b));
}
</script>
