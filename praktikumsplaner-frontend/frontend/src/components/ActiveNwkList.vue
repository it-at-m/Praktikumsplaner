<template>
    <v-list>
        <v-list-item
            v-for="nwk in nwks"
            :key="nwk.id"
        >
            <v-list-item-content>
                <v-list-item-title>
                    {{ nwk.vorname }} {{ nwk.nachname }} ({{
                        nwk.studiengang
                    }}
                    / {{ nwk.jahrgang }})
                </v-list-item-title>
            </v-list-item-content>
        </v-list-item>
    </v-list>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue";
import Nwk from "@/types/Nwk";
import NWKService from "@/api/NwkService";

const nwks = ref<Nwk[]>([]);

onMounted(() => {
    getAllActiveNWKs();
});
function getAllActiveNWKs() {
    NWKService.getAllActiveNWKs().then((fetchedNWKs) => {
        nwks.value = [...fetchedNWKs];
    });
}
</script>