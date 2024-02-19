<template>
    <v-select
        v-model="praktikumsstelle.ausbildungsjahr"
        :items="Ausbildungsjahr"
        label="Ausbildungsjahr*"
        multiple
        variant="outlined"
        item-title="name"
        item-value="value"
        :rules="requiredArrayRule"
        @update:model-value="sortAusbildungsjahre"
    >
        <template #prepend-item>
            <v-checkbox
                v-model="selectAll"
                label="Egal"
                hide-details
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
        <template #item="{ item }">
            <v-checkbox
                :model-value="isSelected(item)"
                hide-details
                @click="toggleSelection(item)"
            >
                <template #label>
                    <v-list-item>
                        <v-list-item-title>
                            {{ item.title }}
                        </v-list-item-title>
                        <v-list-item-subtitle
                            v-if="
                                praktikumsstelle.ausbildungsrichtung === 'FISI'
                            "
                        >
                            {{ item.raw.zeitraumFISI }}
                        </v-list-item-subtitle>
                    </v-list-item>
                </template>
            </v-checkbox>
        </template>
    </v-select>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

const selectAll = true;

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

function toggleSelection(item: {
    name: string;
    value: string;
    zeitraumFISI: string;
}) {
    if (praktikumsstelle.value.ausbildungsjahr) {
        const index = praktikumsstelle.value.ausbildungsjahr.findIndex(
            (selectedItem) => selectedItem === item.value
        );
        if (index > -1) {
            praktikumsstelle.value.ausbildungsjahr.splice(index, 1);
        } else {
            praktikumsstelle.value.ausbildungsjahr.push(item.value);
        }
    } else {
        praktikumsstelle.value.ausbildungsjahr = [];
        praktikumsstelle.value.ausbildungsjahr.push(item.value);
    }
}

function isSelected(item: {
    name: string;
    value: string;
    zeitraumFISI: string;
}) {
    return praktikumsstelle.value.ausbildungsjahr?.some(
        (selectedItem) => selectedItem === item.value
    );
}
</script>
