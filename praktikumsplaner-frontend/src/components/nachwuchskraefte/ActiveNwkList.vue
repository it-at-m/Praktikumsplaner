<template>
    <v-container>
        <v-container v-if="nwks && nwks.length > 0">
            <v-skeleton-loader
                v-if="loading"
                type="card"
            ></v-skeleton-loader>
            <v-row>
                <v-col
                    v-for="nwk in nwks"
                    :key="nwk.id"
                    cols="12"
                    sm="6"
                    md="4"
                >
                    <v-card
                        class="cards"
                        border="primary"
                        elevation="6"
                    >
                        <v-card-title class="text-center"
                            >{{ nwk.vorname }} {{ nwk.nachname }}</v-card-title
                        >
                        <v-card-subtitle class="text-center">{{
                            getSubtitle(nwk)
                        }}</v-card-subtitle>
                        <v-card-actions class="d-flex justify-center">
                            <nwk-update-dialog
                                :nwk="nwk"
                                @updated="loadAllActiveNwks"
                            />
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
        <v-container v-else>
            <v-row class="align-center">
                <v-col
                    cols="auto"
                    class="d-flex align-center"
                >
                    <v-icon
                        color="blue"
                        size="large"
                        >mdi-information-outline</v-icon
                    >
                </v-col>
                <v-col class="d-flex align-center">
                    <p>Es sind noch keine Nachwuchskräfte vorhanden.</p>
                </v-col>
            </v-row>
        </v-container>
    </v-container>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from "vue";

import NwkService from "@/api/NwkService";
import NwkUpdateDialog from "@/components/nachwuchskraefte/NwkUpdateDialog.vue";
import emitter from "@/stores/eventBus";
import Nwk from "@/types/Nwk";

const nwks = ref<Nwk[]>([]);
const loading = ref<boolean>(false);

onMounted(() => {
    loadAllActiveNwks();
    emitter.on("nwkCreated", () => {
        loadAllActiveNwks();
    });
});
onUnmounted(() => {
    emitter.off("nwkCreated", loadAllActiveNwks);
});

function loadAllActiveNwks() {
    NwkService.getAllActiveNwks(loading).then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
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
</script>
<style scoped>
.cards {
    margin-bottom: 20px;
}
.v-card .v-card-title {
    color: #000000;
}
</style>
