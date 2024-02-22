<template>
    <v-row class="PageHeader">
        <v-btn
            :to="{ path: backButtonUrl }"
            icon="mdi-arrow-left"
            elevation="0"
        >
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
    display: flex;
    align-items: center;
    justify-content: flex-start;
    margin: 1%;
}

.PageHeader > .v-btn {
    margin-right: 10px;
}

.text-h5 {
    margin: 0;
}
</style>
