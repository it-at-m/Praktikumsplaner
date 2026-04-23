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
      Z.B. zwingende Volljährigkeit, Vorerfahrungen, Verlängerungswünsche
    </span>
  </v-tooltip>
</template>

<script setup lang="ts">
import { mdiInformation } from "@mdi/js";
</script>
