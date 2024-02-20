<template>
    <v-container>
        <page-title
            back-button-url="/"
            page-header-text="Zurück zur Startseite"
        ></page-title>
        <div class="m-2 p-2">
            <v-icon
                size="large"
                color="red"
            >mdi-alert-octagon</v-icon
            >
            <h1>Zugriff verweigert</h1>
            <p class="m-2 p-2">
                Du hast nicht die erforderlichen Rechte, um auf diese Seite
                zuzugreifen.
            </p>
        </div>
    </v-container>
</template>

<script setup lang="ts">
import router from "@/router";
import PageTitle from "@/components/common/PageTitle.vue";

function goBack() {
    router.push({ name: "home" });
}
</script>
