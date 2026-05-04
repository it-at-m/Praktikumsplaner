<template>
  <h1 class="mb-4">
    <v-btn
      v-if="backButtonUrl"
      :to="{ path: backButtonUrl }"
      :icon="mdiArrowLeft"
      elevation="0"
    >
    </v-btn>
    {{ properties.pageHeaderText }}
  </h1>
</template>

<script setup lang="ts">
import { mdiArrowLeft } from "@mdi/js";

const properties = defineProps<{
  pageHeaderText: string;
  backButtonUrl?: string;
}>();
</script>
