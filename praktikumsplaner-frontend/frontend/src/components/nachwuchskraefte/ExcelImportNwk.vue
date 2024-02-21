<template>
    <div>
        <v-btn
            prepend-icon="mdi-tray-arrow-up"
            color="primary"
            @click="visible = true"
        >
            Datei Hochladen
        </v-btn>
        <v-dialog
            v-model="visible"
            persistent
            max-width="550"
        >
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold">
                        Datei hochladen
                    </v-card-title>
                    <v-card-text>
                        <v-file-input
                            v-model="file"
                            :accept="excelFormat"
                            :rules="rules"
                            label="Datei auswählen"
                            prepend-icon="mdi-tray-arrow-up"
                        ></v-file-input>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            variant="outlined"
                            @click="cancel()"
                        >
                            Abbrechen
                        </v-btn>
                        <v-btn
                            color="primary"
                            variant="flat"
                            @click="uploadFile()"
                        >
                            Hochladen
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-form>
        </v-dialog>
        <progress-circular-overlay :loading="loading" />
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

import NwkService from "@/api/NwkService";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import { useRules } from "@/composables/rules";
import emitter from "@/stores/eventBus";

const visible = ref<boolean>(false);
const loading = ref<boolean>(false);
const file = ref<File[]>();
const form = ref<HTMLFormElement>();
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

function cancel() {
    visible.value = false;
    form.value?.reset();
}
function uploadFile() {
    if (!form.value?.validate() || !file.value) return;
    visible.value = false;
    loading.value = true;
    NwkService.uploadExcelFile(file.value[0])
        .then(() => {
            emitter.emit("nwkCreated");
        })
        .finally(() => {
            loading.value = false;
            form.value?.reset();
            file.value = [];
        });
}
</script>
