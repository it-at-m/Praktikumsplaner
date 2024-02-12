<template>
    <v-list>
        <v-list-item-group>
            <v-list-item
                v-for="nwk in props.value"
                :key="nwk.id"
                draggable="true"
                @dragstart="dragStart($event, nwk)"
            >
                <nwk-card :nwk="nwk" />
            </v-list-item>
        </v-list-item-group>
    </v-list>
</template>
<script setup lang="ts">
import { onMounted } from "vue";
import Nwk from "@/types/Nwk";
import { EventBus } from "@/stores/event-bus";
import NwkCard from "@/components/assign/NwkCard.vue";

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