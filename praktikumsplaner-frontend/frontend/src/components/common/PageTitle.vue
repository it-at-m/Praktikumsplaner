<template>
    <v-row class="PageHeader">
        <v-btn
            icon
            @click="back"
        >
            <v-icon>mdi-arrow-left</v-icon>
        </v-btn>
        <span class="text-h5">{{ props.pageHeaderText }}</span>
    </v-row>
</template>

<script setup lang="ts">
import router from "@/router";

const props = defineProps<{
    pageHeaderText: string;
}>();

function back() {
    // dummy implementation until Vue 3
    router.go(-1);
}
</script>

<style scoped>
.PageHeader {
    margin: 1%;
}
</style>