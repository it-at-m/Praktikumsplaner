<template>
    <v-list>
        <v-list-item
            v-for="stellen in praktikumsstellen"
            :key="stellen.id"
        >
            <v-list-item-content>
                <v-list-item-title>
                    {{ stellen.dienststelle }}
                </v-list-item-title>
            </v-list-item-content>
        </v-list-item>
    </v-list>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";

const praktikumsstellen = ref<Praktikumsstelle[]>([]);

onMounted(() => {
    refreshTasks();
});
function refreshTasks() {
    PraktikumsstellenService.getAllPraktikumsstellen().then(
        (fetchedStellen) => {
            praktikumsstellen.value = [...fetchedStellen];
        }
    );
}
</script>