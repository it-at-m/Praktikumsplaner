<template>
  <v-tooltip location="right">
    <template #activator="{ props }">
      <v-icon
        class="mt-3"
        color="blue"
        size="large"
        v-bind="props"
        :icon="mdiInformation"
      />
    </template>
    <span>
      Hierfür ist ggf. die Anforderung eines erweiterten Führungszeugnisses
      erforderlich.
    </span>
  </v-tooltip>
</template>

<script setup lang="ts">
import { mdiInformation } from "@mdi/js";
</script>
