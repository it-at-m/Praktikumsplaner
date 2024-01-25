<template>
    <v-select
        v-model="studiensemester"
        label="Studiensemester*"
        :items="Studiensemester"
        item-value="value"
        item-text="name"
        :rules="requiredRule"
        :menu-props="customMenuProps"
        outlined
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRules } from "@/composables/rules";
import { Studiensemester } from "@/types/Studiensemester";

const validationRules = useRules();

const props = defineProps<{
    value: object;
}>();
const emits = defineEmits<{
    (e: "input", studiensemester: object): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

const customMenuProps = {
    offsetY: true,
};
const studiensemester = computed({
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