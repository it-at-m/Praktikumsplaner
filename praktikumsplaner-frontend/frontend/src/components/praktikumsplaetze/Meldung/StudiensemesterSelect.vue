<template>
    <v-select
        v-model="praktikumsstelle.studiensemester"
        :items="Studiensemester"
        :label="conditionalRequiredLabel"
        multiple
        variant="outlined"
        item-title="name"
        item-value="value"
        :rules="conditionalRequiredRules"
        :clearable="!isRequired"
        :disabled="disabled"
        @update:model-value="sortSemester"
    >
        <template #prepend-item>
            <v-checkbox
                v-model="selectAll"
                label="Egal"
                hide-details
                color="primary"
                :false-icon="semesterIcon"
                :true-icon="semesterIcon"
                :value="allSemesterSelected"
                @click="selectAllStudiensemester"
            >
                <template #label>
                    <v-list-item>
                        <v-list-item-title>Egal</v-list-item-title>
                    </v-list-item>
                </template>
            </v-checkbox>
            <v-divider class="mt-2"></v-divider>
        </template>
        <template #item="{ item, props }">
            <v-list-item v-bind="props">
                <v-list-item-subtitle
                    v-if="praktikumsstelle.studiengang === 'BSC'"
                >
                    {{ item.raw.zeitraumBSC }}
                </v-list-item-subtitle>
                <v-list-item-subtitle
                    v-else-if="praktikumsstelle.studiengang === 'BWI'"
                >
                    {{ item.raw.zeitraumBWI }}
                </v-list-item-subtitle>
                <v-list-item-subtitle
                    v-else-if="praktikumsstelle.studiengang === 'VI'"
                >
                    {{ item.raw.zeitraumVI }}
                </v-list-item-subtitle>
            </v-list-item>
        </template>
    </v-select>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Studiensemester } from "@/types/Studiensemester";

const validationRules = useRules();

interface Properties {
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
    disabled?: boolean;
}

const properties = withDefaults(defineProps<Properties>(), {
    requiredSymbol: "*",
    disabled: false,
});

const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

const label = "Studiensemester";
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

const allSemesterSelected = computed(() => {
    return (
        praktikumsstelle.value.studiensemester?.length ===
        Studiensemester.length
    );
});

const someSemesterSelected = computed(() => {
    return (
        praktikumsstelle.value.studiensemester?.length !== 0 &&
        praktikumsstelle.value.studiensemester?.length !== undefined &&
        !allSemesterSelected.value
    );
});

const semesterIcon = computed(() => {
    if (allSemesterSelected.value) return "mdi-checkbox-marked";
    if (someSemesterSelected.value) return "mdi-minus-box";
    return "mdi-checkbox-blank-outline";
});

function selectAllStudiensemester() {
    if (allSemesterSelected.value) {
        praktikumsstelle.value.studiensemester = [];
    } else {
        praktikumsstelle.value.studiensemester = Studiensemester.map(
            (studiensemester) => studiensemester.value
        );
    }
}

function sortSemester() {
    praktikumsstelle.value.studiensemester?.sort((a, b) => a.localeCompare(b));
}
</script>
