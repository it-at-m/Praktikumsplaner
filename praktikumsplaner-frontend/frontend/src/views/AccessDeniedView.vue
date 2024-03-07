<template>
    <v-container>
        <page-title
            back-button-url="/"
            page-header-text="Zurück zur Startseite"
        ></page-title>
        <v-row
            align="center"
            class="ml-auto"
        >
            <v-col cols="auto">
                <v-icon
                    size="large"
                    color="red"
                    >mdi-alert-octagon</v-icon
                >
            </v-col>
            <v-col>
                <h1>Zugriff verweigert</h1>
                <p class="m-2 p-2">
                    Du hast nicht die erforderlichen Rechte, um auf diese Seite
                    zuzugreifen.
                </p>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="4">
                <v-img :src="catImageUrl"></v-img>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import PageTitle from "@/components/common/PageTitle.vue";

const catImageUrl = "https://cataas.com/cat";
</script>
