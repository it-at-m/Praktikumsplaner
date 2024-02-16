<template>
    <v-list v-model:selected="selectedNwks">
        <v-list-item
            v-for="nwk in props.modelValue"
            :key="nwk.id"
            :modelValue="nwk"
            draggable="true"
            @dragstart="dragStart($event, nwk)"
        >
            <nwk-card :nwk="nwk" />
        </v-list-item>
    </v-list>
</template>
<script setup lang="ts">
import mitt from "mitt";
import { onMounted, onUnmounted, ref } from "vue";

import NwkCard from "@/components/assign/NwkCard.vue";
import Nwk from "@/types/Nwk";

const props = defineProps<{
    modelValue: Nwk[];
}>();

const emits = defineEmits<{
    (e: "input", nwks: Nwk[]): void;
}>();

const selectedNwks = ref<Nwk[]>([]);

type Events = {
    assignedNwk: Nwk;
    unassignedNwk: Nwk;
};

const emitter = mitt<Events>();

onMounted(() => {
    emitter.on("assignedNwk", removeNwkFromList);
    emitter.on("unassignedNwk", addNwkToList);
});
onUnmounted(() => {
    emitter.off("assignedNwk", removeNwkFromList);
    emitter.off("unassignedNwk", addNwkToList);
});
function dragStart(event: DragEvent, nwk: Nwk) {
    event.dataTransfer?.setData("application/json", JSON.stringify(nwk));
}

function removeNwkFromList(nwk: Nwk) {
    let nwksInternal = props.value;
    let index = -1;

    const nwkToRemove: Nwk | undefined = nwksInternal.find(
        (n) => n.id === nwk.id
    );

    if (nwkToRemove !== undefined) index = nwksInternal.indexOf(nwkToRemove, 0);

    if (index > -1) {
        nwksInternal.splice(index, 1);
        emits("input", nwksInternal);
    }
}

function addNwkToList(nwk: Nwk) {
    let nwksInternal = props.value;
    nwksInternal.push(nwk);
    nwksInternal.sort((a, b) => a.nachname.localeCompare(b.nachname));
    emits("input", nwksInternal);
}
</script>
<style scoped>
</style>
