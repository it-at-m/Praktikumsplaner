<template>
    <v-col>
        <p><b>Excel Datei hochladen</b></p>
        <v-form
            ref="form"
            class="d-flex justify-center align-center form"
        >
            <v-card
                id="card"
                tile
                :class="{ uploadError: hasError }"
            >
                <v-toolbar
                    color="primary"
                    dense
                    dark
                >
                    <v-toolbar-title>Excel Import</v-toolbar-title></v-toolbar
                >
                <v-card-text>
                    <span class="black--text">
                        <v-icon
                            x-large
                            color="primary"
                        >
                            {{ iconUpload }}
                        </v-icon>
                        {{ textUpload }}
                    </span>
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn
                        text
                        color="primary"
                        @click="cancel()"
                    >
                        Abbrechen
                    </v-btn>
                    <v-btn
                        color="primary"
                        @click="handleFileImport()"
                        >{{ buttonActionText }}
                    </v-btn>
                    <input
                        ref="uploader"
                        class="d-none"
                        type="file"
                        :accept="excelFormat"
                        @change="onFileChanged"
                    />
                </v-card-actions>
            </v-card>
        </v-form>
        <v-btn
            outlined
            text
            color="primary"
            class="buttonEnd"
            @click="cancel()"
        >
            Zurück
        </v-btn>
    </v-col>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import ExcelService from "@/api/ExcelService";
import { Store } from "@/Store";
import router from "@/router";

const excelDatei = ref<File>();
const uploader = ref<HTMLInputElement>();
const buttonActionText = ref<string>("Auswählen");
const isReadyForUpload = ref<boolean>(false);
const isUploaded = ref<boolean>(false);
const iconUpload = ref<string>("mdi-file-alert");
const textUpload = ref<string>("Wählen Sie die gewünschte Exeldatei aus!");
const hasError = ref<boolean>(false);

const excelFormat =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
const readyForUploadButtonText = "Hochladen";
const uploadedButtonText = "fortfahren";
const iconUploadedSuccess = "mdi-paperclip-check";
const iconUploadedFailure = "mdi-paperclip-remove";
const uploadedSuccessText = "Ihre Exceldatei wurde erfolgreich Hochgeladen!";
const uploadedFailureText = "Ihre Exceldatei konnte nicht hochgeladen werden!";

onMounted(() => {
    Store.$emit("changeAppHeader", "Excel Datei hochladen");
});

function cancel() {
    router.push("/");
}

function handleFileImport() {
    if (isReadyForUpload.value == true) {
        uploadFile();
    } else if (isUploaded.value == true) {
        router.push("/");
    } else {
        // Trigger click on the FileInput
        uploader.value?.click();
    }
}

function onFileChanged(e: any) {
    excelDatei.value = e.target.files[0];
    buttonActionText.value = readyForUploadButtonText;
    isReadyForUpload.value = true;
}

function onUploadedSuccess() {
    iconUpload.value = iconUploadedSuccess;
    textUpload.value = uploadedSuccessText;
    buttonActionText.value = uploadedButtonText;
}

function onUploadedFailed() {
    iconUpload.value = iconUploadedFailure;
    textUpload.value = uploadedFailureText;
    buttonActionText.value = uploadedButtonText;
    hasError.value = true;
}

function uploadFile() {
    if (!excelDatei.value) return;
    ExcelService.uploadExcelFile(excelDatei.value)
        .then(() => onUploadedSuccess())
        .catch(() => {
            onUploadedFailed();
        })
        .finally(() => {
            isUploaded.value = true;
            isReadyForUpload.value = false;
        });
}
</script>

<style scoped>
.form {
    margin-top: 14rem;
}
.buttonEnd {
    margin-top: 32rem;
}
.uploadError {
    background-color: rgba(216, 47, 67, 0.5);
}
</style>