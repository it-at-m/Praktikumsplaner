<template>
  <v-text-field
    v-model="stelle.dienststelle"
    :disabled="disabled"
    :label="conditionalRequiredLabel"
    persistent-hint
    :hint="hint"
    :rules="conditionalRequiredRules"
    variant="outlined"
    :clearable="!isRequired"
  ></v-text-field>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useRules } from "@/composables/rules";
import { useSecurity } from "@/composables/security";
import { useUserStore } from "@/stores/user";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const validationRules = useRules();
const userStore = useUserStore();
const security = useSecurity();

interface Properties {
  modelValue: Praktikumsstelle;
  isRequired: boolean;
  requiredSymbol?: string;
  disabled?: boolean;
}
const properties = withDefaults(defineProps<Properties>(), {
  requiredSymbol: "*",
  disabled: false,
});

const emits =
  defineEmits<
    (e: "update:modelValue", dienststelle: Praktikumsstelle) => void
  >();

const label = "Konkrete Dienststelle";
const conditionalRequiredLabel = computed(() => {
  return properties.isRequired ? label + properties.requiredSymbol : label;
});

const dienststelleRule = [
  validationRules.notEmptyRule("Darf nicht leer sein."),
  validationRules.maxLengthRule(
    10,
    "Die Dienststelle darf nicht länger als 10 Zeichen sein."
  ),
  validationRules.regexRule(
    /^[A-Z]{3,4}-[A-Za-z\d-]+$/,
    "Die Dienststelle muss der Kurzform entsprechen (z.B. ITM-DKL123)"
  ),
];
const conditionalRequiredRules = computed(() => {
  return properties.isRequired ? dienststelleRule : undefined;
});

const hint = computed(() => {
  if (security.isAusbildungsleitung()) {
    return "";
  }
  if (
    !properties.modelValue.dienststelle?.startsWith(userStore.department || "")
  ) {
    return `Die Praktikumsstelle sollte unterhalb der eigenen Dienststelle ('${userStore.department}') angesiedelt sein`;
  }
  return "";
});
const stelle = computed({
  // getter
  get() {
    return properties.modelValue;
  },
  // setter
  set(newValue) {
    emits("update:modelValue", newValue);
  },
});
</script>
