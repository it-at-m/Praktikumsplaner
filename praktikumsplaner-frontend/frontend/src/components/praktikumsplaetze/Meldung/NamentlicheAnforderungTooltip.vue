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
            >Bei Anforderung einer bestimmten NWK für die Stelle, hier den
            vollständigen Namen eintragen.</span
        >
    </v-tooltip>
</template>
