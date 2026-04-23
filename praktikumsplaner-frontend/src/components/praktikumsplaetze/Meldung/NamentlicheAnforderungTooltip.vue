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
      >Bei Anforderung einer bestimmten NWK für die Stelle, hier den
      vollständigen Namen eintragen.</span
    >
  </v-tooltip>
</template>

<script setup lang="ts">
import { mdiInformation } from "@mdi/js";
</script>
