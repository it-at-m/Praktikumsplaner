<template>
    <v-tooltip location="right">
        <template #activator="{ props }">
            <v-icon
                color="blue"
                class="v-tooltip-spacing"
                size="large"
                v-bind="props"
            >
                mdi-information
            </v-icon>
        </template>
        <span
            >Die Projektarbeit ist die Abschlussarbeit der Auszubildenden</span
        >
    </v-tooltip>
</template>
