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
    <span>Die Projektarbeit ist die Abschlussarbeit der Auszubildenden</span>
  </v-tooltip>
</template>

<script setup lang="ts">
import { mdiInformation } from "@mdi/js";
</script>
