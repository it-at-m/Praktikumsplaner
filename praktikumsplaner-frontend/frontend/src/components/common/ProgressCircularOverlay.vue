<template>
    <v-overlay v-show="loading">
        <v-progress-circular
            v-show="props.loading"
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

<style scoped>

</style>