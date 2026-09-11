<template>
  <v-row>
    <v-col class="d-flex flex-row">
      <v-btn
        v-if="backButtonUrl"
        :to="{ path: backButtonUrl }"
        :icon="mdiArrowLeft"
        elevation="0"
        class="mr-2"
        :data-test="testIds.common.pageTitleBackBtn"
      >
      </v-btn>
      <h1 :data-test="testIds.common.pageTitle">
        {{ properties.pageHeaderText }}
      </h1>
    </v-col>
    <v-col
      cols="4"
      class="d-flex flex-row align-center justify-end"
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
