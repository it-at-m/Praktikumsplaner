<template>
    <v-select
        v-model="dringlichkeit"
        label="Dringlichkeit*"
        :items="Dringlichkeit"
        :menu-props="customMenuProps"
        item-value="value"
        item-text="name"
        :rules="rules"
        outlined
    ></v-select>
</template>

<script setup lang="ts">
import { Dringlichkeit } from "@/types/Dringlichkeit";
import { computed } from "vue";

const props = defineProps<{
    value: object;
    rules: object;
}>();
const emits = defineEmits<{
    (e: "input", dringlichkeit: object): void;
}>();

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