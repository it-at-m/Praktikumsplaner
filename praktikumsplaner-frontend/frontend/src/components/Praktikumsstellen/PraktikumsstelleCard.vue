<template>
    <v-container>
        <v-card
            class="full-width-card card"
            elevation="16"
            outlined
            :ripple="false"
            @click="show = !show"
        >
            <v-card-title
                >Stelle bei
                {{ props.praktikumsstelle.dienststelle }}</v-card-title
            >
            <v-card-subtitle
                v-if="props.praktikumsstelle.namentlicheAnforderung"
            >
                Namentliche Anforderung:
                {{ props.praktikumsstelle.namentlicheAnforderung }}
            </v-card-subtitle>
            <v-icon
                v-if="props.praktikumsstelle.planstelleVorhanden"
                x-large
                class="icon-top-right-position"
                >mdi-account-star</v-icon
            >
            <v-card-text class="pt-0 mt-0 mb-0 pb-0">
                <p style="white-space: pre-line">
                    {{ getCardText(props.praktikumsstelle) }}
                </p></v-card-text
            >
            <v-col
                v-if="props.praktikumsstelle.assignedNwk && props.assignment"
            >
                <v-chip
                    color="primary"
                    close
                    close-icon="mdi-close"
                    @click:close="openConfirmationDialog(praktikumsstelle)"
                    >{{
                        `${props.praktikumsstelle.assignedNwk.vorname} ${props.praktikumsstelle.assignedNwk.nachname}`
                    }}</v-chip
                ></v-col
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
                            {{ getCardDetailText(props.praktikumsstelle) }}
                        </p>
                    </v-card-text>
                </div>
            </v-expand-transition>
            <yes-no-dialog-without-activator
                v-model="unassignConfirmDialog"
                :dialogtitle="unassignDialogTitle"
                :dialogtext="unassignDialogContent"
                @no="resetUnassign"
                @yes="unassignNwk"
            ></yes-no-dialog-without-activator>
        </v-card>
    </v-container>
</template>
<script setup lang="ts">
import { ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import { EventBus } from "@/stores/event-bus";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";

const show = ref<boolean>(false);

const props = defineProps<{
    praktikumsstelle: Praktikumsstelle;
    assignment: boolean;
}>();

const unassignDialogContent = ref<string>("");
const unassignDialogTitle = ref<string>("Zuweisung aufheben?");
const unassignConfirmDialog = ref<boolean>(false);

const stelleToAssignUnassign = ref<Praktikumsstelle>();

function getCardText(stelle: Praktikumsstelle): string {
    let cardText = "";

    if (stelle.studiengang) {
        cardText +=
            "Studiengang: " +
            stelle.studiengang +
            "\n" +
            "Studiensemester: ab " +
            stelle.studiensemester?.charAt(stelle.studiensemester?.length - 1) +
            ". Semester";
    } else if (stelle.ausbildungsrichtung) {
        cardText +=
            "Ausbildungsrichtung: " +
            stelle.ausbildungsrichtung +
            "\n" +
            "Ausbildungsjahr: " +
            stelle.ausbildungsjahr?.charAt(stelle.ausbildungsjahr?.length - 1) +
            ". Ausbildungsjahr";
    }

    return cardText;
}

function getCardDetailText(stelle: Praktikumsstelle): string {
    let cardText = "";
    let dringlichkeit =
        stelle.dringlichkeit.charAt(0).toUpperCase() +
        stelle.dringlichkeit.slice(1).toLowerCase();
    cardText += "Dringlichkeit: " + dringlichkeit + "\n";
    if (stelle.programmierkenntnisse) {
        cardText += "Programmierkenntnisse: ";
        switch (stelle.programmierkenntnisse) {
            case "true":
                cardText += "Ja" + "\n";
                break;
            case "false":
                cardText += "Nein" + "\n";
                break;
            case "EGAL":
                cardText += "egal" + "\n";
                break;
        }
    }
    if (stelle.ausbildungsrichtung) {
        cardText +=
            "Projektarbeit: " + (stelle.projektarbeit ? "Ja" : "Nein") + "\n";
    }
    cardText +=
        "Ausbilder*in: " +
        stelle.oertlicheAusbilder +
        "\n" +
        "Mailadresse Ausbilder*in: " +
        stelle.email +
        "\n" +
        "Tätigkeiten: " +
        stelle.taetigkeiten;
    return cardText;
}

function unassignNwk() {
    if (stelleToAssignUnassign.value?.id) {
        PraktikumsstellenService.unassignNwk(stelleToAssignUnassign.value.id);
        EventBus.$emit(
            "unassignedNwk",
            stelleToAssignUnassign.value.assignedNwk
        );
        stelleToAssignUnassign.value.assignedNwk = undefined;
    }
    resetUnassign();
}
function openConfirmationDialog(stelle: Praktikumsstelle) {
    unassignConfirmDialog.value = true;
    stelleToAssignUnassign.value = stelle;
    unassignDialogContent.value = `Möchten sie die Zuweisung von ${stelle.assignedNwk?.vorname} ${stelle.assignedNwk?.nachname} wirklich aufheben?`;
}
function resetUnassign() {
    stelleToAssignUnassign.value = undefined;
    unassignConfirmDialog.value = false;
}
</script>
<style scoped lang="scss">
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