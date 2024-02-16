<template>
    <v-select
        v-model="stelle.programmierkenntnisse"
        label="Programmierkenntnisse*"
        :items="Programmierkenntnisse"
        :menu-props="customMenuProps"
        :rules="requiredRule"
        item-value="value"
        item-text="name"
        outlined
    >
    </v-select>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { Programmierkenntnisse } from "@/types/YesNoEgalBoolean";

const validationRules = useRules();

const props = defineProps<{
    value: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "input", programmierkenntnisse: Praktikumsstelle): void;
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
