<template>
    <div>
        <yes-no-dialog
            v-model="computedShowDialog"
            dialogtext="Sind Sie sicher, dass Sie die Zuweisungs-Mails an die Ausbilder*innen versenden wollen?"
            dialogtitle="Bestätigung des Mailversands"
            value
            @no="closeConfirmDialog"
            @yes="sendMails"
        ></yes-no-dialog>
        <undelivered-mails-dialog
            v-model:show-undelivered-mails-dialog="showUndeliveredMailsDialog"
            :faulty-stellen="faultyStellen"
        ></undelivered-mails-dialog>
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
import Praktikumsstelle from "@/types/Praktikumsstelle";

const loading = ref<boolean>(false);
const confirmSendMailDialog = ref<boolean>(false);
const showUndeliveredMailsDialog = ref<boolean>(false);

const faultyStellen = ref<Praktikumsstelle[]>([]);

const properties = defineProps<{
    showDialog: boolean;
}>();

const computedShowDialog = computed(() => {
    return properties.showDialog;
});

const emit = defineEmits(["update:showDialog"]);

function sendMails(): void {
    loading.value = true;
    MailService.sendSuccessfulAssignedMails()
        .then((fetchedStellen) => {
            faultyStellen.value = fetchedStellen;
        })
        .finally(() => {
            checkIfUndeliveredMails();
            loading.value = false;
        });
    closeConfirmDialog();
}

function closeConfirmDialog(): void {
    emit("update:showDialog", false);
    confirmSendMailDialog.value = false;
}

function checkIfUndeliveredMails() {
    if (faultyStellen.value.length > 0) {
        showUndeliveredMailsDialog.value = true;
    }
}
</script>
<style scoped></style>
