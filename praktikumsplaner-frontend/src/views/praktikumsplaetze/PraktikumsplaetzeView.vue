<template>
  <v-container class="pa-xl-8">
    <page-title page-header-text="Praktikumsplätze (aktueller Meldezeitraum)">
      <template #actions>
        <v-btn
          v-if="canStellenBeSubmitted"
          color="primary"
          :prepend-icon="mdiPlus"
          @click="toMeldung"
        >
          Hinzufügen
        </v-btn>
      </template>
    </page-title>
    <data-table
      v-if="activeMeldezeitraum"
      :headers="headers"
      :items="praktikumsstellenTableItems"
      :group-by-options="groupByOptions"
      :loading="loadingSite || loadingUebersicht"
      show-expand
      :sort-by="defaultSort"
      expand-on-click
    >
      <template #[`item.actions`]="{ item }">
        <praktikumsstelle-update-dialog
          v-model="itemProxyMap[item.id]!"
          icon-only
          @update:model-value="(newItem) => onRowUpdated(item.id, newItem)"
        />
        <praktikumsstelle-delete-dialog
          :stelle="item"
          @deleted="getAllPraktikumsstellenInCurrentMeldezeitraum"
        />
      </template>
      <template #expanded-row="{ columns, item }">
        <tr>
          <td
            :colspan="columns.length"
            class="py-2"
          >
            <p style="white-space: pre-line">
              {{ generator.getPraktikumsstellenDetailDescription(item) }}
            </p>
          </td>
        </tr>
      </template>
    </data-table>
    <kein-meldezeitraum-message v-else></kein-meldezeitraum-message>
  </v-container>
</template>

<script setup lang="ts">
import type SortItem from "@/types/DataTableSortItem";

import { mdiPlus } from "@mdi/js";
import { computed, onMounted, ref, watch } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import DataTable from "@/components/common/DataTable.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import PraktikumsstelleDeleteDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstelleDeleteDialog.vue";
import PraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstelleUpdateDialog.vue";
import { useSecurity } from "@/composables/security";
import { useTextGenerator } from "@/composables/textGenerator";
import router from "@/plugins/router";
import emitter from "@/stores/eventBus";
import { useUserStore } from "@/stores/user";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const userStore = useUserStore();
const activeMeldezeitraum = ref<boolean>(false);
const loadingUebersicht = ref<boolean>(false);
const loadingSite = ref<boolean>(true);
const security = useSecurity();
const praktikumsstellen = ref<Praktikumsstelle[]>();
// FIXME: workaround to get real object from derived one
const itemProxyMap = computed<Record<string, Praktikumsstelle>>(() => {
  const map: Record<string, Praktikumsstelle> = {};
  (praktikumsstellen.value || []).forEach((s) => {
    if (s.id) map[s.id] = s;
  });
  return map;
});
const headers = [
  { title: "Dienststelle", key: "dienststelle" },
  {
    title: "Art",
    key: "art",
  },
  {
    title: "Richtung",
    key: "richtung",
  },
  {
    title: "Planstelle",
    key: "planstelleVorhanden",
    value: (item: Praktikumsstelle) =>
      item.planstelleVorhanden ? "Ja" : "Nein",
  },
  {
    title: "Aktionen",
    key: "actions",
    align: "center",
    sortable: false,
    width: 10,
  },
];
const groupByOptions = [
  { title: "Art", value: "art" },
  { title: "Dienststelle", value: "dienststelle" },
  { title: "Richtung", value: "richtung" },
];

const generator = useTextGenerator();
const route = router.currentRoute.value;

// FIXME: workaround to allow grouping by derived columns till backend refactored
const praktikumsstellenTableItems = computed(() =>
  (praktikumsstellen.value || []).map((s) => ({
    ...s,
    art: s.art || deriveArt(s.richtung),
    richtung: s.richtung || "",
  }))
);

const defaultSort: SortItem[] = [{ key: "dienststelle", order: "asc" }];

onMounted(() => {
  loadingUebersicht.value = true;
  MeldezeitraumService.getCurrentMeldezeitraum(loadingUebersicht).then(
    (zeitraueme) => {
      activeMeldezeitraum.value = zeitraueme.length > 0;
    }
  );

  getAllPraktikumsstellenInCurrentMeldezeitraum();

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

emitter.on("praktikumsstelleUpdated", () => {
  getAllPraktikumsstellenInCurrentMeldezeitraum();
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

emitter.on("nwkDeleted", getAllPraktikumsstellenInCurrentMeldezeitraum);

const canStellenBeSubmitted = computed(
  () => security.isAusbildungsleitung() || activeMeldezeitraum.value
);

function toMeldung(): void {
  router.push("/praktikumsplaetze/meldung");
}

function deriveArt(richtung?: string): string {
  if (!richtung) return "";
  const studium = ["BSC", "BWI", "VI", "LLB", "PUMA", "QE3"];
  return studium.includes(richtung) ? "Studium" : "Ausbildung";
}

function getAllPraktikumsstellenInCurrentMeldezeitraum() {
  PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
    "current"
  )
    .then((fetchedStellen) => {
      praktikumsstellen.value = fetchedStellen;
    })
    .finally(() => {
      loadingSite.value = false;
    });
}

function onRowUpdated(id: string | undefined, updated: Praktikumsstelle) {
  if (!id) return;
  const idx = (praktikumsstellen.value || []).findIndex((s) => s.id === id);
  if (idx >= 0 && praktikumsstellen.value) {
    praktikumsstellen.value[idx] = updated;
  }
}
</script>
