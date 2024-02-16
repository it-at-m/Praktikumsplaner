<template>
    <v-container>
        <yes-no-dialog-without-activator
            v-model="warningDialog"
            :dialogtitle="warningDialogTitle"
            :dialogtext="warningDialogText"
            @no="resetWarningDialog"
            @yes="delPraktikumsstelle(props.value.id)"
        ></yes-no-dialog-without-activator>
        <v-card
            class="full-width-card card"
            :class="{
                'custom-card-active': assignedNwk,
                spacer: true,
            }"
            elevation="16"
            outlined
            :ripple="false"
            @click="show = !show"
        >
            <v-card-title
                >Stelle bei {{ props.value.dienststelle }}</v-card-title
            >
            <v-card-subtitle v-if="props.value.namentlicheAnforderung">
                Namentliche Anforderung:
                {{ props.value.namentlicheAnforderung }}
            </v-card-subtitle>
            <v-icon
                v-if="props.value.planstelleVorhanden"
                x-large
                class="icon-top-right-position"
                >mdi-account-star</v-icon
            >
            <v-card-text class="pt-0 mt-0 mb-0 pb-0">
                <p style="white-space: pre-line">
                    {{ getCardText(props.value) }}
                </p></v-card-text
            >
            <v-btn
                icon
                class="icon-bottom-right-position"
                @click.stop="show = !show"
            >
                <v-icon>{{
                    show ? "mdi-chevron-up" : "mdi-chevron-down"
                }}</v-icon>
            </v-btn>
            <v-expand-transition>
                <div v-show="show">
                    <v-divider></v-divider>
                    <v-card-text>
                        <p style="white-space: pre-line">
                            {{ getCardDetailText(props.value) }}
                        </p>
                    </v-card-text>
                </div>
            </v-expand-transition>
            <v-card-actions>
                <v-btn @click.stop="warningDialog = true"
                    ><v-icon>mdi-delete</v-icon></v-btn
                >
            </v-card-actions>
        </v-card>
    </v-container>
</template>
<script setup lang="ts">
import { ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useTextGenerator } from "@/composables/textGenerator";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";

const props = defineProps<{
    value: Praktikumsstelle;
}>();

const emits = defineEmits<{
    (e: "deleted"): void;
}>();

const warningDialog = ref<boolean>(false);
const warningDialogTitle = ref("Löschen?");
const warningDialogText = ref(
    "Wollen Sie die Praktikumsstelle wirklich löschen?"
);
const isActive = ref<boolean>(true);
const show = ref<boolean>(false);
const assignedNwk = ref(props.value.assignedNwk);
const generator = useTextGenerator();

function getCardText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardText(stelle);
}

function getCardDetailText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardDetailText(stelle);
}

function delPraktikumsstelle(uuid: string | undefined) {
    if (props.value.id) {
        resetWarningDialog();
        PraktikumsstellenService.deletePraktikumsstelle(uuid!).then(() => {
            emits("deleted");
        });
    }
}

function resetWarningDialog() {
    warningDialog.value = false;
}
</script>
<style scoped lang="scss">
.custom-card-active {
    border-color: #cfcfcf;
    background-color: #cfcfcf;
}

.card {
    padding-right: 45px;
}
.custom-card-title {
    margin-bottom: 5px;
    padding-bottom: 5px;
}

.custom-card-text {
    margin-bottom: 5px;
    padding-bottom: 5px;
    padding-top: 1px;
}

.custom-card-actions {
    margin-top: 5px;
    padding-top: 1px;
}
.custom-card-title {
    margin-bottom: 5px;
    padding-bottom: 5px;
}

.custom-card-text {
    margin-bottom: 5px;
    padding-bottom: 5px;
    padding-top: 1px;
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