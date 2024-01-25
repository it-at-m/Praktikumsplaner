<template>
    <v-select
        v-model="dringlichkeit"
        label="Dringlichkeit*"
        :items="Dringlichkeit"
        :menu-props="customMenuProps"
        item-value="value"
        item-text="name"
        :rules="requiredRule"
        outlined
    ></v-select>
</template>

<script setup lang="ts">
import { Dringlichkeit } from "@/types/Dringlichkeit";
import { computed } from "vue";
import { useRules } from "@/composables/rules";

const validationRules = useRules();

const props = defineProps<{
    value: object;
}>();
const emits = defineEmits<{
    (e: "input", dringlichkeit: object): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

const customMenuProps = {
    offsetY: true,
};
const dringlichkeit = computed({
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