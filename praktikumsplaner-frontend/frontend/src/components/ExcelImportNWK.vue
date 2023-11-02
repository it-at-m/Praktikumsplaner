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
import { EventBus } from "@/EventBus";
import router from "@/router";

const excelDatei = ref<File>();
const uploader = ref<HTMLInputElement>();
const excelFormat =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
const buttonActionText = ref<string>("Auswählen");
const isInUploadStage4 = ref<boolean>(false);
const isInUploadedStage = ref<boolean>(false);
const iconUpload = ref<string>("mdi-file-alert");
const textUpload = ref<string>("Wählen Sie die gewünschte Exeldatei aus!");

onMounted(() => {
    EventBus.$emit("changeAppHeader", "Excel Datei hochladen");
});

function cancel() {
    router.push("/");
}

function handleFileImport() {
    if (isInUploadStage4.value == true) {
        uploadFile();
    } else if (isInUploadedStage.value == true) {
        router.push("/");
    } else {
        // Trigger click on the FileInput
        uploader.value?.click();
    }
}

function onFileChanged(e: { target: { files: (File | undefined)[] } }) {
    excelDatei.value = e.target.files[0];
    buttonActionText.value = "Hochladen";
    isInUploadStage4.value = true;
}

function onUploadedSuccess() {
    iconUpload.value = "mdi-paperclip-check";
    textUpload.value = "Ihre Exceldatei wurde erfolgreich Hochgeladen!";
    buttonActionText.value = "fortfahren";
}

function onUploadedFailed() {
    iconUpload.value = "mdi-paperclip-remove";
    textUpload.value = "Ihre Exceldatei konnte nicht hochgeladen werden!";
    buttonActionText.value = "fortfahren";
    let name = document.getElementById("card") as HTMLElement;
    name?.style.setProperty("background-color", "rgba(216, 47, 67, 0.50)");
}

function uploadFile() {
    if (!excelDatei.value) return;
    ExcelService.uploadExcelFile(excelDatei.value)
        .then(() => onUploadedSuccess())
        .catch(() => {
            onUploadedFailed();
        })
        .finally(() => {
            isInUploadedStage.value = true;
            isInUploadStage4.value = false;
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
</style>