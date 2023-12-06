<template>
    <v-row class="PageHeader">
        <v-btn
            :to="{ path: backButtonUrl }"
            icon
        >
            <v-icon color="black">mdi-arrow-left</v-icon>
        </v-btn>
        <span class="text-h5">{{ props.pageHeaderText }}</span>
    </v-row>
</template>

<script setup lang="ts">
const props = defineProps<{
    pageHeaderText: string;
    backButtonUrl: string;
}>();
</script>

<style scoped>
.PageHeader {
    margin: 1%;
}
</style>