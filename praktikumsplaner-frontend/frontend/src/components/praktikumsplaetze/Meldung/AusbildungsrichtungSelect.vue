<template>
    <v-select
        v-model="stelle.ausbildungsrichtung"
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
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();

const props = defineProps<{
    value: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "input", ausbildungsrichtung: Praktikumsstelle): void;
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