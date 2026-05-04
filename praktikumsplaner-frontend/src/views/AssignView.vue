<template>
  <page-title
    page-header-text="Zuweisung (letzter vergangener Meldezeitraum)"
  ></page-title>
  <v-row>
    <v-col
      class="overflow-y-auto"
      style="max-height: 70vh"
    >
      <v-skeleton-loader
        v-if="loadingNwk"
        type="image"
      ></v-skeleton-loader>
      <active-nwk-list-for-zuweisung
        v-else
        v-model="nwks"
        class="overflow-y-auto"
      />
    </v-col>
    <v-divider vertical />
    <v-col
      class="overflow-y-auto"
      style="max-height: 70vh"
    >
      <v-skeleton-loader
        v-if="loadingPraktikumsstellen"
        type="image"
      ></v-skeleton-loader>
      <praktikumsstellen-list-zuweisung
        v-else
        :praktikumsstellen="praktikumsstellen"
        class="overflow-y-auto"
      />
    </v-col>
  </v-row>
  <v-row
    v-if="!loadingNwk && !loadingPraktikumsstellen"
    class="pt-4 pr-2"
  >
    <v-spacer></v-spacer>
    <v-btn
      :prepend-icon="mdiMail"
      color="primary"
      class="mr-4"
      @click="openMailWarningDialog"
      >Mails senden</v-btn
    >
    <excel-export
      :start-download="startDownload"
      @click="openExcelWarnings"
      @exported="exported"
    ></excel-export>
  </v-row>
  <warning-dialog
    :visible="showWarningDialog"
    :warnings="warnings"
    @accepted="acceptedWarningDialog"
    @rejected="rejectedWarningDialog"
  />
  <send-mails-dialog v-model:show-dialog="showSendMailDialog" />
</template>

<script setup lang="ts">
import { mdiMail } from "@mdi/js";
import { onMounted, ref, watch } from "vue";

import NwkService from "@/api/NwkService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import ActiveNwkListForZuweisung from "@/components/assign/ActiveNwkListForZuweisung.vue";
import ExcelExport from "@/components/assign/ExcelExport.vue";
import PraktikumsstellenListZuweisung from "@/components/assign/PraktikumsstellenListZuweisung.vue";
import SendMailsDialog from "@/components/assign/SendMailsDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import WarningDialog from "@/components/common/WarningDialog.vue";
import { useSecurity } from "@/composables/security";
import { useWarnings } from "@/composables/warningGenerator";
import router from "@/plugins/router";
import emitter from "@/stores/eventBus";
import { useUserStore } from "@/stores/user";
import Nwk from "@/types/Nwk";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Warning from "@/types/Warning";

const warningsGenerator = useWarnings();

const loadingNwk = ref(true);
const loadingPraktikumsstellen = ref(true);
const showSendMailDialog = ref(false);
const showWarningDialog = ref(false);
const warnings = ref<Warning[]>([]);
const nwks = ref<Nwk[]>([]);
const praktikumsstellen = ref<Praktikumsstelle[]>([]);
const startDownload = ref(false);
const isExcelWarningDialog = ref(false);
const route = router.currentRoute.value;
const userStore = useUserStore();

function collectWarnings() {
  warnings.value = warningsGenerator.getAfterAssignmentWarnings(
    praktikumsstellen.value,
    nwks.value
  );
}

function exported() {
  startDownload.value = false;
}

function openMailWarningDialog() {
  collectWarnings();
  showWarningDialog.value = true;
  isExcelWarningDialog.value = false;
}

function openExcelWarnings() {
  collectWarnings();
  showWarningDialog.value = true;
  isExcelWarningDialog.value = true;
}

function openQueryPraktikumsPeriodDialog() {
  showSendMailDialog.value = true;
}

function acceptedWarningDialog() {
  if (isExcelWarningDialog.value) {
    startDownload.value = true;
  } else {
    openQueryPraktikumsPeriodDialog();
  }
  showWarningDialog.value = false;
}

function rejectedWarningDialog() {
  showWarningDialog.value = false;
}

onMounted(() => {
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
  getAllActiveNwks();
  getAllPraktikumsstellenInMostRecentMeldezeitraum();
});

emitter.on("praktikumsstelleUpdated", () => {
  getAllPraktikumsstellenInMostRecentMeldezeitraum();
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

function getAllActiveNwks() {
  NwkService.getAllUnassignedNwks(loadingNwk).then((fetchedNwks) => {
    nwks.value = [...fetchedNwks];
  });
}

function getAllPraktikumsstellenInMostRecentMeldezeitraum() {
  PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
    "most_recent"
  )
    .then((fetchedStellen) => {
      praktikumsstellen.value = fetchedStellen;
    })
    .finally(() => (loadingPraktikumsstellen.value = false));
}
</script>
