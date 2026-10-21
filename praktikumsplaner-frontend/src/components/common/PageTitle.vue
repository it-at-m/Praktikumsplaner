<template>
  <v-row>
    <v-col class="d-flex">
      <v-btn
        v-if="backButtonUrl"
        :to="{ path: backButtonUrl }"
        :icon="mdiArrowLeft"
        elevation="0"
        class="mr-2"
        :data-test="testIds.common.pageTitleBackBtn"
        aria-label="Zur vorherigen Seite zurückkehren"
      />
      <h1
        :data-test="testIds.common.pageTitle"
        class="my-0"
      >
        {{ properties.pageHeaderText }}
      </h1>
    </v-col>
    <v-col
      v-if="$slots.actions"
      cols="12"
      sm="auto"
    >
      <slot name="actions" />
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import { mdiArrowLeft } from "@mdi/js";

import { testIds } from "@/testIds";

const properties = defineProps<{
  pageHeaderText: string;
  backButtonUrl?: string;
}>();
</script>
