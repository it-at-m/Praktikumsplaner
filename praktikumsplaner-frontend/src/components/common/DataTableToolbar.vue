<template>
  <v-row>
    <v-col>
      <v-text-field
        v-model="internalSearch"
        variant="outlined"
        density="comfortable"
        :label="searchLabel"
        hide-details
        clearable
      />
    </v-col>
    <v-col v-if="showGroupBy">
      <v-select
        v-model="internalGroupByRaw"
        variant="outlined"
        density="comfortable"
        :label="groupByLabel"
        hide-details
        clearable
        :items="groupByOptions"
      />
    </v-col>
    <v-col cols="2"></v-col>
  </v-row>
</template>

<script setup lang="ts">
import { computed } from "vue";

interface GroupOption { title: string; value: string }

const props = withDefaults(
  defineProps<{
    search: string | undefined;
    groupByRaw: string | undefined;
    groupByOptions?: GroupOption[];
    showGroupBy?: boolean;
    searchLabel?: string;
    groupByLabel?: string;
  }>(),
  {
    groupByOptions: () => [],
    showGroupBy: true,
    searchLabel: "Suche",
    groupByLabel: "Gruppierung",
  }
);

const emit = defineEmits<{
  (e: "update:search", value: string | undefined): void;
  (e: "update:groupByRaw", value: string | undefined): void;
}>();

// v-model proxies
const internalSearch = computed({
  get: () => props.search,
  set: (val: string | undefined) => emit("update:search", val),
});
const internalGroupByRaw = computed({
  get: () => props.groupByRaw,
  set: (val: string | undefined) => emit("update:groupByRaw", val),
});
</script>
