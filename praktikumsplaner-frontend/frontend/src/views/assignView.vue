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
import PraktikumsstellenList from "@/components/Assignment/PraktikumsstellenList.vue";
import ActiveNwkListForZuweisung from "@/components/Assignment/ActiveNwkListForZuweisung.vue";
import QueryPraktikumsPeriodDialog from "@/components/Assignment/QueryPraktikumsPeriodDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import { onMounted, ref } from "vue";
import WarningDialog from "@/components/common/WarningDialog.vue";
import ExcelExport from "@/components/Assignment/ExcelExport.vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Nwk from "@/types/Nwk";
import Warning from "@/types/Warning";
import NwkService from "@/api/NwkService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";

const showSendMailDialog = ref(false);
const showWarningDialog = ref(false);
const warnings = ref<Warning[]>([]);
const nwks = ref<Nwk[]>([]);
const praktikumsstellen = ref<Map<string, Praktikumsstelle[]>>(
    new Map<string, Praktikumsstelle[]>()
);
const startDownload = ref(false);
const isExcelWarningDialog = ref(false);

function collectWarnings() {
    warnings.value = [];
    for (const nwk of nwks.value) {
        const warning = new Warning(
            "NWK",
            "Die NWK " +
                nwk.vorname +
                " " +
                nwk.nachname +
                " ist nicht verplant."
        );

        warnings.value.push(warning);
    }
    for (const value of praktikumsstellen.value.values()) {
        for (const stelle of value) {
            if (
                (stelle.dringlichkeit.toLocaleLowerCase() == "dringend" ||
                    stelle.dringlichkeit.toLocaleLowerCase() == "zwingend") &&
                stelle.assignedNwk == undefined
            ) {
                const warning = new Warning(
                    "Dringlichkeit",
                    "Der Praktikumsstelle " +
                        stelle.dienststelle +
                        " bei " +
                        stelle.oertlicheAusbilder +
                        " ist keine NWK zugewiesen, die Dringlichkeit ist jedoch mit " +
                        stelle.dringlichkeit +
                        " angegeben."
                );
                warnings.value.push(warning);
            }

            if (
                stelle.namentlicheAnforderung != null &&
                stelle.assignedNwk == undefined
            ) {
                const warning = new Warning(
                    "Namentliche Anforderung",
                    "Der Praktikumsstelle " +
                        stelle.dienststelle +
                        " bei " +
                        stelle.oertlicheAusbilder +
                        " ist keine NWK zugewiesen, es liegt jedoch eine namentliche Anforderung für " +
                        stelle.namentlicheAnforderung +
                        " vor."
                );
                warnings.value.push(warning);
            }
        }
    }
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
    getAllPraktikumsstellen();
});

function getAllActiveNwks() {
    NwkService.getAllUnassignedNwks().then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
    });
}

function getAllPraktikumsstellen() {
    const helperMap = new Map<string, Praktikumsstelle[]>();
    PraktikumsstellenService.getAllPraktikumsstellen().then(
        (fetchedStellen) => {
            for (const [key, value] of Object.entries(fetchedStellen)) {
                helperMap.set(key, value);
            }
            praktikumsstellen.value = helperMap;
        }
    );
}
</script>