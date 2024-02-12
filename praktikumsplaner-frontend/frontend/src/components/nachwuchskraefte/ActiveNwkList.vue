<template>
    <v-container>
        <v-skeleton-loader
            v-if="loading"
            type="card"
        >
        </v-skeleton-loader>
        <v-col
            v-for="nwk in nwks"
            :key="nwk.id"
        >
            <v-card class="cards">
                <v-card-title class="justify-center"
                    >{{ nwk.vorname }} {{ nwk.nachname }}</v-card-title
                >
                <v-card-subtitle class="text-center">{{
                    getSubtitle(nwk)
                }}</v-card-subtitle>
                <v-card-actions>
                    <v-col cols="4"> </v-col>
                    <v-col cols="1"> </v-col>
                    <v-col cols="2">
                        <NwkUpdateDialog
                            class="d-flex justify-center align-center"
                            :nwk="nwk"
                            @updated="getAllActiveNwks"
                        />
                    </v-col>
                    <v-col> </v-col>
                </v-card-actions>
            </v-card>
        </v-col>
    </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import Nwk from "@/types/Nwk";
import NwkService from "@/api/NwkService";
import NwkUpdateDialog from "@/components/nachwuchskraefte/NwkUpdateDialog.vue";
import { EventBus } from "@/stores/event-bus";

const nwks = ref<Nwk[]>([]);
const loading = ref<boolean>(false);

onMounted(() => {
    getAllActiveNwks();
});
function getAllActiveNwks() {
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

EventBus.$on("nwkCreated", () => {
    getAllActiveNwks();
});
</script>
<style scoped lang="scss">
.cards {
    background-color: var(--v-accent-base);
}
</style>