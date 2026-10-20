<template>
  <v-container fluid>
    <v-row class="text-center">
      <v-col class="mb-4">
        <br />
        <h1 class="text-display-medium font-weight-bold mb-3">
          Willkommen beim Praktikumsplaner!
        </h1>
      </v-col>
    </v-row>
    <template v-if="userStore.username">
      <v-row class="text-center">
        <v-col class="mb-4">
          <p class="text-headline-small font-weight-bold">
            Hallo {{ userStore.username }}! Was möchtest du machen?
          </p>
        </v-col>
      </v-row>
      <v-row class="justify-center text-center">
        <v-col
          v-if="security.isAusbildungsleitung()"
          cols="12"
          md="6"
          xl="3"
          class="mb-4"
        >
          <v-card
            class="mx-auto"
            to="nachwuchskraefte"
            :data-test="testIds.main.cardNachwuchskraefte"
          >
            <v-card-title> Nachwuchskräfte anlegen </v-card-title>
            <v-card-text>
              <v-icon
                color="primary"
                size="100"
                :icon="mdiAccountPlus"
              />
            </v-card-text>
          </v-card>
        </v-col>
        <v-col
          v-if="security.isAusbildungsleitung()"
          cols="12"
          md="6"
          xl="3"
          class="mb-4"
        >
          <v-card
            class="mx-auto"
            to="meldezeitraum"
            :data-test="testIds.main.cardMeldezeitraum"
          >
            <v-card-title> Meldezeitraum anlegen </v-card-title>
            <v-card-text>
              <v-icon
                color="primary"
                size="100"
                :icon="mdiCalendarPlus"
              />
            </v-card-text>
          </v-card>
        </v-col>
        <v-col
          v-if="security.checkForAnyRole(['AUSBILDER', 'AUSBILDUNGSLEITUNG'])"
          cols="12"
          md="6"
          xl="3"
          class="mb-4"
        >
          <v-card
            class="mx-auto"
            to="praktikumsplaetze"
            :data-test="testIds.main.cardPraktikumsplaetze"
          >
            <v-card-title> Praktikumsplatz melden </v-card-title>
            <v-card-text>
              <v-icon
                color="primary"
                size="100"
                :icon="mdiAccountArrowRight"
              />
            </v-card-text>
          </v-card>
        </v-col>
        <v-col
          v-if="security.isAusbildungsleitung()"
          cols="12"
          md="6"
          xl="3"
          class="mb-4"
        >
          <v-card
            class="mx-auto"
            to="zuweisung"
            :data-test="testIds.main.cardZuweisung"
          >
            <v-card-title> Zuweisung durchführen </v-card-title>
            <v-card-text>
              <v-icon
                color="primary"
                size="100"
                :icon="mdiAccountCheck"
              />
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </template>
    <v-row
      v-else
      class="justify-center text-center"
    >
      <v-progress-circular
        indeterminate
        size="100"
        color="primary"
      />
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import {
  mdiAccountArrowRight,
  mdiAccountCheck,
  mdiAccountPlus,
  mdiCalendarPlus,
} from "@mdi/js";
import { onMounted } from "vue";

import { checkHealth } from "@/api/HealthService";
import { useSecurity } from "@/composables/security";
import { useSnackbarStore } from "@/stores/snackbar";
import { useUserStore } from "@/stores/user";
import { testIds } from "@/testIds";

const snackbarStore = useSnackbarStore();
const userStore = useUserStore();
const security = useSecurity();

onMounted(() => {
  checkHealth().catch((error) => {
    snackbarStore.showMessage(error);
  });
});
</script>
