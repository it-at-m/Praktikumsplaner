<template>
  <v-card>
    <template #title>
      <slot name="title" />
    </template>
    <template #text>
      <data-table-toolbar
        v-model:search="internalSearch"
        v-model:group-by-raw="internalGroupByRaw"
        :group-by-options="groupByOptions"
        :show-group-by="showGroupBy"
        :search-label="searchLabel"
        :group-by-label="groupByLabel"
      />
    </template>
    <v-data-table
      :headers="headers"
      :items="items"
      :group-by="groupBy"
      :search="internalSearch"
      :loading="loading"
      :fixed-header="fixedHeader"
      :hide-default-footer="hideFooter"
      :show-expand="showExpand"
      v-bind="$attrs"
    >
      <template #[`item.actions`]="slotProps">
        <v-btn-group density="comfortable">
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

type GroupOption = { title: string; value: string };

defineOptions({ inheritAttrs: false });

const props = withDefaults(
  defineProps<{
    headers: any[];
    items: any[] | undefined;
    loading?: boolean;
    groupByOptions?: GroupOption[];
    groupByRaw?: string; // deprecated external control
    showGroupBy?: boolean;
    searchLabel?: string;
    groupByLabel?: string;
    fixedHeader?: boolean;
    hideFooter?: boolean;
    showExpand?: boolean;
  }>(),
  {
    loading: false,
    groupByOptions: () => [],
    groupByRaw: undefined,
    showGroupBy: true,
    searchLabel: "Suche",
    groupByLabel: "Gruppierung",
    fixedHeader: true,
    hideFooter: true,
    showExpand: false,
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

// passthroughs for v-data-table bindings
const headers = computed(() => props.headers);
const items = computed(() => props.items);
const loading = computed(() => props.loading);
const groupByOptions = computed(() => props.groupByOptions);
const showGroupBy = computed(() => props.showGroupBy);
const searchLabel = computed(() => props.searchLabel);
const groupByLabel = computed(() => props.groupByLabel);
const fixedHeader = computed(() => props.fixedHeader);
const hideFooter = computed(() => props.hideFooter);
const showExpand = computed(() => props.showExpand);
</script>
