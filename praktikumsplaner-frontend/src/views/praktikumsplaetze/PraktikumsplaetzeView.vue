<template>
  <v-container class="pa-xl-8">
    <page-title page-header-text="Praktikumsplätze">
      <template #actions>
        <meldezeitraum-select
          v-model="selectedMeldezeitraumId"
          :meldezeitraueme="meldezeitraeume || []"
          :is-required="true"
          :disabled="loading"
          hide-details
          class="mr-5"
        />
        <v-btn-group>
          <v-btn
            v-if="canStellenBeSubmitted"
            text="Hinzufügen"
            :prepend-icon="mdiPlus"
            color="primary"
            @click="toMeldung"
          />
        </v-btn-group>
      </template>
    </page-title>
    <data-table
      v-if="activeMeldezeitraum"
      :headers="headers"
      :items="praktikumsstellenTableItems"
      :group-by-options="groupByOptions"
      :loading="loading"
      show-expand
      :sort-by="defaultSort"
      expand-on-click
    >
      <template
        v-if="isAusbildungsleitung"
        #[`item.actions`]="{ item }"
      >
        <praktikumsstelle-update-dialog
          v-model="itemProxyMap[item.id]!"
          icon-only
          @update:model-value="(newItem) => onRowUpdated(item.id, newItem)"
        />
        <praktikumsstelle-delete-dialog
          :stelle="item"
          @deleted="getAllPraktikumsstellenInSelectedMeldezeitraum"
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
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import PraktikumsstelleDeleteDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstelleDeleteDialog.vue";
import PraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstelleUpdateDialog.vue";
import { useSecurity } from "@/composables/security";
import { useTextGenerator } from "@/composables/textGenerator";
import router from "@/plugins/router";
import emitter from "@/stores/eventBus";
import { useUserStore } from "@/stores/user";
import { findBildungsrichtung } from "@/types/Bildungsrichtung.ts";
import Meldezeitraum from "@/types/Meldezeitraum.ts";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const userStore = useUserStore();
const loading = ref<boolean>(true);
const meldezeitraeume = ref<Meldezeitraum[]>();
const selectedMeldezeitraumId = ref<string>();
const currentMeldezeitraumId = ref<string>();
const security = useSecurity();
ref<boolean>(false);
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
  { title: "Dienststelle", value: "dienststelle" },
  { title: "Art", value: "art" },
  { title: "Richtung", value: "richtung" },
];

const generator = useTextGenerator();
const route = router.currentRoute.value;

const praktikumsstellenTableItems = computed(() =>
  (praktikumsstellen.value || []).map((s) => ({
    ...s,
    art: s.richtung ? findBildungsrichtung(s.richtung)?.art : null,
  }))
);

const defaultSort: SortItem[] = [{ key: "dienststelle", order: "asc" }];

onMounted(() => {
  MeldezeitraumService.getAllMeldezeitraeume()
    .then((mz) => {
      meldezeitraeume.value = mz;
      currentMeldezeitraumId.value =
        MeldezeitraumService.getCurrentMeldezeitraumFromList(mz)?.id;
      if (currentMeldezeitraumId.value == null) {
        loading.value = false;
      } else {
        selectedMeldezeitraumId.value = currentMeldezeitraumId.value;
      }
    })
    .catch(() => {
      loading.value = false;
    });

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
  getAllPraktikumsstellenInSelectedMeldezeitraum();
});

watch(selectedMeldezeitraumId, () => {
  getAllPraktikumsstellenInSelectedMeldezeitraum();
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

// TODO hide actions and create button for Ausbilder if not current Meldezeitraum
const canStellenBeSubmitted = computed(
  () =>
    isAusbildungsleitung.value ||
    (currentMeldezeitraumId.value &&
      currentMeldezeitraumId.value == selectedMeldezeitraumId.value)
);
const isAusbildungsleitung = computed(() => security.isAusbildungsleitung());
const activeMeldezeitraum = computed<boolean>(
  () => !!selectedMeldezeitraumId.value
);

function toMeldung(): void {
  router.push("/praktikumsplaetze/meldung");
}

function getAllPraktikumsstellenInSelectedMeldezeitraum() {
  if (!selectedMeldezeitraumId.value) {
    return;
  }
  loading.value = true;
  PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
    selectedMeldezeitraumId.value
  )
    .then((fetchedStellen) => {
      praktikumsstellen.value = fetchedStellen;
    })
    .finally(() => {
      loading.value = false;
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
