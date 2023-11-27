<template>
    <div>
        <v-container>
            <v-expansion-panels multiple>
                <v-expansion-panel
                    v-for="(
                        praktikumsstellenliste, abteilung
                    ) in praktikumsstellen"
                    :key="abteilung"
                >
                    <v-expansion-panel-header>{{
                        abteilung
                    }}</v-expansion-panel-header>
                    <v-expansion-panel-content>
                        <v-list>
                            <v-list-item-group>
                                <v-list-item
                                    v-for="praktikumsstelle in asPraktikumsstelleList(
                                        praktikumsstellenliste
                                    )"
                                    :key="praktikumsstelle.id"
                                    @drop="drop(praktikumsstelle)"
                                    @dragover.prevent
                                    @dragenter.prevent
                                >
                                    <v-list-item-content>
                                        <v-list-item-title>
                                            Stelle bei
                                            {{
                                                praktikumsstelle.dienststelle
                                            }}</v-list-item-title
                                        >
                                        <v-list-item-subtitle
                                            v-if="praktikumsstelle.studienart"
                                        >
                                            Studiengang:
                                            {{
                                                praktikumsstelle.studienart
                                            }}</v-list-item-subtitle
                                        >
                                        <v-list-item-subtitle v-else>
                                            Ausbildungsrichtung:
                                            {{
                                                praktikumsstelle.ausbildungsrichtung
                                            }}</v-list-item-subtitle
                                        >
                                        <v-list-item-subtitle
                                            v-if="
                                                praktikumsstelle.studiensemester
                                            "
                                            >Studiensemester:
                                            {{
                                                praktikumsstelle.studiensemester
                                            }}</v-list-item-subtitle
                                        >
                                        <v-list-item-subtitle v-else>
                                            Ausbildungsjahr:
                                            {{
                                                praktikumsstelle.ausbildungsjahr
                                            }}</v-list-item-subtitle
                                        >
                                        <v-list-item-subtitle
                                            v-if="
                                                praktikumsstelle.namentlicheAnforderung
                                            "
                                        >
                                            Namentliche Anforderung:
                                            {{
                                                praktikumsstelle.namentlicheAnforderung
                                            }}
                                        </v-list-item-subtitle>
                                    </v-list-item-content>
                                </v-list-item>
                            </v-list-item-group>
                        </v-list>
                    </v-expansion-panel-content>
                </v-expansion-panel>
            </v-expansion-panels>
        </v-container>
    </div>
</template>
<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import { useNwkStore } from "@/stores/nwkStore";

const praktikumsstellen = ref<Map<string, Praktikumsstelle[]>>();
const nwkStore = useNwkStore();
const assignedNwkID = ref(nwkStore.nwkId);

watch(
    () => nwkStore.nwkId,
    () => {
        assignedNwkID.value = nwkStore.nwkId ?? "";
    }
);

onMounted(() => {
    getAllPraktikumsstellen();
});
function getAllPraktikumsstellen() {
    PraktikumsstellenService.getAllPraktikumsstellen().then(
        (fetchedStellen) => {
            praktikumsstellen.value = fetchedStellen;
        }
    );
}
function asPraktikumsstelleList(list: unknown): Praktikumsstelle[] {
    const newList = list as Praktikumsstelle[];
    return newList;
}

function drop(stelle: Praktikumsstelle) {
    if (stelle.id && !stelle.assignedNWKId) {
        stelle.assignedNWKId = assignedNwkID.value;
        console.log(
            "StellenId " + stelle.id + "AssignedNWKId " + stelle.assignedNWKId
        );
        PraktikumsstellenService.assignNWK(stelle.id, stelle.assignedNWKId);
    }
}
</script>