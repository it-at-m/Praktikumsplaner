<template>
    <v-avatar
        :color="properties.backgroundColor"
        size="40"
    >
        <span> {{ initials }} </span>
    </v-avatar>
</template>
<script setup lang="ts">
import { computed } from "vue";

const properties = defineProps<{
    nwkName: string;
    backgroundColor: string;
}>();

const initials = computed(() => {
    const names = properties.nwkName.split(" ");
    return names.map((name) => name.charAt(0).toUpperCase()).join("");
});
</script>
<style scoped>
.v-avatar {
    margin: 10px;
}
</style>
