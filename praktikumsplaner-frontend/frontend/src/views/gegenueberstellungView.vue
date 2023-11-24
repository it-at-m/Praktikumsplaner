<template>
    <v-container>
        <v-row>
            <v-col cols="6">
                <active-n-w-k-list @send-n-w-k-id="handleNWKId" />
                <div class="r"></div>
            </v-col>
            <v-col cols="1">
                <v-divider vertical />
            </v-col>
            <v-col>
                <praktikumsstellen-list :data="temporaryNWKIdStorage" />
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import ActiveNWKList from "@/components/ActiveNWKList.vue";
import PraktikumsstellenList from "@/components/PraktikumsstellenList.vue";
import { ref } from "vue";

const temporaryNWKIdStorage = ref("");

function handleNWKId(nwkId: string) {
    temporaryNWKIdStorage.value = nwkId;
}
</script>