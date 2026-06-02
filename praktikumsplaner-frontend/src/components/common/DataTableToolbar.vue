<template>
  <v-row>
    <v-col cols="3">
      <v-text-field
        v-model="searchModel"
        variant="outlined"
        density="comfortable"
        label="Suche"
        hide-details
        clearable
      />
    </v-col>
    <v-col
      v-if="(groupByOptions?.length ?? 0) > 0"
      cols="2"
    >
      <v-select
        v-model="groupByRawModel"
        variant="outlined"
        density="comfortable"
        label="Gruppierung"
        hide-details
        clearable
        :items="groupByOptions"
      />
    </v-col>
    <v-col></v-col>
  </v-row>
</template>

<script setup lang="ts">
import type GroupOption from "@/types/DataTableGroupOption.ts";

defineProps<{
  groupByOptions?: GroupOption[];
}>();

const searchModel = defineModel<string | undefined>("search");
const groupByRawModel = defineModel<string | undefined>("groupByRaw");
</script>
