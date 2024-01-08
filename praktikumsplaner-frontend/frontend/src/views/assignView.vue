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
                <praktikumsstellen-list
                    :assignment="true"
                    :praktikumsstellen-map="praktikumsstellenMap"
                />
            </v-col>
        </v-row>
        <v-row>
            <v-spacer></v-spacer>
            <v-btn
                color="primary"
                class="mr-4"
                @click="showSendMailDialog = true"
                >Mails senden</v-btn
            >
        </v-row>
        <QueryPraktikumsPeriodDialog :show-dialog.sync="showSendMailDialog" />
    </v-container>
</template>
<script setup lang="ts">
import PraktikumsstellenList from "@/components/Praktikumsstellen/PraktikumsstellenList.vue";
import ActiveNwkListForZuweisung from "@/components/Assignment/ActiveNwkListForZuweisung.vue";
import QueryPraktikumsPeriodDialog from "@/components/Assignment/QueryPraktikumsPeriodDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import { onMounted, ref } from "vue";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const showSendMailDialog = ref(false);
const praktikumsstellenMap = ref<Map<string, Praktikumsstelle[]>>(new Map());

onMounted(() => {
    getAllPraktikumsstellenInMostRecentMeldezeitraum();
});
function getAllPraktikumsstellenInMostRecentMeldezeitraum() {
    PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
        "most_recent"
    ).then((fetchedStellen) => {
        praktikumsstellenMap.value = fetchedStellen;
    });
}
</script>