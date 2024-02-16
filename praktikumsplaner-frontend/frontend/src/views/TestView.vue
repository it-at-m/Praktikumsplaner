<template>
    <div>
        <h1>Test</h1>
        <v-btn @click="testInfoSnackbar">Test Info Snackbar</v-btn>
        <v-btn @click="testWarningSnackbar">Test Warning Snackbar</v-btn>
        <v-btn @click="testErrorSnackbar">Test Error Snackbar</v-btn>
        <v-btn @click="testUserErrorDialog">Test User Error Dialog</v-btn>
    </div>
</template>

<script setup lang="ts">
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/Error";
import { useUserErrorStore } from "@/stores/user-error";

const snackbarStore = useSnackbarStore();
const userErrorStore = useUserErrorStore();

function testInfoSnackbar() {
    snackbarStore.showMessage({
        message: "Test",
        level: Levels.INFO,
    });
}

function testWarningSnackbar() {
    snackbarStore.showMessage({
        message: "Test",
        level: Levels.WARNING,
    });
}

function testErrorSnackbar() {
    snackbarStore.showMessage({
        message: "Test",
        level: Levels.ERROR,
    });
}

function testUserErrorDialog() {
    userErrorStore.showUserError({
        message: "Test",
        title: "Test",
    });
}
</script>

<style scoped>

</style>