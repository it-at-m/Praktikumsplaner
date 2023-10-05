<template>
    <v-dialog
        v-model="visible"
        persistent
    >
        <template #activator="{ on }">
            <v-btn
                color="primary"
                v-on="on"
            >
                Import NWK
            </v-btn>
        </template>
        <v-card>
            <v-card-title>Excel Datei mit NWKs Hochladen</v-card-title>
            <v-list>
                <v-list-item>
                    <v-file-input
                        v-model="excelDatei"
                        accept=".xlsx"
                        label="Fügen Sie eine Excel Datei ein"
                    >
                    </v-file-input>
                </v-list-item>
            </v-list>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    text
                    @click="cancel()"
                >
                    Abbrechen
                </v-btn>
                <v-btn
                    color="primary"
                    @click="uploadFile()"
                >
                    Hochladen
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue";
import ExcelService from "@/api/ExcelService";

const visible = ref<boolean>();
const excelDatei = ref<File>();

function cancel() {
    visible.value = false;
}

function uploadFile() {
    if (!excelDatei.value) return;

    // File Reader encodes as Base64
    const reader = new FileReader();
    reader.readAsDataURL(excelDatei.value);
    reader.onloadend = () => {
        if (typeof reader.result == "string") {
            // Base64 String starts after the comma
            ExcelService.uploadExcelFile(reader.result.split(",")[1]);
        }
    };
}
</script>

<style scoped>

</style>