<template>
  <v-list
    v-if="praktikumsstellen && praktikumsstellen.length > 0"
    class="pl-2"
    :data-test="testIds.assign.praktikumsstellenList"
  >
    <v-list-item
      v-for="praktikumsstelle in praktikumsstellen"
      :key="praktikumsstelle.id"
      class="px-0"
      :ripple="false"
    >
      <praktikumsstelle-card
        :key="praktikumsstelle.id"
        :praktikumsstelle="praktikumsstelle"
      ></praktikumsstelle-card>
    </v-list-item>
  </v-list>
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
import { testIds } from "@/testIds";
import Praktikumsstelle from "@/types/Praktikumsstelle";

defineProps<{
  praktikumsstellen: Praktikumsstelle[];
}>();
</script>
