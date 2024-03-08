<template>
    <v-container>
        <yes-no-dialog-without-activator
            v-model="warningDialog"
            :dialogtitle="warningDialogTitle"
            :dialogtext="warningDialogText"
            @no="resetWarningDialog"
            @yes="delPraktikumsstelle(properties.modelValue)"
        ></yes-no-dialog-without-activator>
        <v-card
            class="full-width-card card"
            elevation="6"
            :ripple="false"
            @click="show = !show"
        >
            <v-card-title
                >Stelle bei
                {{ properties.modelValue.dienststelle }}</v-card-title
            >
            <v-card-subtitle
                v-if="properties.modelValue.namentlicheAnforderung"
            >
                Namentliche Anforderung:
                {{ properties.modelValue.namentlicheAnforderung }}
            </v-card-subtitle>
            <v-icon
                v-if="properties.modelValue.planstelleVorhanden"
                size="x-large"
                class="icon-top-right-position"
                icon="mdi-account-star"
            ></v-icon>
            <v-card-text class="pt-0 mt-0 mb-0 pb-0">
                <p style="white-space: pre-line">
                    {{ getCardText(properties.modelValue) }}
                </p></v-card-text
            >
            <v-col cols="12"></v-col>
            <v-btn
                :icon="show ? 'mdi-chevron-up' : 'mdi-chevron-down'"
                class="icon-bottom-right-position"
                elevation="0"
                @click.stop="show = !show"
            >
            </v-btn>
            <v-expand-transition>
                <div v-show="show">
                    <v-divider></v-divider>
                    <v-card-text>
                        <p style="white-space: pre-line">
                            {{ getCardDetailText(properties.modelValue) }}
                        </p>
                    </v-card-text>
                    <v-card-actions>
                        <ausbildungs-praktikumsstelle-update-dialog
                            v-if="isAusbildungsPraktikumsstelle"
                            v-model="praktikumsstelle"
                        ></ausbildungs-praktikumsstelle-update-dialog>
                        <studiums-praktikumsstelle-update-dialog
                            v-else-if="isStudiumsPraktikumsstelle"
                            v-model="praktikumsstelle"
                        ></studiums-praktikumsstelle-update-dialog>
                    </v-card-actions>
                </div>
            </v-expand-transition>
            <v-card-actions>
                <v-btn
                    icon
                    @click.stop="openDialog()"
                >
                    <v-icon>mdi-delete</v-icon>
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-container>
</template>
<script setup lang="ts">
import { computed, ref } from "vue";

import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import AusbildungsPraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/AusbildungsPraktikumsstelleUpdateDialog.vue";
import StudiumsPraktikumsstelleUpdateDialog from "@/components/praktikumsplaetze/Praktikumsplaetze/StudiumsPraktikumsstelleUpdateDialog.vue";
import { useTextGenerator } from "@/composables/textGenerator";
import emitter from "@/stores/eventBus";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const properties = defineProps<{
    modelValue: Praktikumsstelle;
}>();
const emits = defineEmits<{
    (e: "updated", praktikumsstelleToUpdate: Praktikumsstelle): void;
}>();

const praktikumsstelle = computed({
    get: () => properties.modelValue,
    set: (newValue) => emits("updated", newValue),
});

const isAusbildungsPraktikumsstelle = ref<boolean>(
    PraktikumsstellenService.isAusbildunsPraktikumsstelle(
        praktikumsstelle.value
    )
);
const isStudiumsPraktikumsstelle = ref<boolean>(
    PraktikumsstellenService.isStudiumsPraktikumsstelle(praktikumsstelle.value)
);

const warningDialog = ref<boolean>(false);
const warningDialogTitle = ref("Stelle löschen?");
const warningDialogText = ref(
    "Wollen Sie die Praktikumsstelle wirklich unwiderruflich löschen?"
);
const show = ref<boolean>(false);
const generator = useTextGenerator();

function getCardText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardText(stelle);
}

function getCardDetailText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardDetailText(stelle);
}

function delPraktikumsstelle(stelle: Praktikumsstelle) {
    resetWarningDialog();
    PraktikumsstellenService.deletePraktikumsstelle(stelle!).then(() => {
        emitter.emit("nwkDeleted");
    });
}

function openDialog() {
    warningDialog.value = true;
}
function resetWarningDialog() {
    warningDialog.value = false;
}
</script>
<style scoped>
.card {
    padding-right: 45px;
}

.icon-top-right-position {
    position: absolute;
    top: 20px;
    right: 20px;
}
.icon-bottom-right-position {
    position: absolute;
    bottom: 10px;
    right: 10px;
}
</style>
