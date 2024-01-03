<template>
    <v-container>
        <page-title
            back-button-url="/"
            page-header-text="Praktikumsplätze"
        ></page-title>

        <div v-if="!activeMeldezeitraum">
            <v-row class="align-center">
                <v-col
                    cols="auto"
                    class="d-flex align-center"
                >
                    <v-icon
                        color="blue"
                        large
                        >mdi-information-outline</v-icon
                    >
                </v-col>
                <v-col class="d-flex align-center">
                    <p class="mt-5">
                        Ihre örtliche Ausbildungsleitung hat die Meldung von
                        Stellen noch nicht freigegeben, daher können aktuell
                        leider noch keine Praktikumsplätze gemeldet werden.
                    </p>
                </v-col>
            </v-row>
        </div>
        <div
            v-else
            class="flex-container"
        >
            <v-row>
                <v-col cols="10"></v-col>
                <v-col cols="2">
                    <two-choice-dialog-cards
                        v-model="twoChoiceDialogVisible"
                        buttontext="Hinzufügen"
                        icontext="mdi-plus"
                        dialogtitle="Praktikumsplatz melden"
                        dialogsubtitle="Welche Art von Praktikumsplatz möchtest du melden?"
                        choice-one="Studium"
                        choice-one-subtitle="Praktikumsplatz für Studierende "
                        choice-two="Ausbildung"
                        choice-two-subtitle="Praktikumsplatz für Auszubildende"
                        @choiceOne="toStudium"
                        @choiceTwo="toAusbildung"
                        @close="closeDialog"
                    ></two-choice-dialog-cards>
                </v-col>
            </v-row>
        </div>
    </v-container>
</template>
<script setup lang="ts">
import { onMounted, ref } from "vue";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import PageTitle from "@/components/common/PageTitle.vue";
import router from "@/router";
import TwoChoiceDialogCards from "@/components/common/TwoChoiceDialogCards.vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const activeMeldezeitraum = ref<boolean>(false);
const twoChoiceDialogVisible = ref<boolean>(false);

onMounted(() => {
    MeldezeitraumService.getCurrentMeldezeitraum()
        .then((zeitraueme) => {
            activeMeldezeitraum.value = zeitraueme.length > 0;
        })
        .then(() => {
            if (userStore.getRoles.includes("ROLE_AUSBILDUNGSLEITUNG")) {
                activeMeldezeitraum.value = true;
            }
        });
});

function toAusbildung(): void {
    router.push("/praktikumsplaetze/meldungAusbildung");
}
function toStudium(): void {
    router.push("/praktikumsplaetze/meldungStudium");
}
function closeDialog(): void {
    twoChoiceDialogVisible.value = false;
}
</script>
<style>
.flex-container {
    display: flex;
    align-items: center;
    margin-top: 30px;
}
</style>
