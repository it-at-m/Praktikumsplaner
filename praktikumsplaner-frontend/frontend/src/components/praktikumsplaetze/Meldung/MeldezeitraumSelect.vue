<template>
    <v-select
        v-model="stelle.meldezeitraumID"
        label="Meldezeitraum*"
        :items="props.meldezeitraueme"
        item-value="id"
        item-title="zeitraumName"
        variant="outlined"
    >
        <template #item="data">
            {{ data.item.zeitraumName }}:
            {{
                formatter.formatDateFromString(
                    data.item.zeitraum.startZeitpunkt
                )
            }}
            -
            {{
                formatter.formatDateFromString(data.item.zeitraum.endZeitpunkt)
            }}
        </template>
    </v-select>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { useFormatter } from "@/composables/formatter";
import Meldezeitraum from "@/types/Meldezeitraum";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const props = defineProps<{
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
        return props.modelValue;
    },
    // setter
    set(newValue) {
        emits("update:modelValue", newValue);
    },
});
</script>
