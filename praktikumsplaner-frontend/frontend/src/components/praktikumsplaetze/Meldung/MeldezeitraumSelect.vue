<template>
    <v-select
        v-model="stelle.meldezeitraumID"
        :label="conditionalRequiredLabel"
        item-value="id"
        item-title="zeitraumName"
        :items="properties.meldezeitraueme"
        :rules="conditionalRequiredRules"
        variant="outlined"
        @select="onClick"
    >
        <template #item="{ props, item }">
            <v-list-item v-bind="props">
                <v-list-item-title>
                    {{
                        formatter.formatDateFromString(
                            item.raw.zeitraum.startZeitpunkt
                        )
                    }}
                    -
                    {{
                        formatter.formatDateFromString(
                            item.raw.zeitraum.endZeitpunkt
                        )
                    }}
                </v-list-item-title>
            </v-list-item>
        </template>
        <template #selection="{ item }">
            {{ item.raw.zeitraumName }} :
            {{
                formatter.formatDateFromString(item.raw.zeitraum.startZeitpunkt)
            }}
            -
            {{ formatter.formatDateFromString(item.raw.zeitraum.endZeitpunkt) }}
        </template>
    </v-select>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useFormatter } from "@/composables/formatter";
import Meldezeitraum from "@/types/Meldezeitraum";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import {useRules} from "@/composables/rules";

const validationRules = useRules();

const properties = defineProps<{
    meldezeitraueme: Meldezeitraum[];
    modelValue: Praktikumsstelle;
    isRequired: boolean;
    requiredSymbol?: string;
}>();

const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

const label = "Meldezeitraum";
const conditionalRequiredLabel = computed(() => {
    return properties.isRequired ? label + properties.requiredSymbol : label;
});

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];
const conditionalRequiredRules = computed(() => {
    return properties.isRequired ? requiredRule : undefined;
});

const formatter = useFormatter();

const stelle = computed({
    // getter
    get() {
        return properties.modelValue;
    },
    // setter
    set(newValue) {
        emits("update:modelValue", newValue);
    },
});

function onClick(item: Meldezeitraum) {
    stelle.value.meldezeitraumID = item.id;
    emits("update:modelValue", stelle.value);
}
</script>
