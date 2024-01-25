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
                @click="openMailWarningDialog"
                >Mails senden</v-btn
            >
            <ExcelExport
                :start-download="startDownload"
                @click="openExcelWarnings"
                @exported="exported"
            ></ExcelExport>
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
import PraktikumsstellenList from "@/components/praktikumsstellen/PraktikumsstellenList.vue";
import ActiveNwkListForZuweisung from "@/components/assignment/ActiveNwkListForZuweisung.vue";
import QueryPraktikumsPeriodDialog from "@/components/assignment/QueryPraktikumsPeriodDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import { onMounted, ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import WarningDialog from "@/components/common/WarningDialog.vue";
import ExcelExport from "@/components/assignment/ExcelExport.vue";
import Nwk from "@/types/Nwk";
import Warning from "@/types/Warning";
import NwkService from "@/api/NwkService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import { useWarnings } from "@/composables/warnings";

const showSendMailDialog = ref(false);
const showWarningDialog = ref(false);
const warnings = ref<Warning[]>([]);
const nwks = ref<Nwk[]>([]);
const praktikumsstellenMap = ref<Map<string, Praktikumsstelle[]>>(
    new Map<string, Praktikumsstelle[]>()
);
const startDownload = ref(false);
const isExcelWarningDialog = ref(false);

function collectWarnings() {
    const stellen: Praktikumsstelle[] = [];
    for (const value of praktikumsstellenMap.value.values()) {
        for (const stelle of value) {
            stellen.push(stelle);
        }
    }

    warnings.value = useWarnings().getAfterAssignmentWarnings(
        stellen,
        nwks.value
    );
}

function exported() {
    startDownload.value = false;
}

function openMailWarningDialog() {
    collectWarnings();
    showWarningDialog.value = true;
    isExcelWarningDialog.value = false;
}

function openExcelWarnings() {
    collectWarnings();
    showWarningDialog.value = true;
    isExcelWarningDialog.value = true;
}

function openQueryPraktikumsPeriodDialog() {
    showSendMailDialog.value = true;
}

function acceptedWarningDialog() {
    if (isExcelWarningDialog.value) {
        startDownload.value = true;
    } else {
        openQueryPraktikumsPeriodDialog();
    }
    showWarningDialog.value = false;
}

function rejectedWarningDialog() {
    showWarningDialog.value = false;
}

onMounted(() => {
    getAllActiveNwks();
    getAllPraktikumsstellenInMostRecentMeldezeitraum();
});

function getAllActiveNwks() {
    NwkService.getAllUnassignedNwks().then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
    });
}

function getAllPraktikumsstellenInMostRecentMeldezeitraum() {
    const helperMap = new Map<string, Praktikumsstelle[]>();
    PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
        "most_recent"
    ).then((fetchedStellen) => {
        for (const [key, value] of Object.entries(fetchedStellen)) {
            helperMap.set(key, value);
        }
        praktikumsstellenMap.value = helperMap;
    });
}
</script>