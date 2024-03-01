<template>
    <div>
        <v-dialog
            v-model="computedShowDialog"
            max-width="850px"
            persistent
            @update:model-value="closeSendMailDialog"
        >
            <v-form ref="form">
                <v-card>
                    <v-card-title
                        >Zuweisungszeiträume der Nachwuchskräfte</v-card-title
                    >
                    <v-card-subtitle
                        >Bitte geben Sie die Zeiträume an, in denen die
                        Studien-/Ausbildungsgruppen voraussichtlich Ihren Dienst
                        antreten werden.</v-card-subtitle
                    >
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12">
                                    <v-container class="v-container-single-box">
                                        <v-col>
                                            <h4>
                                                Bachelor of Science (B.Sc.) -
                                                Informatik
                                            </h4>
                                        </v-col>
                                        <v-col>
                                            <zeitraum-picker
                                                :value="bsc"
                                                label="Zuweisungszeitraum"
                                            ></zeitraum-picker>
                                        </v-col>
                                    </v-container>
                                </v-col>
                                <v-col cols="12">
                                    <v-container class="v-container-single-box">
                                        <v-col>
                                            <h4>Verwaltungsinformatik (VI)</h4>
                                        </v-col>
                                        <zeitraum-picker
                                            :value="vi"
                                            label="Zuweisungszeitraum"
                                        ></zeitraum-picker>
                                    </v-container>
                                </v-col>
                                <v-col cols="12">
                                    <v-container class="v-container-single-box">
                                        <v-col>
                                            <h4>Wirtschaftsinformatik (BWI)</h4>
                                        </v-col>
                                        <zeitraum-picker
                                            :value="bwi"
                                            label="Zuweisungszeitraum"
                                        ></zeitraum-picker>
                                    </v-container>
                                </v-col>
                                <v-col cols="12">
                                    <v-container class="v-container-single-box">
                                        <v-col>
                                            <h4>
                                                Fachinformatiker für
                                                Systemintegration (FISI)
                                            </h4>
                                        </v-col>
                                        <zeitraum-picker
                                            :value="fisi"
                                            label="Zuweisungszeitraum"
                                        ></zeitraum-picker>
                                    </v-container>
                                </v-col>
                            </v-row>
                            <v-card-actions class="pl-0 pr-0">
                                <v-row>
                                    <v-col class="v-col-auto mr-auto">
                                        <v-btn
                                            color="primary"
                                            variant="outlined"
                                            @click="closeSendMailDialog"
                                            >Abbrechen</v-btn
                                        >
                                    </v-col>
                                    <v-col class="v-col-auto">
                                        <v-btn
                                            color="primary"
                                            variant="elevated"
                                            :disabled="!allValid"
                                            @click="openConfirmationDialog"
                                            >Weiter</v-btn
                                        >
                                        <yes-no-dialog
                                            v-model="confirmSendMailDialog"
                                            dialogtext="Sind Sie sicher, dass Sie die Zuweisungs-Mails an die Ausbilder*innen versenden wollen?"
                                            dialogtitle="Bestätigung des Mailversands"
                                            value
                                            @no="closeConfirmDialog"
                                            @yes="sendMails"
                                        ></yes-no-dialog>
                                        <undelivered-mails-dialog
                                            :faulty-stellen="faultyStellen"
                                            :show-undelivered-mails-dialog="
                                                showUndeliveredMailsDialog
                                            "
                                        ></undelivered-mails-dialog>
                                    </v-col>
                                </v-row>
                            </v-card-actions>
                        </v-container>
                    </v-card-text>
                </v-card>
            </v-form>
        </v-dialog>
        <progress-circular-overlay
            :loading="loading"
        ></progress-circular-overlay>
    </div>
</template>
<script setup lang="ts">
import { computed, ref } from "vue";

import MailService from "@/api/MailService";
import UndeliveredMailsDialog from "@/components/assign/UndeliveredMailsDialog.vue";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import ZeitraumPicker from "@/components/meldezeitraeume/ZeitraumPicker.vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Zeitraum from "@/types/Zeitraum";

const form = ref<HTMLFormElement>();

const loading = ref<boolean>(false);
const confirmSendMailDialog = ref<boolean>(false);
const showUndeliveredMailsDialog = ref<boolean>(false);

const faultyStellen = ref<Praktikumsstelle[]>([]);

const bsc = ref<Zeitraum>(new Zeitraum());
const vi = ref<Zeitraum>(new Zeitraum());
const bwi = ref<Zeitraum>(new Zeitraum());
const fisi = ref<Zeitraum>(new Zeitraum());

const props = defineProps<{
    showDialog: boolean;
}>();

const allValid = computed(() => {
    return form.value?.checkValidity();
});

const computedShowDialog = computed(() => {
    return props.showDialog;
});

const emit = defineEmits(["update:showDialog"]);

function openConfirmationDialog() {
    form.value?.validate();
    if (form.value?.isValid) {
        confirmSendMailDialog.value = true;
    }
}

function sendMails(): void {
    loading.value = true;
    const assignmentPeriods = new Map<string, Zeitraum>();
    assignmentPeriods.set("BSC", bsc.value);
    assignmentPeriods.set("VI", vi.value);
    assignmentPeriods.set("BWI", bwi.value);
    assignmentPeriods.set("FISI", fisi.value);

    const assignmentPeriodsObj = Object.fromEntries(assignmentPeriods);

    MailService.sendSuccessfulAssignedMails(assignmentPeriodsObj)
        .then((fetchedStellen) => {
            faultyStellen.value = fetchedStellen;
        })
        .finally(() => {
            loading.value = false;
        });
    checkIfUndeliveredMails();
    closeSendMailDialog();
    closeConfirmDialog();
}

function closeSendMailDialog(): void {
    emit("update:showDialog", false);
    form.value?.reset();
}

function closeConfirmDialog(): void {
    confirmSendMailDialog.value = false;
}

function checkIfUndeliveredMails() {
    if (faultyStellen.value.length > 0) {
        showUndeliveredMailsDialog.value = true;
    }
}
</script>
<style scoped>
.v-container-single-box {
    border: 2px solid grey;
    padding-top: 5px;
    padding-bottom: 5px;
}
button {
    margin-top: 10px;
}
</style>
