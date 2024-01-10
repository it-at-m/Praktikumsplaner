<template>
    <v-container>
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
                    <NwkUpdateDialog
                        :nwk="nwk"
                        @updated="getAllActiveNwks"
                    />
                </v-card-actions>
            </v-card>
        </v-col>
    </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import Nwk from "@/types/Nwk";
import NwkService from "@/api/NwkService";
import NwkUpdateDialog from "@/components/Nachwuchskraefte/NwkUpdateDialog.vue";
const nwks = ref<Nwk[]>([]);

onMounted(() => {
    getAllActiveNwks();
});
function getAllActiveNwks() {
    NwkService.getAllActiveNwks().then((fetchedNwks) => {
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
<style scoped lang="scss">
.cards {
    background-color: var(--v-accent-base);
}
</style>