<template>
    <v-overlay v-if="loading">
        <v-progress-circular
            v-if="props.loading"
            indeterminate
            color="primary"
        >
        </v-progress-circular>
    </v-overlay>
</template>

<script setup lang="ts">
const props = defineProps<{
    loading: boolean;
}>();
</script>

<style scoped></style>
