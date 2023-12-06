<template>
    <v-container>
        <v-card
            class="full-width-card"
            elevation="16"
            outlined
        >
            <v-row>
                <v-col cols="9">
                    <v-card-title
                        >Stelle bei
                        {{ praktikumsstelle.dienststelle }}</v-card-title
                    >
                </v-col>
                <v-col>
                    <v-card-text v-if="praktikumsstelle.planstelleVorhanden">
                        <v-icon x-large>mdi-account-star</v-icon>
                    </v-card-text>
                </v-col>
            </v-row>
            <v-card-text>
                <p style="white-space: pre-line">
                    {{ getCardText(praktikumsstelle) }}
                </p></v-card-text
            >
            <v-chip
                v-if="praktikumsstelle.assignedNwk"
                color="primary"
                close
                close-icon="mdi-close"
                @click:close="unassignNwk(praktikumsstelle)"
                >{{
                    `${praktikumsstelle.assignedNwk.vorname} ${praktikumsstelle.assignedNwk.nachname}`
                }}</v-chip
            >
            <v-card-actions class="custom-card-actions">
                <v-spacer></v-spacer>
                <v-btn
                    icon
                    @click="show = !show"
                >
                    <v-icon>{{
                        show ? "mdi-chevron-up" : "mdi-chevron-down"
                    }}</v-icon>
                </v-btn>
            </v-card-actions>
            <v-expand-transition>
                <div v-show="show">
                    <v-divider></v-divider>
                    <v-card-text>
                        {{ praktikumsstelle }}
                    </v-card-text>
                </div>
            </v-expand-transition>
        </v-card>
    </v-container>
</template>
<script setup lang="ts">
import { ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import { EventBus } from "@/stores/event-bus";

const show = ref<boolean>(false);

const props = defineProps<{
    praktikumsstelle: Praktikumsstelle;
}>();

function getCardText(stelle: Praktikumsstelle): string {
    let cardText = "";

    if (stelle.studienart) {
        cardText +=
            "Studiengang: " +
            stelle.studienart +
            "\n" +
            "Studiensemester: " +
            stelle.studiensemester;
    } else if (stelle.ausbildungsrichtung) {
        cardText +=
            "Ausbildungsrichtung: " +
            stelle.ausbildungsrichtung +
            "\n" +
            "Ausbildungsjahr: " +
            stelle.ausbildungsjahr;
    }

    if (stelle.namentlicheAnforderung) {
        cardText +=
            "\nNamentliche Anforderung: " + stelle.namentlicheAnforderung;
    }

    return cardText;
}

function unassignNwk(stelle: Praktikumsstelle) {
    if (stelle.id) {
        PraktikumsstellenService.unassignNwk(stelle.id);
        EventBus.$emit("unassignedNwk", stelle.assignedNwk);
        stelle.assignedNwk = undefined;
    }
}
</script>
<style scoped lang="scss">

</style>