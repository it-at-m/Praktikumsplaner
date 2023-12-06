<template>
    <v-container class="finishedBackground">
        <PageTitle
            back-button-url="/"
            page-header-text="Meldezeitraum"
        ></PageTitle>
        <CreateMeldezeitraum></CreateMeldezeitraum>
    </v-container>
</template>

<script setup lang="ts">
import CreateMeldezeitraum from "@/components/Meldezeitraeume/CreateMeldezeitraum.vue";
import PageTitle from "@/components/common/PageTitle.vue";
</script>

