<template>
    <v-container>
        <v-col>
            <slot name="header"></slot>
            <v-list v-if="props.value.length > 0">
                <v-card
                    v-for="meldezeitraum in props.value"
                    :key="meldezeitraum.id"
                >
                    <v-divider></v-divider>
                    <v-list-item two-line>
                        <v-list-item-content>
                            <v-list-item-title class="justify-center">
                                {{ meldezeitraum.zeitraumName }}
                                <slot name="card-title-icon"></slot>
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                <v-icon>mdi-calendar-start</v-icon>
                                {{
                                    useFormatter().formatDateFromString(
                                        meldezeitraum.zeitraum.startZeitpunkt
                                    )
                                }}

                                <v-icon>mdi-calendar-end</v-icon>
                                {{
                                    useFormatter().formatDateFromString(
                                        meldezeitraum.zeitraum.endZeitpunkt
                                    )
                                }}
                            </v-list-item-subtitle>
                        </v-list-item-content>
                        <v-list-item-action>
                            <v-btn
                                icon
                                @click="warnBeforeDelete(meldezeitraum)"
                            >
                                <v-icon>mdi-delete</v-icon>
                            </v-btn>
                        </v-list-item-action>
                    </v-list-item>
                    <v-divider></v-divider>
                </v-card>
            </v-list>
            <slot
                v-else
                name="notfoundmessage"
            ></slot>
        </v-col>
        <yes-no-dialog-without-activator
            dialogtitle="Achtung!"
            dialogtext="Wenn Sie den Meldezeitraum löschen, werden auch alle zugehörigen Praktikumsplätze gelöscht. Wollen Sie den Meldezeitraum wirklich löschen?"
            :value="showDeleteWarningDialog"
            @no="resetDeleteWarningDialog"
            @yes="deleteMeldezeitraum"
        ></yes-no-dialog-without-activator>
        <progress-circular-overlay
            :loading="loading"
        ></progress-circular-overlay>
    </v-container>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits } from "vue";
import Meldezeitraum from "@/types/Meldezeitraum";
import { useFormatter } from "@/composables/formatter";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";

const showDeleteWarningDialog = ref(false);
const loading = ref(false);
const meldezeitraumToDelete = ref<Meldezeitraum>();

const props = defineProps<{
    value: Meldezeitraum[];
}>();

const emits = defineEmits<{
    (e: "delete", meldezeitraum: Meldezeitraum | undefined): void;
}>();

function warnBeforeDelete(meldezeitraum: Meldezeitraum) {
    showDeleteWarningDialog.value = true;
    meldezeitraumToDelete.value = meldezeitraum;
}

function resetDeleteWarningDialog() {
    showDeleteWarningDialog.value = false;
    meldezeitraumToDelete.value = undefined;
}

function deleteMeldezeitraum() {
    if (!meldezeitraumToDelete.value) {
        return;
    }
    loading.value = true;
    MeldezeitraumService.deleteMeldezeitraumById(meldezeitraumToDelete.value.id)
        .then(() => {
            emits("delete", meldezeitraumToDelete.value);
        })
        .finally(() => {
            loading.value = false;
            resetDeleteWarningDialog();
        });
}
</script>