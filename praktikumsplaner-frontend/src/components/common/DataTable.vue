<template>
  <data-table-toolbar
    v-model:search="internalSearch"
    v-model:group-by-raw="groupByRaw"
    :group-by-options="groupByOptions"
  />
  <v-data-table
    :headers="headers"
    :items="items"
    :group-by="groupBy"
    :search="internalSearch"
    :sort-by="sortBy"
    :loading="loading"
    fixed-header
    hide-default-footer
    items-per-page="-1"
    :show-expand="showExpand"
    :expand-on-click="expandOnClick"
    v-bind="$attrs"
  >
    <template #[`item.actions`]="slotProps">
      <v-btn-group
        density="comfortable"
        @click.stop
      >
        <slot
          name="item.actions"
          v-bind="slotProps"
        />
      </v-btn-group>
    </template>
    <template #expanded-row="slotProps">
      <slot
        name="expanded-row"
        v-bind="slotProps"
      />
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import type GroupOption from "@/types/DataTableGroupOption.ts";
import type SortItem from "@/types/DataTableSortItem";

import { computed, ref } from "vue";

import DataTableToolbar from "@/components/common/DataTableToolbar.vue";

defineOptions({ inheritAttrs: false });

withDefaults(
  defineProps<{
    /* eslint-disable @typescript-eslint/no-explicit-any */
    headers: any[];
    items: any[] | undefined;
    loading?: boolean;
    groupByOptions?: GroupOption[];
    sortBy?: SortItem[];
    showExpand?: boolean;
    expandOnClick?: boolean;
  }>(),
  {
    loading: false,
    groupByOptions: () => [],
    sortBy: () => [],
    showExpand: false,
    expandOnClick: false,
  }
);

const internalSearch = ref<string | undefined>(undefined);
const groupByRaw = ref<string | undefined>();

const groupBy = computed<SortItem[]>(() => {
  return groupByRaw.value ? [{ key: groupByRaw.value, order: "asc" }] : [];
});
</script>
