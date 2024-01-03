<template>
    <v-container>
        <PageTitle
            back-button-url="/"
            page-header-text="Zuweisung"
        ></PageTitle>
        <v-row>
            <v-col cols="5">
                <active-nwk-list-for-zuweisung v-model="nwks" />
            </v-col>
            <v-divider vertical />
            <v-col cols="7">
                <praktikumsstellen-list
                    :praktikumsstellen="praktikumsstellen"
                />
            </v-col>
        </v-row>
        <v-row>
            <v-spacer></v-spacer>
            <v-btn
                color="primary"
                class="mr-4"
                @click="openWarningDialog"
                >Mails senden</v-btn
            >
        </v-row>
        <WarningDialog
            :visible="showWarningDialog"
            :warnings="warnings"
            @accepted="acceptedWarningDialog"
            @rejected="rejectedWarningDialog"
        />
        <QueryPraktikumsPeriodDialog :show-dialog.sync="showSendMailDialog" />
    </v-container>
</template>
<script setup lang="ts">
import PraktikumsstellenList from "@/components/Assignment/PraktikumsstellenList.vue";
import ActiveNwkListForZuweisung from "@/components/Assignment/ActiveNwkListForZuweisung.vue";
import QueryPraktikumsPeriodDialog from "@/components/Assignment/QueryPraktikumsPeriodDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import { onMounted, ref } from "vue";
import WarningDialog from "@/components/common/WarningDialog.vue";
import Warning from "@/types/Warning";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import NwkService from "@/api/NwkService";
import Nwk from "@/types/Nwk";

const showSendMailDialog = ref(false);
const showWarningDialog = ref(false);
const warnings = ref<Warning[]>([]);
const nwks = ref<Nwk[]>([]);
const praktikumsstellen = ref<Map<string, Praktikumsstelle[]>>(
    new Map<string, Praktikumsstelle[]>()
);

function collectWarnings() {
    warnings.value.push(new Warning("test", "test"));
    warnings.value.push(new Warning("test2", "test2"));
    warnings.value.push(new Warning("test3", "test3"));
    warnings.value.push(new Warning("test4", "test4"));
}

function openWarningDialog() {
    collectWarnings();
    showWarningDialog.value = true;
}

function openQueryPraktikumsPeriodDialog() {
    showSendMailDialog.value = true;
}

function acceptedWarningDialog() {
    showWarningDialog.value = false;
    openQueryPraktikumsPeriodDialog();
}

function rejectedWarningDialog() {
    showWarningDialog.value = false;
}

onMounted(() => {
    getAllActiveNwks();
    getAllPraktikumsstellen();
});

function getAllActiveNwks() {
    NwkService.getAllUnassignedNwks().then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
        console.log(fetchedNwks);
    });
}

function getAllPraktikumsstellen() {
    PraktikumsstellenService.getAllPraktikumsstellen().then(
        (fetchedStellen) => {
            for (const [key, value] of Object.entries(fetchedStellen)) {
                console.log("key:" + key + "; " + "value:" + value);
                praktikumsstellen.value.set(key, value);
            }
        }
    );
}
</script>