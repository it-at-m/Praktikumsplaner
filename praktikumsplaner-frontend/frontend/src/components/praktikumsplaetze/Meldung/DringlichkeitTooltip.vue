<template>
    <v-tooltip right>
        <template #activator="{ on, attrs }">
            <v-icon
                color="blue"
                class="v-tooltip-spacing"
                v-bind="attrs"
                large
                v-on="on"
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
