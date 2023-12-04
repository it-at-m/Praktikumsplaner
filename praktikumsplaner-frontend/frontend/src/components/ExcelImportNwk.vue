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
import ExcelService from "@/api/NwkService";
import router from "@/router";
import { useHeaderStore } from "@/stores/header";

const excelFormat =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
const buttonTextInputFile = "Auswählen";
const buttonTextReadyForUpload = "Hochladen";
const buttonTextUploaded = "fortfahren";
const iconBeforeUpload = "mdi-file-alert";
const iconUploadedSuccess = "mdi-paperclip-check";
const iconUploadedFailure = "mdi-paperclip-remove";
const textBeforeUpload = "Wählen Sie die gewünschte Exeldatei aus!";
const textUploadedSuccess = "Ihre Exceldatei wurde erfolgreich Hochgeladen!";
const textUploadedFailure = "Ihre Exceldatei konnte nicht hochgeladen werden!";

const excelDatei = ref<File>();
const uploader = ref<HTMLInputElement>();
const buttonActionText = ref<string>(buttonTextInputFile);
const isReadyForUpload = ref<boolean>(false);
const isUploaded = ref<boolean>(false);
const iconUpload = ref<string>(iconBeforeUpload);
const textUpload = ref<string>(textBeforeUpload);
const hasError = ref<boolean>(false);
const headerStore = useHeaderStore();

onMounted(() => {
    headerStore.setHeader("Excel Datei hochladen");
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
    buttonActionText.value = buttonTextReadyForUpload;
    isReadyForUpload.value = true;
}

function onUploadedSuccess() {
    iconUpload.value = iconUploadedSuccess;
    textUpload.value = textUploadedSuccess;
    buttonActionText.value = buttonTextUploaded;
}

function onUploadedFailed() {
    iconUpload.value = iconUploadedFailure;
    textUpload.value = textUploadedFailure;
    buttonActionText.value = buttonTextUploaded;
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

<style scoped lang="scss">
.form {
    margin-top: 14rem;
}
.buttonEnd {
    margin-top: 32rem;
}
.uploadError {
    background-color: var(--v-errorExcel-base);
}
</style>