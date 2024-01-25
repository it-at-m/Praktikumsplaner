<template>
    <v-container
        @drop="drop($event, value)"
        @dragover.prevent
        @dragenter.prevent
    >
        <yes-no-dialog-without-activator
            v-model="warningDialog"
            :dialogtitle="warningDialogTitle"
            :dialogtext="warningDialogText"
            @no="resetWarningDialog"
            @yes="assignNwk"
        ></yes-no-dialog-without-activator>

        <yes-no-dialog-without-activator
            v-model="unassignConfirmDialog"
            :dialogtitle="unassignDialogTitle"
            :dialogtext="unassignDialogContent"
            @no="resetUnassign"
            @yes="unassignNwk"
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
            <v-col v-if="assignedNwk">
                <v-chip
                    :color="getNwkColor(assignedNwk)"
                    close
                    close-icon="mdi-close"
                    @click:close="openConfirmationDialog(value)"
                    >{{
                        `${assignedNwk.vorname} ${assignedNwk.nachname}`
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
                            {{ getCardDetailText(props.value) }}
                        </p>
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
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import Nwk from "@/types/Nwk";
import { findStudiengangColorByValue } from "@/types/Studiengang";
import { findAusbildungsrichtungColorByValue } from "@/types/Ausbildungsrichtung";
import { useWarnings } from "@/composables/warnings";
import { useTextGenerator } from "@/composables/textGenerator";
import { valueToNameStudiensemester } from "@/types/Studiensemester";
import { valueToNameAusbildungsjahr } from "@/types/Ausbildungsjahr";

const props = defineProps<{
    value: Praktikumsstelle;
}>();

const show = ref<boolean>(false);
const unassignDialogContent = ref<string>("");
const unassignDialogTitle = ref<string>("Zuweisung aufheben?");
const unassignConfirmDialog = ref<boolean>(false);
const warningDialog = ref<boolean>(false);
const stelleToAssignUnassign = ref<Praktikumsstelle>();
const nwkToAssignUnassing = ref<Nwk>();
const warningDialogTitle = ref<string>(
    "Warnung. Wollen sie wirklich fortfahren?"
);
const warningDialogText = ref<string>("");
const assignedNwk = ref(props.value.assignedNwk);

function getCardText(stelle: Praktikumsstelle): string {
    let cardText = "";

    if (stelle.studiengang) {
        cardText += "Studiengang: " + stelle.studiengang + "\n";
        if (stelle.studiensemester) {
            cardText += "Semester: ";
            stelle.studiensemester.sort();
            for (let i = 0; i < stelle.studiensemester.length - 1; i++) {
                cardText +=
                    valueToNameStudiensemester(stelle.studiensemester[i]) +
                    ", ";
            }
            cardText +=
                valueToNameStudiensemester(
                    stelle.studiensemester[stelle.studiensemester.length - 1]
                ) + "\n";
        }
    } else if (stelle.ausbildungsrichtung) {
        cardText += "Ausbildungsrichtung: " + stelle.ausbildungsrichtung + "\n";
        if (stelle.ausbildungsjahr) {
            cardText += "Ausbildungsjahr: ";
            stelle.ausbildungsjahr.sort();
            for (let i = 0; i < stelle.ausbildungsjahr.length; i++) {
                cardText +=
                    valueToNameAusbildungsjahr(stelle.ausbildungsjahr[i]) +
                    ", ";
            }
            cardText +=
                valueToNameAusbildungsjahr(
                    stelle.ausbildungsjahr[stelle.ausbildungsjahr.length - 1]
                ) + "\n";
        }
    }

    return cardText;
}

function getCardDetailText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardDetailText(stelle);
}

function drop(event: DragEvent, stelle: Praktikumsstelle) {
    try {
        const draggedNwk: Nwk = JSON.parse(
            event.dataTransfer?.getData("application/json") as string
        );
        nwkToAssignUnassing.value = new Nwk(
            draggedNwk.id,
            draggedNwk.vorname,
            draggedNwk.nachname,
            draggedNwk.jahrgang,
            draggedNwk.vorlesungstage,
            draggedNwk.isActive,
            draggedNwk.studiengang,
            draggedNwk.ausbildungsrichtung
        );
    } catch (e) {
        return;
    }

    if (
        !stelle ||
        !stelle.id ||
        stelle.assignedNwk ||
        nwkToAssignUnassing.value.id === ""
    ) {
        return;
    }

    // Check if Studiums or Ausbildungspraktikumsstelle
    if (
        stelle.ausbildungsrichtung == undefined &&
        nwkToAssignUnassing.value.studiengang == undefined
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich " +
            nwkToAssignUnassing.value.vorname +
            " " +
            nwkToAssignUnassing.value.nachname +
            " auf eine Studiumspraktikumsstelle setzen, obwohl er/sie Auszubildende/r ist?\n";
    }

    if (
        stelle.studiengang == undefined &&
        nwkToAssignUnassing.value.ausbildungsrichtung == undefined
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich " +
            nwkToAssignUnassing.value.vorname +
            " " +
            nwkToAssignUnassing.value.nachname +
            " auf eine Ausbildungspraktikumsstelle setzen, obwohl er/sie Student*in ist?\n";
    }

    // Check if studiengang is the same
    if (
        stelle.studiengang &&
        nwkToAssignUnassing.value?.studiengang != undefined &&
        stelle.studiengang != nwkToAssignUnassing.value.studiengang
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich eine/n " +
            nwkToAssignUnassing.value.studiengang +
            " Student*in auf eine " +
            stelle.studiengang +
            " Stelle setzen?\n";
    }

    // Check if requested Nwk is the same
    if (
        stelle.namentlicheAnforderung &&
        stelle.namentlicheAnforderung?.toUpperCase() !=
            nwkToAssignUnassing.value.vorname.toUpperCase() +
                " " +
                nwkToAssignUnassing.value.nachname.toUpperCase()
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich " +
            nwkToAssignUnassing.value.vorname +
            " " +
            nwkToAssignUnassing.value.nachname +
            " auf diese Stelle setzen obwohl explizit " +
            stelle.namentlicheAnforderung +
            " angefordert wurde?\n";
    }

    // Check if Nwk is in the right semester
    if (
        stelle.studiengang != undefined &&
        nwkToAssignUnassing.value?.studiengang != undefined &&
        stelle.studiensemester
    ) {
        const expectedSemesters: number[] = [];
        for (let i = 0; i < stelle.studiensemester.length; i++) {
            expectedSemesters.push(+stelle.studiensemester[i].substring(8, 9));
        }
        const actualSemester = calculateSemester(nwkToAssignUnassing.value);
        if (!expectedSemesters.includes(actualSemester)) {
            warningDialogText.value +=
                "Wollen sie wirklich eine/n Student*in im " +
                actualSemester +
                " Semester auf diese Stelle setzen, obwohl ein/e Student*in im ";
            for (let i = 0; i < expectedSemesters.length; i++) {
                warningDialogText.value += expectedSemesters[i] + ". Semester";
                if (i < expectedSemesters.length - 1) {
                    warningDialogText.value += ", ";
                }
            }
            warningDialogText.value += " erwartet wird.\n";
        }
    }

    // Check if Nwk is in the right Lehrjahr
    if (
        stelle.ausbildungsrichtung != undefined &&
        nwkToAssignUnassing.value?.ausbildungsrichtung != undefined &&
        stelle.ausbildungsjahr
    ) {
        const expectedLehrjahre: number[] = [];
        for (let i = 0; i < stelle.ausbildungsjahr.length; i++) {
            expectedLehrjahre.push(+stelle.ausbildungsjahr[i].substring(4, 5));
        }
        const actualLehrjahr = calculateLehrjahr(nwkToAssignUnassing.value);
        if (!expectedLehrjahre.includes(actualLehrjahr)) {
            warningDialogText.value +=
                "Wollen sie wirklich eine/n Auszubildende/n im " +
                actualLehrjahr +
                ". Lehrjahr auf diese Stelle setzen, obwohl ein/e Auszubildende/r im ";
            for (let i = 0; i < expectedLehrjahre.length; i++) {
                warningDialogText.value += expectedLehrjahre[i] + ". Lehrjahr";
                if (i < expectedLehrjahre.length - 1) {
                    warningDialogText.value += ", ";
                }
            }
            warningDialogText.value += " erwartet wird.\n";
        }
    }

    stelleToAssignUnassign = stelle;
    if (warningDialogText.value == "") {
        assignNwk();
    } else {
        warningDialog.value = true;
    }
}

function assignNwk() {
    if (!stelleToAssignUnassign || !stelleToAssignUnassign.id) {
        nwkToAssignUnassing.value = undefined;
        return;
    }

    stelleToAssignUnassign.assignedNwk = nwkToAssignUnassing.value;
    PraktikumsstellenService.assignNwk(
        stelleToAssignUnassign.id || "",
        stelleToAssignUnassign.assignedNwk?.id
    );
    assignedNwk.value = nwkToAssignUnassing.value;
    EventBus.$emit("assignedNwk", stelleToAssignUnassign.assignedNwk);
    resetWarningDialog();
}

function resetWarningDialog() {
    warningDialogText.value = "";
    warningDialog.value = false;
}

function calculateSemester(nwk: Nwk) {
    if (!nwk) return -1;
    if (nwk && nwk.ausbildungsrichtung != undefined) return 0;
    let semester: number;
    const startYear: number = +nwk.jahrgang.substring(0, 2) + 2000;
    const currentYear: number = new Date().getFullYear();
    const difference = currentYear - startYear;
    semester = difference * 2;
    if (new Date().getMonth() > 8) semester += 1;
    if (new Date().getMonth() < 3) semester -= 1;
    return semester;
}

function calculateLehrjahr(nwk: Nwk) {
    if (!nwk) return -1;
    if (nwk.studiengang != undefined) return 0;
    let lehrjahr: number;
    const startYear: number = +nwk.jahrgang.substring(0, 2) + 2000;
    const currentYear: number = new Date().getFullYear();
    lehrjahr = currentYear - startYear;
    if (new Date().getMonth() > 8) {
        lehrjahr += 1;
    } else {
        lehrjahr -= 1;
    }
    return lehrjahr;
}

function unassignNwk() {
    if (stelleToAssignUnassign?.id) {
        PraktikumsstellenService.unassignNwk(stelleToAssignUnassign.id);
        EventBus.$emit("unassignedNwk", stelleToAssignUnassign.assignedNwk);
        stelleToAssignUnassign.assignedNwk = undefined;
        assignedNwk.value = undefined;
    }
    resetUnassign();
}

function openConfirmationDialog(stelle: Praktikumsstelle) {
    unassignConfirmDialog.value = true;
    stelleToAssignUnassign = stelle;
    unassignDialogContent.value = `Möchten sie die Zuweisung von ${stelle.assignedNwk?.vorname} ${stelle.assignedNwk?.nachname} wirklich aufheben?`;
}

function resetUnassign() {
    stelleToAssignUnassign = undefined;
    unassignConfirmDialog.value = false;
}

function getNwkColor(nwk: Nwk): string {
    let color = "primary";
    if (nwk.studiengang && nwk.ausbildungsrichtung == undefined) {
        color = findStudiengangColorByValue(nwk.studiengang);
    } else if (nwk.ausbildungsrichtung && nwk.studiengang == undefined) {
        color = findAusbildungsrichtungColorByValue(nwk.ausbildungsrichtung);
    }
    return color;
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