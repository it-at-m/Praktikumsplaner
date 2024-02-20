<template>
    <v-select
        v-model="stelle.meldezeitraumID"
        label="Meldezeitraum*"
        item-value="id"
        item-title="zeitraumName"
        :items="properties.meldezeitraueme"
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

const properties = defineProps<{
    meldezeitraueme: Meldezeitraum[];
    modelValue: Praktikumsstelle;
}>();

const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

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
