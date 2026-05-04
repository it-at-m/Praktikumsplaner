<template>
  <page-title page-header-text="Praktikumsplätze">
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
  <data-table-card
    v-if="activeMeldezeitraum"
    :headers="headers"
    :items="praktikumsstellenTableItems"
    :group-by-options="groupByOptions"
    :loading="loadingSite || loadingUebersicht"
    :show-expand="true"
    :sort-by="defaultSort"
  >
    <template #title>
      <span> Übersicht aus dem aktuellen Meldezeitraum </span>
      <small v-if="!security.isAusbildungsleitung() && security.isAusbilder()"
        >(Nur eigene Plätze von örtl. Ausbilder*innen angezeigt)</small
      >
    </template>
    <template #[`item.actions`]="{ item }">
      <v-btn
        :icon="mdiPencil"
        color="primary"
        @click=""
      />
      <v-btn
        :icon="mdiDelete"
        color="error"
      />
    </template>
    <template #expanded-row="{ columns, item }">
      <tr>
        <td
          :colspan="columns.length"
          class="py-2"
        >
          <p>
            {{ generator.getPraktikumsstellenCardDetailText(item) }}
          </p>
        </td>
      </tr>
    </template>
  </data-table-card>
  <kein-meldezeitraum-message v-else></kein-meldezeitraum-message>
</template>

<script setup lang="ts">
import type { SortItem } from "vuetify/lib/components/VDataTable/composables/sort";

import { mdiDelete, mdiPencil, mdiPlus } from "@mdi/js";
import { computed, onMounted, ref, watch } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import DataTableCard from "@/components/common/DataTableCard.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import TwoChoiceDialogCards from "@/components/common/TwoChoiceDialogCards.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
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
    align: "end",
    sortable: false,
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
</script>
