<template>
  <v-row class="mb-4">
    <v-col cols="9" class="d-flex flex-row">
      <v-btn
        v-if="backButtonUrl"
        :to="{ path: backButtonUrl }"
        :icon="mdiArrowLeft"
        elevation="0"
        class="mr-2"
      >
      </v-btn>
      <h1>{{ properties.pageHeaderText }}</h1>
    </v-col>
    <v-col class="d-flex justify-end align-center">
      <v-btn-group>
        <slot name="actions" />
      </v-btn-group>
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import { mdiArrowLeft } from "@mdi/js";

const properties = defineProps<{
  pageHeaderText: string;
  backButtonUrl?: string;
}>();
</script>
