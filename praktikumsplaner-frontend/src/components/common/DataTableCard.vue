<template>
  <v-card>
    <template #title>
      <slot name="title" />
    </template>
    <template #text>
      <data-table-toolbar
        v-model:search="internalSearch"
        v-model:group-by-raw="internalGroupByRaw"
        :group-by-options="props.groupByOptions"
      />
    </template>
    <v-data-table
      :headers="props.headers"
      :items="props.items"
      :group-by="groupBy"
      :search="internalSearch"
      :sort-by="props.sortBy"
      :loading="props.loading"
      fixed-header
      hide-default-footer
      :show-expand="props.showExpand"
      :expand-on-click="props.expandOnClick"
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
  </v-card>
</template>

<script setup lang="ts">
import type { SortItem } from "vuetify/lib/components/VDataTable/composables/sort";

import { computed, ref } from "vue";

import DataTableToolbar from "@/components/common/DataTableToolbar.vue";

interface GroupOption {
  title: string;
  value: string;
}

defineOptions({ inheritAttrs: false });

const props = withDefaults(
  defineProps<{
    /* eslint-disable @typescript-eslint/no-explicit-any */
    headers: any[];
    items: any[] | undefined;
    loading?: boolean;
    groupByOptions?: GroupOption[];
    groupByRaw?: string; // deprecated external control
    sortBy?: SortItem[];
    showGroupBy?: boolean;
    showExpand?: boolean;
    expandOnClick?: boolean;
  }>(),
  {
    loading: false,
    groupByOptions: () => [],
    groupByRaw: undefined,
    sortBy: () => [],
    showGroupBy: true,
    showExpand: false,
    expandOnClick: false,
  }
);

// internal, component-scoped state for search/groupBy
const internalSearch = ref<string | undefined>(undefined);
const internalGroupByRaw = ref<string | undefined>(props.groupByRaw);

const groupBy = computed<SortItem[]>(() => {
  return internalGroupByRaw.value
    ? [{ key: internalGroupByRaw.value, order: "asc" }]
    : [];
});
</script>
