<template>
    <v-container>
        <PageTitle
            back-button-url="/"
            page-header-text="Zuweisung"
        ></PageTitle>
        <v-btn
            color="primary"
            @click="showSendMailDialog = true"
            >Mails senden</v-btn
        >
        <v-row>
            <v-col cols="4">
                <active-nwk-list-for-zuweisung />
            </v-col>
            <v-divider vertical />
            <v-col cols="8">
                <praktikumsstellen-list />
            </v-col>
        </v-row>
        <QueryPraktikumsPeriodDialog :show-dialog.sync="showSendMailDialog" />
    </v-container>
</template>
<script setup lang="ts">
import PraktikumsstellenList from "@/components/Assignment/PraktikumsstellenList.vue";
import ActiveNwkListForZuweisung from "@/components/Assignment/ActiveNwkListForZuweisung.vue";
import QueryPraktikumsPeriodDialog from "@/components/Assignment/QueryPraktikumsPeriodDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import { ref } from "vue";

const showSendMailDialog = ref(false);
</script>