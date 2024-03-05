<template>
    <div>
        <v-btn
            color="primary"
            prepend-icon="mdi-tray-arrow-down"
            @click="clickExport"
        >
            Exportieren
        </v-btn>
        <progress-circular-overlay
            :loading="loading"
        ></progress-circular-overlay>
    </div>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";

import ExportService from "@/api/ExportService";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";

const loading = ref<boolean>(false);
const properties = defineProps<{
    startDownload: boolean;
}>();

const emits = defineEmits<{
    (e: "click"): void;
    (e: "exported"): void;
}>();

watch(
    () => properties.startDownload,
    () => {
        if (properties.startDownload) {
            downloadExcel();
            emits("exported");
        }
    }
);

function clickExport() {
    emits("click");
}

function downloadExcel() {
    ExportService.downloadExcelFile(loading);
}
</script>

<style scoped></style>
