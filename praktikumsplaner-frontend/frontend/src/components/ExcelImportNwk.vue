<template>
    <div>
        <v-dialog
            v-model="visible"
            persistent
            max-width="550"
        >
            <template #activator="{ on }">
                <v-btn
                    color="primary"
                    v-on="on"
                >
                    <v-icon>mdi-tray-arrow-up</v-icon>
                    Datei Hochladen
                </v-btn>
            </template>
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >Datei hochladen</v-card-title
                    >
                    <v-list>
                        <v-list-item>
                            <v-file-input
                                v-model="excelDatei"
                                :accept="excelFormat"
                                :rules="rules"
                                label="Datei auswählen"
                                prepend-icon="mdi-tray-arrow-up"
                            >
                            </v-file-input>
                        </v-list-item>
                    </v-list>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            outlined
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
import { ref } from "vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";
import NwkService from "@/api/NwkService";
import { useRules } from "@/composables/rules";
import ErrorDialog from "@/components/common/ErrorDialog.vue";

const visible = ref<boolean>();
const excelDatei = ref<File>();
const form = ref<HTMLFormElement>();
const snackbarStore = useSnackbarStore();
const excelFormat =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
const validationRules = useRules();
const rules = [
    validationRules.fileRequiredRule(
        "Eine Excel-Datei hochladen oder abbrechen."
    ),
    validationRules.fileTypeRule(
        excelFormat,
        "Falsches Dateiformat. Es muss eine Excel-Datei hochgeladen werden."
    ),
];
const errorDialog = ref<boolean>(false);
const errorDialogText = ref<string>(
    "Ihre Exceldatei konnte nicht hochgeladen werde. Bitte überprüfen Sie die Datei und versuchen Sie es erneut."
);
const errorDialogTitle = ref<string>("Excel Import fehlgeschlagen");

function cancel() {
    visible.value = false;
    form.value?.reset();
}
function uploadFile() {
    if (!excelDatei.value || !form.value?.validate()) return;
    NwkService.uploadExcelFile(excelDatei.value)
        .then(() =>
            snackbarStore.showMessage({
                message: "Nachwuchskräfte erfolgreich angelegt.",
                level: Levels.SUCCESS,
            })
        )
        .catch(() => {
            showError();
        })
        .finally(() => {
            cancel();
        });
}

function showError() {
    visible.value = false;
    errorDialog.value = true;
}
</script>

<style scoped>
</style>