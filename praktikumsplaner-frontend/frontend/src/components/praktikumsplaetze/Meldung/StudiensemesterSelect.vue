<template>
    <v-select
        v-model="praktikumsstelle.studiensemester"
        label="Studiensemester*"
        :items="Studiensemester"
        item-value="value"
        item-title="name"
        :rules="requiredArrayRule"
        variant="outlined"
        multiple
        @update:model-value="sortSemester"
    >
        <template #prepend-item>
            <v-list-item
                ripple
                @mousedown.prevent
                @click="selectAllStudiensemester"
            >
                <v-list-item-action>
                    <v-icon :color="semesterIconColor()">
                        {{ semesterIcon }}
                    </v-icon>
                </v-list-item-action>

                <v-list-item-title> Egal </v-list-item-title>
            </v-list-item>
            <v-divider class="mt-2"></v-divider>
        </template>
        <template #item="data">
            <v-list-item-action>
                <v-icon v-if="data.attrs['aria-selected'] === 'true'">
                    mdi-checkbox-marked
                </v-icon>
                <v-icon v-else> mdi-checkbox-blank-outline </v-icon>
            </v-list-item-action>

            <v-list-item-title>
                {{ data.item.name }}
            </v-list-item-title>
            <v-list-item-subtitle v-if="praktikumsstelle.studiengang === 'BSC'">
                {{ data.item.zeitraumBSC }}
            </v-list-item-subtitle>
            <v-list-item-subtitle
                v-else-if="praktikumsstelle.studiengang === 'BWI'"
            >
                {{ data.item.zeitraumBWI }}
            </v-list-item-subtitle>
            <v-list-item-subtitle
                v-else-if="praktikumsstelle.studiengang === 'VI'"
            >
                {{ data.item.zeitraumVI }}
            </v-list-item-subtitle>
        </template>
    </v-select>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Studiensemester } from "@/types/Studiensemester";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

const praktikumsstelle = computed({
    // getter
    get() {
        return props.modelValue;
    },
    // setter
    set(newValue) {
        emits("update:modelValue", newValue);
    },
});

const requiredArrayRule = [
    validationRules.notEmptyArrayRule("Darf nicht leer sein."),
];

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

function semesterIconColor() {
    return allSemesterSelected.value || someSemesterSelected.value
        ? "primary"
        : "darkgrey";
}
</script>
