<template>
    <v-container>
        <page-title
            back-button-url="/"
            page-header-text="Zuweisung"
        ></page-title>
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
            <v-col cols="6">
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
                prepend-icon="mdi-mail"
                color="primary"
                class="mr-4"
                @click="openMailWarningDialog"
                >Mails senden</v-btn
            >
            <excel-export
                :start-download="startDownload"
                @click="openExcelWarnings"
                @exported="exported"
            ></excel-export>
        </v-row>
        <warning-dialog
            :visible="showWarningDialog"
            :warnings="warnings"
            @accepted="acceptedWarningDialog"
            @rejected="rejectedWarningDialog"
        />
        <query-praktikums-period-dialog
            v-model:showDialog="showSendMailDialog"
        />
    </v-container>
</template>
<script setup lang="ts">
import { onMounted, ref, watch } from "vue";

import NwkService from "@/api/NwkService";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import ActiveNwkListForZuweisung from "@/components/assign/ActiveNwkListForZuweisung.vue";
import ExcelExport from "@/components/assign/ExcelExport.vue";
import PraktikumsstellenListZuweisung from "@/components/assign/PraktikumsstellenListZuweisung.vue";
import QueryPraktikumsPeriodDialog from "@/components/assign/QueryPraktikumsPeriodDialog.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import WarningDialog from "@/components/common/WarningDialog.vue";
import { useSecurity } from "@/composables/security";
import { useWarnings } from "@/composables/warningGenerator";
import router from "@/router";
import { useUserStore } from "@/stores/user";
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
const route = router.currentRoute.value;
const userStore = useUserStore();

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
    if (userStore.username !== undefined) {
        redirectIfUnauthorized();
    } else {
        // This Watcher is responsible for redirecting the user to the AccessDenied view if his roles do not suffice
        watch(
            () => userStore.roles,
            () => {
                redirectIfUnauthorized();
            }
        );
    }
    getAllActiveNwks();
    getAllPraktikumsstellenInMostRecentMeldezeitraum();
});

function redirectIfUnauthorized() {
    const requiresRoles =
        route.meta.requiresRole != undefined
            ? (route.meta.requiresRole as string[])
            : undefined;
    const security = useSecurity();
    if (
        requiresRoles !== undefined &&
        !security.checkForAnyRole(requiresRoles)
    ) {
        router.push("/AccessDenied");
    }
}

function getAllActiveNwks() {
    NwkService.getAllUnassignedNwks(loadingNwk).then((fetchedNwks) => {
        nwks.value = [...fetchedNwks];
    });
}

function getAllPraktikumsstellenInMostRecentMeldezeitraum() {
    const helperMap = new Map<string, Praktikumsstelle[]>();
    PraktikumsstellenService.getAllPraktikumsstellenInSpecificMeldezeitraum(
        "most_recent",
        loadingPraktikumsstellen
    ).then((fetchedStellen) => {
        for (const [key, value] of Object.entries(fetchedStellen)) {
            helperMap.set(key, value);
        }
        praktikumsstellenMap.value = helperMap;
    });
}
</script>
