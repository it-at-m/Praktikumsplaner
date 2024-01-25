<template>
    <v-select
        v-model="ausbildungsrichtung"
        label="Ausbildungsrichtung*"
        :items="Ausbildungsrichtung"
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
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";

const validationRules = useRules();

const props = defineProps<{
    value: string | undefined;
}>();
const emits = defineEmits<{
    (e: "input", ausbildungsrichtung: string | undefined): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

const customMenuProps = {
    offsetY: true,
};
const ausbildungsrichtung = computed({
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