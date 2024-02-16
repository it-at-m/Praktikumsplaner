<template>
    <v-container>
        <v-row>
            <v-col>
                <v-list-item>
                    <v-select
                        v-model="nwkProp.studiengang"
                        label="Studienrichtung"
                        :items="Studiengang"
                        item-value="value"
                        item-text="name"
                        outlined
                        clearable
                        :rules="isStudiumOrAusbildungRule"
                        @click:clear="nwkProp.studiengang = undefined"
                    ></v-select>
                </v-list-item>
            </v-col>
            <v-col>
                <v-list-item>
                    <v-select
                        v-model="nwkProp.ausbildungsrichtung"
                        label="Ausbildungsrichtung"
                        :items="Ausbildungsrichtung"
                        item-value="value"
                        item-text="name"
                        outlined
                        clearable
                        :rules="isStudiumOrAusbildungRule"
                        @click:clear="nwkProp.ausbildungsrichtung = undefined"
                    ></v-select>
                </v-list-item>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import { Studiengang } from "@/types/Studiengang";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import NwkCreate from "@/types/NwkCreate";
import { computed } from "vue";

const props = defineProps<{
    nwk: NwkCreate;
}>();

const emits = defineEmits<{
    (e: "updated", v: NwkCreate): void;
}>();

const nwkProp = computed({
    get: () => props.nwk,
    set: (v) => emits("updated", v),
});

const isStudiumOrAusbildungRule = computed(() => {
    return [
        (nwkProp.value.studiengang != undefined &&
            nwkProp.value.ausbildungsrichtung == undefined) ||
            (nwkProp.value.studiengang == undefined &&
                nwkProp.value.ausbildungsrichtung != undefined) ||
            "Es muss eine Studienrichtung oder eine Ausbildungsrichtung angegeben werden.",
    ];
});
</script>