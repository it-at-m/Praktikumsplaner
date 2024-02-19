<template>
    <v-container>
        <PageTitle
            back-button-url="/"
            page-header-text="Zuweisung"
        ></PageTitle>
        <v-row>
            <v-col cols="5">
                <v-skeleton-loader
                    v-if="loadingNwk"
                    type="image"
                ></v-skeleton-loader>
                <active-nwk-list-for-zuweisung
                    v-else
                    v-model="nwks"
                 />
            </v-col>
            <v-divider vertical />
            <v-col cols="7">
                <v-skeleton-loader
                    v-if="loadingPraktikumsstellen"
                    type="image"
                ></v-skeleton-loader>
                <praktikumsstellen-list-zuweisung
                    v-else
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
                text="Mails senden"
                ></v-btn
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
        <QueryPraktikumsPeriodDialog v-model:showDialog="showSendMailDialog" />
    </v-container>
</template>
<script setup lang="ts">
import { onMounted, ref } from "vue";

import NwkService from "@/api/NwkService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import ActiveNwkListForZuweisung from "@/components/assign/ActiveNwkListForZuweisung.vue";
import ExcelExport from "@/components/assign/ExcelExport.vue";
import PraktikumsstellenListZuweisung from "@/components/assign/PraktikumsstellenListZuweisung.vue";
import QueryPraktikumsPeriodDialog from "@/components/assign/QueryPraktikumsPeriodDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import WarningDialog from "@/components/common/WarningDialog.vue";
import { useWarnings } from "@/composables/warningGenerator";
import Nwk from "@/types/Nwk";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Warning from "@/types/Warning";

const warningsGenerator = useWarnings();

const loadingNwk = ref(true);
const loadingPraktikumsstellen = ref(true);
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

    warnings.value = warningsGenerator.getAfterAssignmentWarnings(
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
    NwkService.getAllUnassignedNwks()
        .then((fetchedNwks) => {
            nwks.value = [...fetchedNwks];
        })
        .finally(() => {
            loadingNwk.value = false;
        });
}

function getAllPraktikumsstellenInMostRecentMeldezeitraum() {
    const helperMap = new Map<string, Praktikumsstelle[]>();
    PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
        "most_recent"
    )
        .then((fetchedStellen) => {
            for (const [key, value] of Object.entries(fetchedStellen)) {
                helperMap.set(key, value);
            }
            praktikumsstellenMap.value = helperMap;
        })
        .finally(() => {
            loadingPraktikumsstellen.value = false;
        });
}
</script>
