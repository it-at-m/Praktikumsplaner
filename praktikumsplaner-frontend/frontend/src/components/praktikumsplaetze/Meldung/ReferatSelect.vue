<template>
    <v-select
        v-model="stelle.referat"
        :items="Referat"
        item-value="value"
        item-title="name"
        label="Referat"
        variant="outlined"
    ></v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Referat } from "@/types/Referat";

const props = defineProps<{
    modelValue: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "update:modelValue", stelle: Praktikumsstelle): void;
}>();

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
