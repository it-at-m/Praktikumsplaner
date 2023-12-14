<template>
    <v-dialog
        max-width="850px"
        :value="props.showDialog"
        persistent
        @input="closeSendMailDialog"
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
                                <v-container class="v-container">
                                    <v-col>
                                        <h4>
                                            Bachelor of Science (B.Sc.) -
                                            Informatik
                                        </h4>
                                    </v-col>
                                    <v-col>
                                        <ZeitraumPicker
                                            :value="bsc"
                                            label="Zuweisungszeitraum"
                                            :horizontal="true"
                                        ></ZeitraumPicker>
                                    </v-col>
                                </v-container>
                            </v-col>
                            <v-col cols="12">
                                <v-container class="v-container">
                                    <v-col>
                                        <h4>Verwaltungsinformatik (VI)</h4>
                                    </v-col>
                                    <ZeitraumPicker
                                        :value="vi"
                                        label="Zuweisungszeitraum"
                                        :horizontal="true"
                                    ></ZeitraumPicker>
                                </v-container>
                            </v-col>
                            <v-col cols="12">
                                <v-container class="v-container">
                                    <v-col>
                                        <h4>Wirtschaftsinformatik (BWI)</h4>
                                    </v-col>
                                    <ZeitraumPicker
                                        :value="bwi"
                                        label="Zuweisungszeitraum"
                                        :horizontal="true"
                                    ></ZeitraumPicker>
                                </v-container>
                            </v-col>
                            <v-col cols="12">
                                <v-container class="v-container">
                                    <v-col>
                                        <h4>
                                            Fachinformatiker für
                                            Systemintegration (FISI)
                                        </h4>
                                    </v-col>
                                    <ZeitraumPicker
                                        :value="fisi"
                                        label="Zuweisungszeitraum"
                                        :horizontal="true"
                                    ></ZeitraumPicker>
                                </v-container>
                            </v-col>
                        </v-row>
                        <v-card-actions class="pl-0 pr-0">
                            <v-row>
                                <v-col class="col-auto mr-auto">
                                    <v-btn
                                        color="primary"
                                        outlined
                                        @click="closeSendMailDialog"
                                        >Abbrechen</v-btn
                                    >
                                </v-col>
                                <v-col class="col-auto">
                                    <v-btn
                                        color="primary"
                                        @click="openConfirmationDialog"
                                        >Weiter</v-btn
                                    >
                                    <yes-no-dialog
                                        v-model="confirmSendMailDialog"
                                        dialogtext="Sind Sie sicher, dass Sie die Zuweisungs-Mails an die Ausbilder*innen versenden wollen?"
                                        dialogtitle="Bestätigung des Mailversands"
                                        @no="confirmSendMailDialog = false"
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
</template>
<script setup lang="ts">
import ZeitraumPicker from "@/components/Meldezeitraeume/ZeitraumPicker.vue";
import { ref } from "vue";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import Zeitraum from "@/types/Zeitraum";
import MailService from "@/api/MailService";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import UndeliveredMailsDialog from "@/components/Assignment/undeliveredMailsDialog.vue";

const form = ref<HTMLFormElement>();

const confirmSendMailDialog = ref<boolean>(false);

const faultyStellen = ref<Praktikumsstelle[]>([]);

const showUndeliveredMailsDialog = ref<boolean>(false);

const bsc = ref<Zeitraum>(new Zeitraum());
const vi = ref<Zeitraum>(new Zeitraum());
const bwi = ref<Zeitraum>(new Zeitraum());
const fisi = ref<Zeitraum>(new Zeitraum());

const props = defineProps<{
    showDialog: boolean;
}>();

const emit = defineEmits<{
    (e: "update:showDialog", b: boolean): void;
}>();

function openConfirmationDialog(): void {
    if (form.value?.validate()) {
        confirmSendMailDialog.value = true;
    }
}

function sendMails(): void {
    const assignmentPeriods = new Map<string, Zeitraum>();
    assignmentPeriods.set("BSC", bsc.value);
    assignmentPeriods.set("VI", vi.value);
    assignmentPeriods.set("BWI", bwi.value);
    assignmentPeriods.set("FISI", fisi.value);

    const assignmentPeriodsObj = Object.fromEntries(assignmentPeriods);

    MailService.sendSuccessfulAssignedMails(assignmentPeriodsObj).then(
        (fetchedStellen) => {
            faultyStellen.value = fetchedStellen;
        }
    );
    checkIfUndeliveredMails();
    closeSendMailDialog();
}

function closeSendMailDialog(): void {
    emit("update:showDialog", false);
    resetForm();
}

function resetForm() {
    bsc.value = new Zeitraum();
    vi.value = new Zeitraum();
    bwi.value = new Zeitraum();
    fisi.value = new Zeitraum();
    form.value?.resetValidation();
}

function checkIfUndeliveredMails() {
    if (faultyStellen.value.length > 0) {
        showUndeliveredMailsDialog.value = true;
    }
}
</script>
<style scoped>
.v-container {
    border: 2px solid grey;
    padding-top: 5px;
    padding-bottom: 5px;
}
button {
    margin-top: 10px;
}
</style>