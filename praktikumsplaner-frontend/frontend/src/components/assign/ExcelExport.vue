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
const props = defineProps<{
    startDownload: boolean;
}>();

const emits = defineEmits<{
    (e: "click"): void;
    (e: "exported"): void;
}>();

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
