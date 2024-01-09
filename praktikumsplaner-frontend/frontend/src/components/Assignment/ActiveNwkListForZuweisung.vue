<template>
    <v-list>
        <v-list-item-group>
            <v-list-item
                v-for="nwk in props.value"
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
import { onMounted } from "vue";
import Nwk from "@/types/Nwk";
import { useNwkStore } from "@/stores/nwkStore";
import { EventBus } from "@/stores/event-bus";
import NwkCard from "@/components/Assignment/NwkCard.vue";

const nwkStore = useNwkStore();

const props = defineProps<{
    value: Nwk[];
}>();

const emits = defineEmits<{
    (e: "input", nwks: Nwk[]): void;
}>();

onMounted(() => {
    EventBus.$on("assignedNwk", removeNwkFromList);
    EventBus.$on("unassignedNwk", addNwkToList);
});

function dragStart(nwk: Nwk) {
    nwkStore.updateNwkId(nwk);
}

function removeNwkFromList(nwk: Nwk) {
    let nwksInternal = props.value;
    const index = nwksInternal.indexOf(nwk, 0);
    if (index > -1) {
        nwksInternal.splice(index, 1);
        emits("input", nwksInternal);
    }
}

function addNwkToList(nwk: Nwk) {
    let nwksInternal = props.value;
    nwksInternal.push(nwk);
    emits("input", nwksInternal);
}
</script>
<style scoped>
.v-card {
    margin: 5px;
}
</style>