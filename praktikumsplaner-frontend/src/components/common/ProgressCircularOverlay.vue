<template>
    <v-overlay
        v-model="loadingComputed"
        class="align-center justify-center"
    >
        <v-progress-circular
            indeterminate
            color="primary"
            size="large"
        ></v-progress-circular>
    </v-overlay>
</template>

<script setup lang="ts">
import { computed } from "vue";

const loadingComputed = computed(() => {
    return properties.loading;
});

const properties = defineProps<{
    loading: boolean;
}>();
</script>

<style scoped></style>
