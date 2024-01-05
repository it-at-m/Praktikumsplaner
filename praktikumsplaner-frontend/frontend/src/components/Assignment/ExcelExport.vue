<template>
    <div>
        <v-btn
            color="primary"
            @click="downloadExcel"
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
import { ref } from "vue";
import ExportService from "@/api/ExportService";

const errorDialog = ref<boolean>(false);
const errorDialogText = ref<string>(
    "Beim Exportieren ist ein Fehler aufgetreten."
);
const errorDialogTitle = ref<string>("Fehler");

function downloadExcel() {
    ExportService.downloadExcelFile();
}
</script>

<style scoped>

</style>