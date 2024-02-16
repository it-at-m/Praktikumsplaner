<template>
    <v-select
        v-model="praktikumsstelle.ausbildungsjahr"
        label="Ausbildungsjahr*"
        :items="Ausbildungsjahr"
        item-value="value"
        item-title="name"
        :rules="requiredArrayRule"
        variant="outlined"
        multiple
        @update:model-value="sortAusbildungsjahre"
    >
        <template #prepend-item>
            <v-list-item
                ripple
                @mousedown.prevent
                @click="selectAllAusbildungsjahre"
            >
                <v-list-item-action>
                    <v-icon :color="ausbildungsjahrIconColor()">
                        {{ AusbildungsjahrIcon }}
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
            <v-list-item-subtitle
                v-if="praktikumsstelle.ausbildungsrichtung === 'FISI'"
            >
                {{ data.item.zeitraumFISI }}
            </v-list-item-subtitle>
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

const AusbildungsjahrIcon = computed(() => {
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

function ausbildungsjahrIconColor() {
    return allAusbildungsjahreSelected.value ||
        someAusbildungsjahreSelected.value
        ? "primary"
        : "darkgrey";
}
</script>
