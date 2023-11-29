<template>
    <v-container>
        <v-row>
            <v-col cols="6">
                <active-n-w-k-list></active-n-w-k-list>
            </v-col>
            <v-col cols="1">
                <v-divider vertical />
            </v-col>
            <v-col>
                <praktikumsstellen-list></praktikumsstellen-list>
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import ActiveNWKList from "@/components/ActiveNwkList.vue";
import PraktikumsstellenList from "@/components/PraktikumsstellenList.vue";
</script>