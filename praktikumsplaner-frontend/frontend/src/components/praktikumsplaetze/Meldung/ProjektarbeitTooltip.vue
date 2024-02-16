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
            >Die Projektarbeit ist die Abschlussarbeit der Auszubildenden</span
        >
    </v-tooltip>
</template>
