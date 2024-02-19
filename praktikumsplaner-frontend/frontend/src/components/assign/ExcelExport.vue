<template>
    <div>
        <v-btn
            color="primary"
            prepend-icon="mdi-tray-arrow-down"
            @click="clickExport"
        >
            Exportieren
        </v-btn>
        <error-dialog
            v-model="errorDialogVisible"
            :dialogtext="errorDialogText"
            :dialogtitle="errorDialogTitle"
            icontext="mdi mdi-alert-octagon-outline"
            iconcolor="red"
            @close="errorDialogVisible = false"
        ></error-dialog>
        <progress-circular-overlay
            :loading="loading"
        ></progress-circular-overlay>
    </div>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";

import ExportService from "@/api/ExportService";
import ErrorDialog from "@/components/common/ErrorDialog.vue";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";

const loading = ref<boolean>(false);
const props = defineProps<{
    startDownload: boolean;
}>();

const emits = defineEmits<{
    (e: "click"): void;
    (e: "exported"): void;
}>();

const errorDialogVisible = ref<boolean>(false);
const errorDialogText = ref<string>(
    "Beim Exportieren ist ein Fehler aufgetreten."
);
const errorDialogTitle = ref<string>("Fehler");

watch(
    () => props.startDownload,
    () => {
        if (props.startDownload) {
            downloadExcel();
            emits("exported");
        }
    }
);

function clickExport() {
    emits("click");
}

function downloadExcel() {
    loading.value = true;
    ExportService.downloadExcelFile().finally(() => (loading.value = false));
}
</script>

<style scoped></style>
