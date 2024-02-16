<template>
    <v-select
        v-model="stelle.dringlichkeit"
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
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    value: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "input", dringlichkeit: Praktikumsstelle): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

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
