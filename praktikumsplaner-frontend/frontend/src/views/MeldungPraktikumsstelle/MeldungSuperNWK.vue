<template>
    <v-container class="spacing-left">
        <h1>Meldung einer übernatürlichen Nachwuchskraft🦸‍♀️🦸‍♂️</h1>
        <br />
        <h3>
            Wow, du bist wirklich mutig! Aber leider können wir noch keine
            übernatürlichen Nachwuchskräfte verteilen. Bitte wähle entweder
            Auszubildende oder Studierende aus.
        </h3>
        <v-row class="bottom-buttons">
            <v-col>
                <v-btn
                    color="primary"
                    outlined
                    :to="{ path: '/meldungAusbilder' }"
                >
                    ZURÜCK
                </v-btn>
            </v-col>
            <v-col cols="8" />
        </v-row>
    </v-container>
</template>
<style>
.spacing-left {
    margin-left: 30px;
}
.bottom-buttons {
    margin-top: 40%;
}
</style>