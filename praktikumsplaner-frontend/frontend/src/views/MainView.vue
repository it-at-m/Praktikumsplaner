<template>
    <v-container>
        <v-row class="text-center">
            <v-col class="mb-4">
                <br />
                <h1 class="text-h3 font-weight-bold mb-3">
                    Willkommen beim Praktikumsplaner!
                </h1>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import HealthService from "@/api/HealthService";
import { useSnackbarStore } from "@/stores/snackbar";
import { onMounted } from "vue";
import { useHeaderStore } from "@/stores/header";

const snackbarStore = useSnackbarStore();
const headerStore = useHeaderStore();

onMounted(() => {
    HealthService.checkHealth().catch((error) => {
        snackbarStore.showMessage(error);
    });
    headerStore.setHeader("Praktikumsplaner");
});
</script>

<style scoped>
</style>
