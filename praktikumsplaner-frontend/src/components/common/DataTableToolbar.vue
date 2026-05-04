<template>
  <v-row>
    <v-col cols="3">
      <v-text-field
        v-model="internalSearch"
        variant="outlined"
        density="comfortable"
        label="Suche"
        hide-details
        clearable
      />
    </v-col>
    <v-col
      v-if="(props.groupByOptions?.length || 0) > 0"
      cols="2"
    >
      <v-select
        v-model="internalGroupByRaw"
        variant="outlined"
        density="comfortable"
        label="Gruppierung"
        hide-details
        clearable
        :items="groupByOptions"
      />
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import { computed } from "vue";

interface GroupOption {
  title: string;
  value: string;
}

const props = withDefaults(
  defineProps<{
    search: string | undefined;
    groupByRaw: string | undefined;
    groupByOptions?: GroupOption[];
  }>(),
  {
    groupByOptions: () => [],
  }
);

const emit =
  defineEmits<
    (
      e: "update:search" | "update:groupByRaw",
      value: string | undefined
    ) => void
  >();

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
