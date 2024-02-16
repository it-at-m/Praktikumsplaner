<template>
  <v-container>
    <v-skeleton-loader v-if="loading" type="card"></v-skeleton-loader>
    <v-row>
      <v-col
          cols="12" sm="6" md="4"
          v-for="nwk in nwks"
          :key="nwk.id"
      >
        <v-card class="cards" border="primary md" elevation="6" >
          <v-card-title class="text-center">{{ nwk.vorname }} {{ nwk.nachname }}</v-card-title>
          <v-card-subtitle class="text-center">{{ getSubtitle(nwk) }}</v-card-subtitle>
          <v-card-actions class="d-flex justify-center">
            <NwkUpdateDialog :nwk="nwk" @updated="loadAllActiveNwks" />
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>


<script setup lang="ts">
import mitt from "mitt";
import { onMounted, ref } from "vue";

import NwkService from "@/api/NwkService";
import NwkUpdateDialog from "@/components/nachwuchskraefte/NwkUpdateDialog.vue";
import Nwk from "@/types/Nwk";

const nwks = ref<Nwk[]>([]);
const loading = ref<boolean>(false);

onMounted(() => {
    loadAllActiveNwks();
});
function loadAllActiveNwks() {
    loading.value = true;
    NwkService.getAllActiveNwks()
        .then((fetchedNwks) => {
            nwks.value = [...fetchedNwks];
        })
        .finally(() => {
            loading.value = false;
        });
}

function getSubtitle(nwk: Nwk): string {
    let subtitle = "Daten konnten nicht geladen werden.";
    if (nwk.studiengang) {
        subtitle = "(" + nwk.studiengang + " / " + nwk.jahrgang + ")";
    } else if (nwk.ausbildungsrichtung) {
        subtitle = "(" + nwk.ausbildungsrichtung + " / " + nwk.jahrgang + ")";
    }
    return subtitle;
}

type Events = {
    nwkCreated: boolean;
};

const emitter = mitt<Events>();

emitter.on("nwkCreated", () => {
    loadAllActiveNwks();
});
</script>
<style scoped>
.cards {
  margin-bottom: 20px; /* Fügt einen Abstand zwischen den Karten hinzu */
}
.v-card .v-card-title {
  color: #000000;
}
</style>
