<template>
  <v-checkbox
    v-model="model"
    label="Erweitertes Führungszeugnis vorhanden?"
    :disabled="disabled"
    :data-test="testIds.praktikumsstelle.fuehrungszeugnisCheckbox"
  ></v-checkbox>
</template>

<script setup lang="ts">
import { testIds } from "@/testIds";

interface Properties {
  disabled?: boolean;
}
withDefaults(defineProps<Properties>(), {
  disabled: false,
});
const model = defineModel<boolean>();
</script>
