<template>
    <v-row>
        <v-col cols="6">
            <DatePicker
                ref="startDate"
                v-model="range.startZeitpunkt"
                prepend-icon="mdi-calendar-start"
                label="Startzeitpunkt"
                :rules="startZeitpunktRules"
            >
            </DatePicker>
        </v-col>
        <v-col cols="6">
            <DatePicker
                ref="endDate"
                v-model="range.endZeitpunkt"
                prepend-icon="mdi-calendar-end"
                label="Endzeitpunkt"
                :rules="endZeitpunktRules"
            >
            </DatePicker>
        </v-col>
    </v-row>
</template>

<script setup lang="ts">
import DatePicker from "@/components/common/DatePicker.vue";
import { computed, nextTick, ref, watch } from "vue";
import { useRules } from "@/composables/rules";
import Zeitraum from "@/types/Zeitraum";

const props = defineProps<{
    value: Zeitraum;
}>();
const emits = defineEmits<{
    (e: "input", v: Zeitraum): void;
}>();

const validationRules = useRules();
const endDate = ref<DatePicker>();
const startDate = ref<DatePicker>();

const range = computed(() => {
    return props.value;
});

watch(
    () => range.value.startZeitpunkt,
    (startZeitpunkt) => {
        nextTick(endDate.value?.validate);
        emits("input", range.value);
    }
);
watch(
    () => range.value.endZeitpunkt,
    (endZeitpunkt) => {
        nextTick(startDate.value?.validate);
        emits("input", range.value);
    }
);

const endZeitpunktRules = computed(() => {
    return [
        validationRules.notEmptyDateRule(
            "Es muss ein Endzeitpunkt angegeben werden"
        ),
        validationRules.dateAfterRule(
            range.value.startZeitpunkt,
            "Der Endzeitpunkt muss nach dem Startzeitpunkt liegen"
        ),
    ];
});
const startZeitpunktRules = computed(() => {
    return [
        validationRules.notEmptyDateRule(
            "Es muss ein Startzeitpunkt angegeben werden."
        ),
        validationRules.dateBeforeRule(
            range.value.endZeitpunkt,
            "Der Startzeitpunkt muss vor dem Endzeitpunkt liegen"
        ),
    ];
});
</script>