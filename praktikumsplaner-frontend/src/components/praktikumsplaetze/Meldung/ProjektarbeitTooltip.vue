<template>
  <v-tooltip location="right">
    <template #activator="{ props }">
      <v-icon
        color="blue"
        class="mt-3"
        size="large"
        v-bind="props"
        :icon="mdiInformation"
      />
    </template>
    <span
      >Die Projektarbeit stellt die Abschlussarbeit der Auszubildenden dar und
      ist ausschließlich für die Fachrichtung FISI relevant.<br />
      Bei VD-Auszubildenden bitte stets "Nein" auswählen.</span
    >
  </v-tooltip>
</template>

<script setup lang="ts">
import { mdiInformation } from "@mdi/js";
</script>
