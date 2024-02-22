<template>
    <v-tooltip location="right">
        <template #activator="{ props }">
            <v-icon
                class="mt-3"
                color="blue"
                size="large"
                v-bind="props"
            >
                mdi-information
            </v-icon>
        </template>
        <span
            >Die Dringlichkeit gibt an, wie hoch priorisiert der Praktikumsplatz
            zu besetzen ist.</span
        >
    </v-tooltip>
</template>
<script setup lang="ts"></script>
