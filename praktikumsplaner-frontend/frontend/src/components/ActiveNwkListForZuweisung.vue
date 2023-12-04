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
import Nwk from "@/types/Nwk";
import NwkService from "@/api/NwkService";
import { useNwkStore } from "@/stores/nwkStore";
import { EventBus } from "@/stores/event-bus";

const nwks = ref<Nwk[]>([]);
const nwkStore = useNwkStore();

onMounted(() => {
    getAllActiveNwks();
    EventBus.$on("assignedNwk", removeNwkFromList);
    EventBus.$on("unassignedNwk", addNwkToList);
});
function getAllActiveNwks() {
    NwkService.getAllActiveNwks().then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
    });
}

function dragStart(nwk: Nwk) {
    nwkStore.updateNwkId(nwk);
}

function removeNwkFromList(nwk: Nwk) {
    const index = nwks.value.indexOf(nwk, 0);
    if (index > -1) {
        nwks.value.splice(index, 1);
    }
}

function addNwkToList(nwk: Nwk) {
    nwks.value.push(nwk);
}
</script>