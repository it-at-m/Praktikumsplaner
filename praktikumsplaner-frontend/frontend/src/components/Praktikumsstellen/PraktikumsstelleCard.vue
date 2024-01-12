<template>
    <v-container
        @drop="props.assignment ? drop($event, value) : noDropAllowed()"
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
            <v-col v-if="assignedNwk && props.assignment">
                <v-chip
                    color="primary"
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
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";

const props = defineProps<{
    value: Praktikumsstelle;
    assignment: boolean;
}>();

const emits = defineEmits<{
    (e: "input", praktikumsstelle: Praktikumsstelle): void;
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
        nwkToAssignUnassing.value.studiengang != "FISI" &&
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
        nwkToAssignUnassing.value.studiengang != "FISI" &&
        stelle.studiensemester
    ) {
        const expectedSemester: number = +stelle.studiensemester.substring(
            8,
            10
        );
        const actualSemester = calculateSemester(nwkToAssignUnassing.value);
        if (expectedSemester > actualSemester) {
            warningDialogText.value +=
                "Wollen sie wirklich eine/n Student*in im " +
                actualSemester +
                " Semester auf diese Stelle setzen, obwohl ein/e Student*in ab dem " +
                expectedSemester +
                " Semester gefordert ist?\n";
        }
    }

    // Check if Nwk is in the right Lehrjahr
    if (
        stelle.ausbildungsrichtung != undefined &&
        nwkToAssignUnassing.value.studiengang == "FISI" &&
        stelle.ausbildungsjahr
    ) {
        const expectedLehrjahr: number = +stelle.ausbildungsjahr.substring(
            4,
            6
        );
        const actualLehrjahr = calculateLehrjahr(nwkToAssignUnassing.value);
        if (expectedLehrjahr > actualLehrjahr) {
            warningDialogText.value +=
                "Wollen sie wirklich eine/n Auszubildende/n im " +
                actualLehrjahr +
                " Lehrjahr auf diese Stelle setzen, obwohl eine/n Auszubildende/n ab dem " +
                expectedLehrjahr +
                " Lehrjahr gefordert ist?\n";
        }
    }

    stelleToAssignUnassign.value = stelle;
    if (warningDialogText.value == "") {
        assignNwk();
    } else {
        warningDialog.value = true;
    }
}

function assignNwk() {
    if (!stelleToAssignUnassign.value || !stelleToAssignUnassign.value.id) {
        nwkToAssignUnassing.value = undefined;
        return;
    }

    stelleToAssignUnassign.value.assignedNwk = nwkToAssignUnassing.value;
    PraktikumsstellenService.assignNwk(
        stelleToAssignUnassign.value.id || "",
        stelleToAssignUnassign.value.assignedNwk?.id
    );
    assignedNwk.value = nwkToAssignUnassing.value;
    emits("input", stelleToAssignUnassign.value);
    EventBus.$emit("assignedNwk", stelleToAssignUnassign.value.assignedNwk);
    resetWarningDialog();
}

function resetWarningDialog() {
    warningDialogText.value = "";
    warningDialog.value = false;
}

function calculateSemester(nwk: Nwk) {
    if (!nwk) return -1;
    if (nwk && nwk.studiengang == "FISI") return 0;
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
    if (nwk.studiengang != "FISI") return 0;

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
    if (stelleToAssignUnassign.value?.id) {
        PraktikumsstellenService.unassignNwk(stelleToAssignUnassign.value.id);
        EventBus.$emit(
            "unassignedNwk",
            stelleToAssignUnassign.value.assignedNwk
        );
        stelleToAssignUnassign.value.assignedNwk = undefined;
        assignedNwk.value = undefined;
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
function noDropAllowed() {
    useSnackbarStore().showMessage({
        message: "Die Zuweisung ist hier nicht erlaubt.",
        level: Levels.ERROR,
    });
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