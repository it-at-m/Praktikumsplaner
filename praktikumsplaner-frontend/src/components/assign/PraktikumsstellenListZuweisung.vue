<template>
  <v-container
    v-if="properties.praktikumsstellen && properties.praktikumsstellen.length > 0"
  >
    <v-list>
      <v-list-item
        v-for="praktikumsstelle in praktikumsstellen"
        :key="praktikumsstelle.id"
        :ripple="false"
      >
        <praktikumsstelle-card
          :key="praktikumsstelle.id"
          :praktikumsstelle="praktikumsstelle"
        ></praktikumsstelle-card>
      </v-list-item>
    </v-list>
  </v-container>
  <v-container
    v-else
    class="d-flex justify-center align-center"
  >
    <v-row justify="center">
      <v-col
        cols="auto"
        class="d-flex align-center justify-center"
      >
        <v-icon
          color="blue"
          size="large"
          class="mr-3"
          :icon="mdiInformationOutline"
        />
        <span
          >Für den vergangenen Meldezeitraum sind keine Praktikumsstellen
          vorhanden.</span
        >
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { mdiInformationOutline } from "@mdi/js";

import PraktikumsstelleCard from "@/components/assign/PraktikumsstelleCardZuweisung.vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const properties = defineProps<{
  praktikumsstellen: Praktikumsstelle[];
}>();
</script>
