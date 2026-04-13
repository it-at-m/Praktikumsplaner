<template>
  <v-checkbox
    v-model="stelle.erwFuehrungszeugnisVorhanden"
    :label="label"
    :disabled="disabled"
  ></v-checkbox>
</template>

<script setup lang="ts">
import { computed } from "vue";

import Praktikumsstelle from "@/types/Praktikumsstelle";

interface Properties {
  modelValue: Praktikumsstelle;
  disabled?: boolean;
}
const properties = withDefaults(defineProps<Properties>(), {
  disabled: false,
});

const emits =
  defineEmits<
    (
      e: "update:modelValue",
      erwFuehrungszeugnisVorhanden: Praktikumsstelle
    ) => void
  >();

const label = "Erweitertes Führungszeugnis vorhanden?";

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
