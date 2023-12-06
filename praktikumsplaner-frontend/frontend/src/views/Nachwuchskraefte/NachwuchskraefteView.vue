<template>
    <v-container>
        <page-title
            page-header-text="Nachwuchskräfte"
            back-button-url="/"
        ></page-title>
        <v-row>
            <v-col cols="7"></v-col>
            <v-col>
                <excel-import-nwk></excel-import-nwk>
            </v-col>
            <v-col>
                <v-btn
                    disabled
                    class="primary"
                >
                    <v-icon>mdi-plus</v-icon>
                    Hinzufügen
                </v-btn>
            </v-col>
        </v-row>
        <v-row></v-row>
        <v-container class="box">
            <span> Übersicht</span>
            <active-nwk-list></active-nwk-list>
        </v-container>
        <Error-dialog
            :dialogtext="errorDialogText"
            :dialogtitle="errorDialogTitle"
            icontext="mdi mdi-alert-octagon-outline"
            iconcolor="red"
            :value="errorDialog"
            @close="errorDialog = false"
        ></Error-dialog>
    </v-container>
</template>

<script setup lang="ts">
import ExcelImportNwk from "@/components/ExcelImportNwk.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import ActiveNwkList from "@/components/ActiveNwkList.vue";
import ErrorDialog from "@/components/common/ErrorDialog.vue";
import { ref } from "vue";
import { EventBus } from "@/stores/event-bus";

const errorDialog = ref<boolean>(false);
const errorDialogText = ref<string>(
    "Ihre Exceldatei konnte nicht hochgeladen werde. Bitte überprüfen Sie die Datei und versuchen Sie es erneut."
);
const errorDialogTitle = ref<string>("Excel Import fehlgeschlagen");

function showError() {
    errorDialog.value = true;
}

EventBus.$on("excelUploadError", showError);
</script>

<style scoped>
.box {
    margin: 1%;
    border: 2px solid #0000001a;
}
</style>