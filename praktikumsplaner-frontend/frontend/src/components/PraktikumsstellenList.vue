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
                            v-for="praktikumsstelle in praktikumsstellenliste as Praktikumsstelle[]"
                            :key="praktikumsstelle"
                        >
                            <v-list-item-content>{{
                                praktikumsstelle
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
</script>