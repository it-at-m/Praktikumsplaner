<template>
    <v-select
        v-model="praktikumsstelleInternal.ausbildungsjahr"
        label="Ausbildungsjahr*"
        :items="Ausbildungsjahr"
        item-value="value"
        item-text="name"
        :rules="requiredArrayRule"
        :menu-props="customMenuProps"
        outlined
        multiple
        @change="
            () => {
                sortAusbildungsjahre();
                emit('update:praktikumsstelle', praktikumsstelleInternal);
            }
        "
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
                <v-list-item-content>
                    <v-list-item-title> Egal </v-list-item-title>
                </v-list-item-content>
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
            <v-list-item-content>
                <v-list-item-title>
                    {{ data.item.name }}
                </v-list-item-title>
                <v-list-item-subtitle
                    v-if="praktikumsstelle.ausbildungsrichtung === 'FISI'"
                >
                    {{ data.item.zeitraumFISI }}
                </v-list-item-subtitle>
            </v-list-item-content>
        </template>
    </v-select>
</template>
<script setup lang="ts">
import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { computed } from "vue";
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";

const validationRules = useRules();

const props = defineProps<{
    praktikumsstelle: Praktikumsstelle;
}>();
const emit = defineEmits<{
    (e: "update:praktikumsstelle", b: Praktikumsstelle): void;
}>();
const praktikumsstelleInternal = computed(() => props.praktikumsstelle);

const requiredArrayRule = [
    validationRules.notEmptyArrayRule("Darf nicht leer sein."),
];
const customMenuProps = {
    offsetY: true,
};

const allAusbildungsjahreSelected = computed(() => {
    return (
        praktikumsstelleInternal.value.ausbildungsjahr?.length ===
        Ausbildungsjahr.length
    );
});

const someAusbildungsjahreSelected = computed(() => {
    return (
        praktikumsstelleInternal.value.ausbildungsjahr?.length !== 0 &&
        praktikumsstelleInternal.value.ausbildungsjahr?.length !== undefined &&
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
        praktikumsstelleInternal.value.ausbildungsjahr = [];
    } else {
        praktikumsstelleInternal.value.ausbildungsjahr = Ausbildungsjahr.map(
            (ausbildungsjahr) => ausbildungsjahr.value
        );
    }
}

function sortAusbildungsjahre() {
    praktikumsstelleInternal.value.ausbildungsjahr?.sort((a, b) =>
        a.localeCompare(b)
    );
}

function ausbildungsjahrIconColor() {
    return allAusbildungsjahreSelected.value ||
        someAusbildungsjahreSelected.value
        ? "primary"
        : "darkgrey";
}
</script>