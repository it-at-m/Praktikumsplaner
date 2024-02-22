<template>
    <v-row>
        <v-col cols="12">
            <v-select
                v-model="nwk.studiengang"
                label="Studienrichtung"
                :items="Studiengang"
                item-value="value"
                item-title="name"
                variant="outlined"
                clearable
                :rules="isStudiumOrAusbildungRule"
                @click:clear="clearStudienrichtung()"
            ></v-select>
        </v-col>
        <v-col cols="12">
            <v-select
                v-model="nwk.ausbildungsrichtung"
                label="Ausbildungsrichtung"
                :items="Ausbildungsrichtung"
                item-value="value"
                item-title="name"
                variant="outlined"
                clearable
                :rules="isStudiumOrAusbildungRule"
                @click:clear="clearAusbildungsrichtung()"
            ></v-select>
        </v-col>
    </v-row>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import NwkCreate from "@/types/NwkCreate";
import { Studiengang } from "@/types/Studiengang";

const props = defineProps<{
    modelValue: NwkCreate;
}>();

const emits = defineEmits<{
    (e: "updated", v: NwkCreate): void;
}>();

const nwk = computed({
    get: () => props.modelValue,
    set: (v) => emits("updated", v),
});

function clearAusbildungsrichtung() {
    nwk.value.ausbildungsrichtung = undefined;
}

function clearStudienrichtung() {
    nwk.value.studiengang = undefined;
}

const isStudiumOrAusbildungRule = computed(() => {
    return [
        (nwk.value.studiengang != undefined &&
            nwk.value.ausbildungsrichtung == undefined) ||
            (nwk.value.studiengang == undefined &&
                nwk.value.ausbildungsrichtung != undefined) ||
            "Es muss eine Studienrichtung oder eine Ausbildungsrichtung angegeben werden.",
    ];
});
</script>
