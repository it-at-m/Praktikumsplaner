<template>
    <v-container>
        <v-row>
            <v-col cols="5">
                <active-nwk-list />
                <div class="r"></div>
            </v-col>
            <v-col>
                <v-divider vertical />
            </v-col>
            <v-col cols="5">
                <praktikumsstellen-list />
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import ActiveNwkList from "@/components/ActiveNwkList.vue";
import PraktikumsstellenList from "@/components/PraktikumsstellenList.vue";
</script>