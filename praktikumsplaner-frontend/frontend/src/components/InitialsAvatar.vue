<template>
    <v-avatar
        :color="pickRandomColor()"
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
const colors = [
    "#FFC0CB", //Rosa
    "#FFFF00", //Gelb
    "#7FFFD4", //Aquamarin
    "#FF4500", //Orange-Rot
    "#ADD8E6", //Hellblau
    "#FF69B4", //Kräftiges Rosa
    "#9370DB", //Lila
    "#3CB371", //Dunkelgrün
    "#FFA500", //Orange
    "#7FFF00", //Helles grün
];

function pickRandomColor() {
    return colors[Math.floor(Math.random() * colors.length)];
}
</script>
<style scoped>
.v-avatar {
    margin: 10px;
}
</style>