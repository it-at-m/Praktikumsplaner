<template>
    <v-radio-group
        v-model="planstelleVorhanden"
        class="radios custom-label"
        row
        :rules="booleanRule"
    >
        <template #label>
            <span class="custom-label">Planstelle vorhanden*:</span>
        </template>
        <v-radio
            v-for="item in YesNoBoolean"
            :key="item.value"
            :label="item.name"
            :value="item.value"
            class="ml-5"
        ></v-radio>
    </v-radio-group>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRules } from "@/composables/rules";
import { YesNoBoolean } from "@/types/YesNoBoolean";

const validationRules = useRules();

const props = defineProps<{
    value: boolean | undefined;
}>();
const emits = defineEmits<{
    (e: "input", planstelleVorhanden: boolean | undefined): void;
}>();

const booleanRule = [
    validationRules.notEmptyBooleanRule("Darf nicht leer sein."),
];

const planstelleVorhanden = computed({
    // getter
    get() {
        return props.value;
    },
    // setter
    set(newValue) {
        emits("input", newValue);
    },
});
</script>