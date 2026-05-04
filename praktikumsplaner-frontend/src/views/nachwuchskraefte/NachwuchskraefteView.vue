<template>
  <page-title page-header-text="Nachwuchskräfte">
    <template #actions>
      <excel-import-nwk></excel-import-nwk>
      <nwk-create-dialog></nwk-create-dialog>
    </template>
  </page-title>
  <data-table
    :headers="headers"
    :items="nwkTableItems"
    :group-by-options="groupByOptions"
    :loading="loading"
    :sort-by="defaultSort"
  >
    <template #[`item.actions`]="{ item }">
      <nwk-update-dialog
        :nwk="item"
        @updated="loadAllActiveNwks"
      />
    </template>
  </data-table>
</template>

<script setup lang="ts">
import type Nwk from "@/types/Nwk";

import { computed, onMounted, onUnmounted, ref, watch } from "vue";

import NwkService from "@/api/NwkService";
import DataTable from "@/components/common/DataTable.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import ExcelImportNwk from "@/components/nachwuchskraefte/ExcelImportNwk.vue";
import NwkCreateDialog from "@/components/nachwuchskraefte/NwkCreateDialog.vue";
import NwkUpdateDialog from "@/components/nachwuchskraefte/NwkUpdateDialog.vue";
import { useSecurity } from "@/composables/security";
import router from "@/plugins/router";
import emitter from "@/stores/eventBus";
import { useUserStore } from "@/stores/user";
import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";

const userStore = useUserStore();
const route = router.currentRoute.value;
const nwks = ref<Nwk[]>([]);
const loading = ref<boolean>(false);
const groupByOptions = [
  { title: "Art", value: "art" },
  { title: "Richtung", value: "richtung" },
  { title: "Jahrgang", value: "jahrgang" },
];
const headers = [
  {
    title: "Vorname",
    key: "vorname",
  },
  {
    title: "Nachname",
    key: "nachname",
  },
  {
    title: "Art",
    key: "art",
    value: (item: Nwk) =>
      item.studiengang
        ? "Studium"
        : item.ausbildungsrichtung
          ? "Ausbildung"
          : "",
  },
  {
    title: "Richtung",
    key: "richtung",
    value: (item: Nwk) =>
      item.studiengang
        ? item.studiengang
        : item.ausbildungsrichtung
          ? item.ausbildungsrichtung
          : "",
  },
  { title: "Jahrgang", key: "jahrgang" },
  {
    title: "Vorlesungstage",
    key: "vorlesungstage",
    value: (item: Nwk) =>
      item.vorlesungstage && item.vorlesungstage.length > 0
        ? new GermanWeekdayMapper()
            .getGermanShortDays(item.vorlesungstage)
            .join(", ")
        : "",
  },
  {
    title: "Aktionen",
    key: "actions",
    align: "center",
    sortable: false,
    width: 10,
  },
];

// FIXME: workaround to allow grouping by derived columns till backend refactored
const nwkTableItems = computed(() =>
  nwks.value.map((n) => ({
    ...n,
    art: n.studiengang ? "Studium" : n.ausbildungsrichtung ? "Ausbildung" : "",
    richtung: n.studiengang
      ? n.studiengang
      : n.ausbildungsrichtung
        ? n.ausbildungsrichtung
        : "",
  }))
);

const defaultSort = [{ key: "nachname", order: "asc" }];

onMounted(() => {
  loadAllActiveNwks();
  emitter.on("nwkCreated", loadAllActiveNwks);
  emitter.on("nwkDeleted", loadAllActiveNwks);
  if (userStore.username) {
    redirectIfUnauthorized();
  } else {
    // This Watcher is responsible for redirecting the user to the AccessDenied view if his roles do not suffice
    watch(
      () => userStore.roles,
      () => {
        redirectIfUnauthorized();
      }
    );
  }
});

onUnmounted(() => {
  emitter.off("nwkCreated", loadAllActiveNwks);
  emitter.off("nwkDeleted", loadAllActiveNwks);
});

function redirectIfUnauthorized() {
  const requiresRoles =
    route.meta.requiresRole != undefined
      ? (route.meta.requiresRole as string[])
      : undefined;
  const security = useSecurity();
  if (requiresRoles !== undefined && !security.checkForAnyRole(requiresRoles)) {
    router.push("/AccessDenied");
  }
}

function loadAllActiveNwks() {
  NwkService.getAllActiveNwks(loading).then((fetchedNwks) => {
    nwks.value = [...fetchedNwks];
  });
}
</script>
