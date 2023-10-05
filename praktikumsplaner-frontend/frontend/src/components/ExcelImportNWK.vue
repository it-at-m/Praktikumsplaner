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
        <v-form ref="form">
            <v-card>
                <v-card-title>Excel Datei mit NWKs Hochladen</v-card-title>
                <v-list>
                    <v-list-item>
                        <v-file-input
                            v-model="excelDatei"
                            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                            :rules="fileRules"
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
        </v-form>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";
import ExcelService from "@/api/ExcelService";

const visible = ref<boolean>();
const excelDatei = ref<File>();
const form = ref<HTMLFormElement>();
const snackbarStore = useSnackbarStore();

const fileRules = [
    (value: File) =>
        !value ||
        value.type ==
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ||
        "Nur Excel Dateien sind Erlaubt",
];

function cancel() {
    visible.value = false;
    form.value?.reset();
}

function uploadFile() {
    if (!excelDatei.value || !form.value?.validate()) return;

    // File Reader encodes as Base64
    const reader = new FileReader();
    reader.readAsDataURL(excelDatei.value);
    reader.onloadend = () => {
        if (typeof reader.result == "string") {
            // Base64 String starts after the comma
            ExcelService.uploadExcelFile(reader.result.split(",")[1])
                .then(() =>
                    snackbarStore.showMessage({
                        message: "NWKs erfolgreich Angelegt!",
                        level: Levels.INFO,
                    })
                )
                .catch((error) => {
                    snackbarStore.showMessage({
                        message: error,
                        level: Levels.ERROR,
                    });
                })
                .finally(() => {
                    cancel();
                });
        }
    };
}
</script>

<style scoped>

</style>