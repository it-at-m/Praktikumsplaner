<template>
    <v-list>
        <v-list-item-group>
            <v-list-item
                v-for="nwk in nwks"
                :key="nwk.id"
                draggable="true"
                @dragstart="dragStart(nwk)"
            >
                <nwk-card :nwk="nwk" />
            </v-list-item>
        </v-list-item-group>
    </v-list>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue";
import Nwk from "@/types/Nwk";
import NwkService from "@/api/NwkService";
import { useNwkStore } from "@/stores/nwkStore";
import { EventBus } from "@/stores/event-bus";
import NwkCard from "@/components/NwkCard.vue";

const nwks = ref<Nwk[]>([]);
const nwkStore = useNwkStore();

onMounted(() => {
    getAllActiveNwks();
    EventBus.$on("assignedNwk", removeNwkFromList);
    EventBus.$on("unassignedNwk", addNwkToList);
});
function getAllActiveNwks() {
    NwkService.getAllUnassignedNwks().then((fetchedNwks) => {
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
<style scoped>
.v-card {
    margin: 5px;
}
</style>