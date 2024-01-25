<template>
    <v-select
        v-model="programmierkenntnisse"
        label="Programmierkenntnisse*"
        :items="Programmierkenntnisse"
        :menu-props="customMenuProps"
        :rules="requiredRule"
        item-value="value"
        item-text="name"
        outlined
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { Programmierkenntnisse } from "@/types/YesNoEgalBoolean";
import { useRules } from "@/composables/rules";

const validationRules = useRules();

const props = defineProps<{
    value: object;
}>();
const emits = defineEmits<{
    (e: "input", programmierkenntnisse: object): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

const customMenuProps = {
    offsetY: true,
};
const programmierkenntnisse = computed({
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