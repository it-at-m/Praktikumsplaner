<template>
    <div>
        <v-container>
            <div
                v-for="(praktikumsstellenliste, abteilung) in praktikumsstellen"
                :key="abteilung"
            >
                <v-subheader>{{ abteilung }}</v-subheader>
                <v-list>
                    <v-list-item-group>
                        <v-list-item
                            v-for="praktikumsstelle in asPraktikumsstelleList(
                                praktikumsstellenliste
                            )"
                            :key="praktikumsstelle.id"
                        >
                            <v-list-item-content>{{
                                praktikumsstelle.email
                            }}</v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </div>
        </v-container>
    </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";

const praktikumsstellen = ref<Map<string, Praktikumsstelle[]>>();

onMounted(() => {
    refreshTasks();
});
function refreshTasks() {
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