<template>
    <v-container>
        <PageTitle
            back-button-url="/"
            page-header-text="Zuweisung"
        ></PageTitle>
        <v-row>
            <v-col cols="5">
                <active-nwk-list-for-zuweisung />
            </v-col>
            <v-divider vertical />
            <v-col cols="7">
                <praktikumsstellen-list />
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import PraktikumsstellenList from "@/components/PraktikumsstellenList.vue";
import ActiveNwkListForZuweisung from "@/components/ActiveNwkListForZuweisung.vue";
import PageTitle from "@/components/common/PageTitle.vue";
</script>