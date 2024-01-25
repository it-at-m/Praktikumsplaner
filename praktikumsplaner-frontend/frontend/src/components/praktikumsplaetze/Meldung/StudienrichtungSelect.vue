<template>
    <v-select
        v-model="studiengang"
        label="Studienrichtung*"
        :items="Studiengang"
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
import { Studiengang } from "@/types/Studiengang";

const validationRules = useRules();

const props = defineProps<{
    value: object;
}>();
const emits = defineEmits<{
    (e: "input", studiengang: object): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

const customMenuProps = {
    offsetY: true,
};
const studiengang = computed({
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