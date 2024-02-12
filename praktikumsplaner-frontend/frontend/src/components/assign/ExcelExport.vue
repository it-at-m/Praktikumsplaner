<template>
    <div>
        <v-btn
            color="primary"
            @click="clickExport"
        >
            <v-icon>mdi-tray-arrow-down</v-icon>
            Exportieren
        </v-btn>
        <Error-dialog
            :dialogtext="errorDialogText"
            :dialogtitle="errorDialogTitle"
            icontext="mdi mdi-alert-octagon-outline"
            iconcolor="red"
            :value="errorDialog"
            @close="errorDialog = false"
        ></Error-dialog>
    </div>
</template>

<script setup lang="ts">
import ErrorDialog from "@/components/common/ErrorDialog.vue";
import { ref, watch } from "vue";
import ExportService from "@/api/ExportService";

const props = defineProps<{
    startDownload: boolean;
}>();

const emits = defineEmits<{
    (e: "click"): void;
    (e: "exported"): void;
}>();

const errorDialog = ref<boolean>(false);
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
    ExportService.downloadExcelFile();
}
</script>

<style scoped>

</style>