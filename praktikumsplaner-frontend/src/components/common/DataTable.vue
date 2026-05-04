<template>
  <data-table-toolbar
    v-model:search="internalSearch"
    v-model:group-by-raw="groupByRaw"
    :group-by-options="props.groupByOptions"
  />
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
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

import DataTableToolbar from "@/components/common/DataTableToolbar.vue";

interface GroupOption {
  title: string;
  value: string;
}
interface SortItem {
  key: string;
  order?: string;
}

defineOptions({ inheritAttrs: false });

const props = withDefaults(
  defineProps<{
    /* eslint-disable @typescript-eslint/no-explicit-any */
    headers: any[];
    items: any[] | undefined;
    loading?: boolean;
    groupByOptions?: GroupOption[];
    sortBy?: SortItem[];
    showGroupBy?: boolean;
    showExpand?: boolean;
    expandOnClick?: boolean;
  }>(),
  {
    loading: false,
    groupByOptions: () => [],
    sortBy: () => [],
    showGroupBy: true,
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
