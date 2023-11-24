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
import { ref, onMounted } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";

const praktikumsstellen = ref<Map<string, Praktikumsstelle[]>>();

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
</script>