<template>
    <v-list>
        <v-list-item
            v-for="nwk in nwks"
            :key="nwk.id"
            draggable
            @dragstart="dragStart(nwk)"
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
import NWK from "@/types/NWK";
import NWKService from "@/api/NWKService";
import { useNwkStore } from "@/stores/nwkStore";

const nwks = ref<NWK[]>([]);
const nwkStore = useNwkStore();

onMounted(() => {
    getAllActiveNWKs();
});
function getAllActiveNWKs() {
    NWKService.getAllActiveNWKs().then((fetchedNWKs) => {
        nwks.value = [...fetchedNWKs];
    });
}

function dragStart(nwk: NWK) {
    console.log(nwk.id);
    nwkStore.updateNwkId(nwk.id);
}
</script>