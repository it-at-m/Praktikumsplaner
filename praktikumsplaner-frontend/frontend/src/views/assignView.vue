<template>
    <v-container>
        <v-row>
            <v-col cols="6">
                <active-nwk-list-for-zuweisung />
                <div class="r"></div>
            </v-col>
            <v-col cols="1">
                <v-divider vertical />
            </v-col>
            <v-col>
                <praktikumsstellen-list />
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import PraktikumsstellenList from "@/components/PraktikumsstellenList.vue";
import ActiveNwkListForZuweisung from "@/components/ActiveNwkListForZuweisung.vue";
</script>