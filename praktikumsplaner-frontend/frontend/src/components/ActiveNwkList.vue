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
                <v-card-subtitle class="text-center"
                    >({{ nwk.studiengang }} /
                    {{ nwk.jahrgang }})</v-card-subtitle
                >
            </v-card>
        </v-col>
    </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import Nwk from "@/types/Nwk";
import NwkService from "@/api/NwkService";
const nwks = ref<Nwk[]>([]);

onMounted(() => {
    getAllActiveNwks();
});
function getAllActiveNwks() {
    NwkService.getAllActiveNwks().then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
    });
}
</script>
<style scoped lang="scss">
.cards {
    background-color: var(--v-accent-base);
}
</style>