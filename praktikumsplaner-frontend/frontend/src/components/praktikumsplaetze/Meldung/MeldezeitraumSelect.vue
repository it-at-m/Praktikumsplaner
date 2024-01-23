<template>
    <v-select
        v-model="zeitraum"
        label="Meldezeitraum*"
        :items="props.meldezeitraueme"
        :menu-props="customMenuProps"
        item-value="id"
        item-text="zeitraumName"
        outlined
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
import Meldezeitraum from "@/types/Meldezeitraum";
import { useFormatter } from "@/composables/formatter";
import { computed } from "vue";

const props = defineProps<{
    meldezeitraueme: Meldezeitraum[];
    value: string;
}>();

const emits = defineEmits<{
    (e: "input", meldezeitraumId: string): void;
}>();

const formatter = useFormatter();

const zeitraum = computed({
    // getter
    get() {
        return props.value;
    },
    // setter
    set(newValue) {
        emits("input", newValue);
    },
});
const customMenuProps = {
    offsetY: true,
};
</script>