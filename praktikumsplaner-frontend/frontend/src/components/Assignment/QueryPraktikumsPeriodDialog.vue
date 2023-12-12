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
                                            horizontal="true"
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
                                        horizontal="true"
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
                                        horizontal="true"
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
                                        horizontal="true"
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
import Meldezeitraum from "@/types/Meldezeitraum";

const form = ref<HTMLFormElement>();

const confirmSendMailDialog = ref<boolean>(false);

const bsc = ref<Meldezeitraum>(new Meldezeitraum(""));
const vi = ref<Meldezeitraum>(new Meldezeitraum(""));
const bwi = ref<Meldezeitraum>(new Meldezeitraum(""));
const fisi = ref<Meldezeitraum>(new Meldezeitraum(""));

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
    confirmSendMailDialog.value = false;
    emit("update:showDialog", false);
    resetForm();
}

function closeSendMailDialog(): void {
    emit("update:showDialog", false);
    resetForm();
}

function resetForm() {
    bsc.value = new Meldezeitraum("");
    vi.value = new Meldezeitraum("");
    bwi.value = new Meldezeitraum("");
    fisi.value = new Meldezeitraum("");
    form.value?.resetValidation();
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