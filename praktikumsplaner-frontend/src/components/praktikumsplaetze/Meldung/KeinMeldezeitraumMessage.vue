<script setup lang="ts">
import { mdiInformationOutline } from "@mdi/js";
</script>
<template>
  <v-row class="align-center">
    <v-col
      cols="auto"
      class="d-flex align-center"
    >
      <v-icon
        color="blue"
        size="large"
        :icon="mdiInformationOutline"
      />
    </v-col>
    <v-col class="d-flex align-center">
      <p class="mt-5">
        Es gibt keinen aktuellen Meldezeitraum.
      </p>
    </v-col>
  </v-row>
</template>
