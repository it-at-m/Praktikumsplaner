<template>
    <v-select
        v-model="ausbildungsjahr"
        label="Ausbildungsjahr*"
        :items="Ausbildungsjahr"
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
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";

const validationRules = useRules();

const props = defineProps<{
    value: string | undefined;
}>();
const emits = defineEmits<{
    (e: "input", ausbildungsjahr: string | undefined): void;
}>();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein.")];

const customMenuProps = {
    offsetY: true,
};
const ausbildungsjahr = computed({
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