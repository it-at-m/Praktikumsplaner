<template>
  <div>
    <v-row class="text-center">
      <v-col class="mb-4">
        <br />
        <h1 class="text-h3 font-weight-bold mb-3">
          Willkommen beim Praktikumsplaner!
        </h1>
      </v-col>
    </v-row>
    <template v-if="userStore.username">
      <v-row class="text-center">
        <v-col class="mb-4">
          <h2>Hallo {{ userStore.username }}! Was möchtest du machen?</h2>
        </v-col>
      </v-row>
      <v-row class="justify-center text-center">
        <v-col
          v-if="security.isAusbildungsleitung()"
          cols="3"
          class="mb-4"
        >
          <router-link
            to="nachwuchskraefte"
            class="text-decoration-none"
          >
            <v-card class="mx-auto">
              <v-card-title> Nachwuchskräfte anlegen </v-card-title>
              <v-card-text>
                <v-icon
                  color="primary"
                  size="100"
                  :icon="mdiAccountPlus"
                />
              </v-card-text>
            </v-card>
          </router-link>
        </v-col>
        <v-col
          v-if="security.isAusbildungsleitung()"
          cols="3"
          class="mb-4"
        >
          <router-link
            to="meldezeitraum"
            class="text-decoration-none"
          >
            <v-card class="mx-auto">
              <v-card-title> Meldezeitraum anlegen </v-card-title>
              <v-card-text>
                <v-icon
                  color="primary"
                  size="100"
                  :icon="mdiCalendarPlus"
                />
              </v-card-text>
            </v-card>
          </router-link>
        </v-col>
        <v-col
          v-if="security.checkForAnyRole(['AUSBILDER', 'AUSBILDUNGSLEITUNG'])"
          cols="3"
          class="mb-4"
        >
          <router-link
            to="praktikumsplaetze"
            class="text-decoration-none"
          >
            <v-card class="mx-auto">
              <v-card-title> Praktikumsplatz melden </v-card-title>
              <v-card-text>
                <v-icon
                  color="primary"
                  size="100"
                  :icon="mdiAccountArrowRight"
                />
              </v-card-text>
            </v-card>
          </router-link>
        </v-col>
        <v-col
          v-if="security.isAusbildungsleitung()"
          cols="3"
          class="mb-4"
        >
          <router-link
            to="zuweisung"
            class="text-decoration-none"
          >
            <v-card class="mx-auto">
              <v-card-title> Zuweisung durchführen </v-card-title>
              <v-card-text>
                <v-icon
                  color="primary"
                  size="100"
                  :icon="mdiAccountCheck"
                />
              </v-card-text>
            </v-card>
          </router-link>
        </v-col>
      </v-row>
    </template>
    <v-row
      v-else
      class="justify-center text-center"
    >
      <v-col
        cols="3"
        class="mb-4"
      >
        <v-progress-circular
          indeterminate
          size="100"
          color="primary"
        >
        </v-progress-circular>
      </v-col>
    </v-row>
  </div>
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

const snackbarStore = useSnackbarStore();
const userStore = useUserStore();
const security = useSecurity();

onMounted(() => {
  checkHealth().catch((error) => {
    snackbarStore.showMessage(error);
  });
});
</script>

<style scoped></style>
