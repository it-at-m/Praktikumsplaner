<template>
    <v-container v-if="props.modelValue && modelValue.length > 0">
        <v-list v-model:selected="selectedNwks">
            <v-list-item
                v-for="nwk in props.modelValue"
                :key="nwk.id"
                :model-value="nwk"
                draggable="true"
                @dragstart="dragStart($event, nwk)"
            >
                <nwk-card :nwk="nwk" />
            </v-list-item>
        </v-list>
    </v-container>
    <v-container v-else class="d-flex justify-center align-center">
        <v-row justify="center">
            <v-col cols="auto" class="d-flex align-center justify-center">
                <v-icon color="blue" size="large" class="mr-3">mdi-information-outline</v-icon>
                <span>Es sind noch keine Nachwuchskräfte vorhanden.</span>
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import { onMounted, onUnmounted, ref } from "vue";

import NwkCard from "@/components/assign/NwkCard.vue";
import emitter from "@/stores/eventBus";
import Nwk from "@/types/Nwk";

const props = defineProps<{
    modelValue: Nwk[];
}>();

const emits = defineEmits<{
    (e: "input", nwks: Nwk[]): void;
}>();

const selectedNwks = ref<Nwk[]>([]);

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
    const nwksInternal = props.modelValue;
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
    const nwksInternal = props.modelValue;
    nwksInternal.push(nwk);
    nwksInternal.sort((a, b) => a.nachname.localeCompare(b.nachname));
    emits("input", nwksInternal);
}
</script>
<style scoped>
[v-cloak] [draggable="true"] {
    cursor: grab;
}
</style>
