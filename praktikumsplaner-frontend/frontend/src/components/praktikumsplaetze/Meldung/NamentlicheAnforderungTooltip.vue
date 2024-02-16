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
            >Bei Anforderung einer bestimmten NWK für die Stelle, hier den
            vollständigen Namen eintragen.</span
        >
    </v-tooltip>
</template>
