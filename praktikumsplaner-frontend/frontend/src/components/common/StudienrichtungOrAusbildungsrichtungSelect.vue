<template>
    <v-container>
        <v-col>
            <v-row>
                <v-select
                    v-model="nwk.studiengang"
                    label="Studienrichtung"
                    :items="Studiengang"
                    item-value="value"
                    item-text="name"
                    outlined
                    clearable
                    :rules="isStudiumOrAusbildungRule"
                    @click:clear="clearStudienrichtung()"
                ></v-select>
            </v-row>
            <v-row>
                <v-select
                    v-model="nwk.ausbildungsrichtung"
                    label="Ausbildungsrichtung"
                    :items="Ausbildungsrichtung"
                    item-value="value"
                    item-text="name"
                    outlined
                    clearable
                    :rules="isStudiumOrAusbildungRule"
                    @click:clear="clearAusbildungsrichtung()"
                ></v-select>
            </v-row>
        </v-col>
    </v-container>
</template>

<script setup lang="ts">
import { Studiengang } from "@/types/Studiengang";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import NwkCreate from "@/types/NwkCreate";
import { computed } from "vue";

const props = defineProps<{
    value: NwkCreate;
}>();

const emits = defineEmits<{
    (e: "updated", v: NwkCreate): void;
}>();

const nwk = computed({
    get: () => props.value,
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