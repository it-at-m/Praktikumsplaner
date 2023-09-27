<template>
    <v-container>
        <yes-no-dialog
            v-model="saveLeaveDialog"
            dialogtitle="Wirklich verlassen?"
            dialogtext="Wollen Sie wirklich nicht die Dokumentation anschauen?"
            @no="cancel"
            @yes="leave"
        />
    </v-container>
</template>

<script setup lang="ts">
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import { useSaveLeave } from "@/composables/saveLeave";
import { ref } from "vue";

const dokumentationClicked = ref(false);
const { cancel, leave, saveLeaveDialog } = useSaveLeave(isDirty);

function isDirty(): boolean {
    return !dokumentationClicked.value;
}
</script>
