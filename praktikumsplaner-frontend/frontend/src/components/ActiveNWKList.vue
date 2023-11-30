<template>
    <v-list>
        <v-list-item
            v-for="nwk in nwks"
            :key="nwk.id"
            draggable="true"
            @dragstart="dragStart(nwk)"
        >
            <v-card
                elevation="0"
                color="grey"
            >
                <v-list-item-content>
                    <v-list-item-title style="cursor: grab">
                        {{ nwk.vorname }} {{ nwk.nachname }} ({{
                            nwk.studiengang
                        }}
                        / {{ nwk.jahrgang }})
                    </v-list-item-title>
                </v-list-item-content>
            </v-card>
        </v-list-item>
    </v-list>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue";
import NWK from "@/types/NWK";
import NWKService from "@/api/NWKService";
import { useNwkStore } from "@/stores/nwkStore";
import { EventBus } from "@/stores/event-bus";

const nwks = ref<NWK[]>([]);
const nwkStore = useNwkStore();

onMounted(() => {
    getAllActiveNWKs();
    EventBus.$on("assignedNWK", removeNWKFromList);
    EventBus.$on("unassignedNWK", addNWKToList);
});
function getAllActiveNWKs() {
    NWKService.getAllUnassignedNWKs().then((fetchedNWKs) => {
        nwks.value = [...fetchedNWKs];
    });
}

function dragStart(nwk: NWK) {
    nwkStore.updateNwkId(nwk);
}

function removeNWKFromList(nwk: NWK) {
    const index = nwks.value.indexOf(nwk, 0);
    if (index > -1) {
        nwks.value.splice(index, 1);
    }
}

function addNWKToList(nwk: NWK) {
    nwks.value.push(nwk);
}
</script>