<template>
    <v-avatar
        color="accent"
        size="40"
    >
        <span> {{ initials }} </span>
    </v-avatar>
</template>
<script setup lang="ts">
import { computed, defineProps } from "vue";

const props = defineProps<{
    nwkName: string;
}>();

const initials = computed(() => {
    const names = props.nwkName.split(" ");
    return names.map((name) => name.charAt(0).toUpperCase()).join("");
});
</script>
<style scoped>
.v-avatar {
    margin: 10px;
}
</style>