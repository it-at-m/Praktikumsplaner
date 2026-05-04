<template>
  <page-title
    class="mb-0"
    page-header-text="Meldezeiträume"
  >
    <template #actions>
      <create-meldezeitraum
        v-model="model"
        @meldezeitraum-added="reloadMeldezeitraeume"
      ></create-meldezeitraum>
    </template>
  </page-title>
  <v-card>
    <v-skeleton-loader
      v-if="loading"
      type="heading, divider, list-item, heading, divider, list-item, heading"
    >
    </v-skeleton-loader>
    <v-row>
      <v-col>
        <meldezeitraum-list
          v-if="!loading"
          :value="current"
          @deleted="reloadMeldezeitraeume"
        >
          <template #card-title-icon> <v-icon :icon="mdiStar" /> </template>
          <template #header>
            <h3>Aktueller Meldezeitraum</h3>
          </template>
          <template #notfoundmessage>
            <p>Kein aktueller Meldezeitraum gefunden.</p>
          </template>
        </meldezeitraum-list>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col>
        <meldezeitraum-list
          v-if="!loading"
          :value="upcoming"
          @deleted="reloadMeldezeitraeume"
        >
          <template #header>
            <h3>Kommende Meldezeiträume</h3>
          </template>
          <template #notfoundmessage>
            <p>Keine kommenden Meldezeiträume gefunden.</p>
          </template>
        </meldezeitraum-list>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col>
        <meldezeitraum-list
          v-if="!loading"
          :value="passed"
          @deleted="reloadMeldezeitraeume"
          ><template #header>
            <h3>Vergangene Meldezeiträume</h3>
          </template>
          <template #notfoundmessage>
            <p>Keine vergangenen Meldezeiträume gefunden.</p>
          </template>
        </meldezeitraum-list>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import { mdiStar } from "@mdi/js";
import { onMounted, ref, watch } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import PageTitle from "@/components/common/PageTitle.vue";
import CreateMeldezeitraum from "@/components/meldezeitraeume/CreateMeldezeitraum.vue";
import MeldezeitraumList from "@/components/meldezeitraeume/MeldezeitraumList.vue";
import { useSecurity } from "@/composables/security";
import router from "@/plugins/router";
import { useUserStore } from "@/stores/user";
import Meldezeitraum from "@/types/Meldezeitraum";

const userStore = useUserStore();

const model = ref<boolean>(true);
const loading = ref<boolean>(false);
const current = ref<Meldezeitraum[]>([]);
const upcoming = ref<Meldezeitraum[]>([]);
const passed = ref<Meldezeitraum[]>([]);
const route = router.currentRoute.value;

onMounted(() => {
  reloadMeldezeitraeume();

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

function reloadMeldezeitraeume() {
  MeldezeitraumService.getCurrentMeldezeitraum(loading).then((response) => {
    current.value = response;
  });

  MeldezeitraumService.getPassedMeldezeitraueme(loading).then((response) => {
    passed.value = response;
  });

  MeldezeitraumService.getUpcomingMeldezeitraueme(loading).then((response) => {
    upcoming.value = response;
  });
}
</script>
