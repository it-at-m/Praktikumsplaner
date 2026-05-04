<template>
  <page-title page-header-text="Praktikumsplätze (aktueller Meldezeitraum)">
    <template #actions>
      <two-choice-dialog-cards
        v-if="canStellenBeSubmitted()"
        v-model="twoChoiceDialogVisible"
        buttontext="Hinzufügen"
        :icon="mdiPlus"
        dialogtitle="Praktikumsplatz melden"
        dialogsubtitle="Welche Art von Praktikumsplatz möchtest du melden?"
        choice-one-title="Studium"
        choice-one-subtitle="Praktikumsplatz für Studierende "
        choice-two-title="Ausbildung"
        choice-two-subtitle="Praktikumsplatz für Auszubildende"
        @choice-one="toStudium"
        @choice-two="toAusbildung"
      />
    </template>
  </page-title>
  <data-table
    v-if="activeMeldezeitraum"
    :headers="headers"
    :items="praktikumsstellenTableItems"
    :group-by-options="groupByOptions"
    :loading="loadingSite || loadingUebersicht"
    :show-expand="true"
    :sort-by="defaultSort"
    :expand-on-click="true"
  >
    <template #[`item.actions`]="{ item }">
      <studiums-praktikumsstelle-update-dialog
        v-if="item.studiengang"
        v-model="itemProxyMap[item.id]!"
        icon-only
        @update:model-value="onRowUpdated(item.id, $event)"
      />
      <ausbildungs-praktikumsstelle-update-dialog
        v-else
        v-model="itemProxyMap[item.id]!"
        icon-only
        @update:model-value="onRowUpdated(item.id, $event)"
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
            {{ generator.getPraktikumsstellenCardDetailText(item) }}
          </p>
        </td>
      </tr>
    </template>
  </data-table>
  <kein-meldezeitraum-message v-else></kein-meldezeitraum-message>
</template>

<script setup lang="ts">
import { mdiPlus } from "@mdi/js";
import { computed, onMounted, ref, watch } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import DataTable from "@/components/common/DataTable.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import TwoChoiceDialogCards from "@/components/common/TwoChoiceDialogCards.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import AusbildungsPraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/AusbildungsPraktikumsstelleUpdateDialog.vue";
import PraktikumsstelleDeleteDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstelleDeleteDialog.vue";
import StudiumsPraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/StudiumsPraktikumsstelleUpdateDialog.vue";
import { useSecurity } from "@/composables/security";
import { useTextGenerator } from "@/composables/textGenerator.ts";
import router from "@/plugins/router";
import emitter from "@/stores/eventBus";
import { useUserStore } from "@/stores/user";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const userStore = useUserStore();
const activeMeldezeitraum = ref<boolean>(false);
const loadingUebersicht = ref<boolean>(false);
const loadingSite = ref<boolean>(true);
const security = useSecurity();
const twoChoiceDialogVisible = ref<boolean>(false);
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
    value: (item: Praktikumsstelle) =>
      item.studiengang
        ? "Studium"
        : item.ausbildungsrichtung
          ? "Ausbildung"
          : "",
  },
  {
    title: "Richtung",
    key: "richtung",
    value: (item: Praktikumsstelle) =>
      item.studiengang
        ? item.studiengang
        : item.ausbildungsrichtung
          ? item.ausbildungsrichtung
          : "",
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
    art: s.studiengang ? "Studium" : s.ausbildungsrichtung ? "Ausbildung" : "",
    richtung: s.studiengang
      ? s.studiengang
      : s.ausbildungsrichtung
        ? s.ausbildungsrichtung
        : "",
  }))
);

const defaultSort = [{ key: "dienststelle", order: "asc" }];

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

function canStellenBeSubmitted() {
  return security.isAusbildungsleitung() || activeMeldezeitraum.value;
}
function toAusbildung(): void {
  router.push("/praktikumsplaetze/meldungAusbildung");
}
function toStudium(): void {
  router.push("/praktikumsplaetze/meldungStudium");
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
