<template>
    <v-select
        v-model="referat"
        :items="Referat"
        :menu-props="customMenuProps"
        item-value="value"
        item-text="name"
        label="Referat"
        outlined
    ></v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { Referat } from "@/types/Referat";

const props = defineProps<{
    value: object;
}>();
const emits = defineEmits<{
    (e: "input", dringlichkeit: object): void;
}>();

const customMenuProps = {
    offsetY: true,
};
const referat = computed({
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