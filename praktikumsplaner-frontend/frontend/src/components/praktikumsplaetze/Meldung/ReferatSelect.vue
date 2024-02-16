<template>
    <v-select
        v-model="stelle.referat"
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

import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Referat } from "@/types/Referat";

const props = defineProps<{
    value: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "input", stelle: Praktikumsstelle): void;
}>();

const customMenuProps = {
    offsetY: true,
};
const stelle = computed({
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
