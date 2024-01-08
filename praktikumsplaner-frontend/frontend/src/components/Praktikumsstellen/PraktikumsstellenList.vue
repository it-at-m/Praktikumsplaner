<template>
    <div>
        <yes-no-dialog-without-activator
            v-model="warningDialog"
            :dialogtitle="warningDialogTitle"
            :dialogtext="warningDialogText"
            @no="resetWarningDialog"
            @yes="assignNwk"
        ></yes-no-dialog-without-activator>

        <v-container>
            <v-expansion-panels multiple>
                <v-expansion-panel
                    v-for="(
                        praktikumsstellenliste, abteilung
                    ) in props.praktikumsstellenMap"
                    :key="abteilung"
                    class="custom-panel"
                >
                    <v-expansion-panel-header>{{
                        abteilung
                    }}</v-expansion-panel-header>
                    <v-expansion-panel-content>
                        <v-list>
                            <v-list-item-group>
                                <v-list-item
                                    v-for="praktikumsstelle in asPraktikumsstelleList(
                                        praktikumsstellenliste
                                    )"
                                    :key="praktikumsstelle.id"
                                    :class="{
                                        'v-list-item--active':
                                            praktikumsstelle.assignedNwk,
                                        spacer: true,
                                    }"
                                    :ripple="false"
                                    @drop="
                                        props.assignment
                                            ? drop(praktikumsstelle)
                                            : noDropAllowed()
                                    "
                                    @dragover.prevent
                                    @dragenter.prevent
                                >
                                    <PraktikumsstelleCard
                                        :praktikumsstelle="praktikumsstelle"
                                        :assignment="props.assignment"
                                    ></PraktikumsstelleCard>
                                </v-list-item>
                            </v-list-item-group>
                        </v-list>
                    </v-expansion-panel-content>
                </v-expansion-panel>
            </v-expansion-panels>
        </v-container>
    </div>
</template>
<script setup lang="ts">
import { ref, watch } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import { useNwkStore } from "@/stores/nwkStore";
import Nwk from "@/types/Nwk";
import { EventBus } from "@/stores/event-bus";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import PraktikumsstelleCard from "@/components/Praktikumsstellen/PraktikumsstelleCard.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";

const props = defineProps<{
    praktikumsstellenMap: Map<string, Praktikumsstelle[]> | undefined;
    assignment: boolean;
}>();

const nwkStore = useNwkStore();
const assignedNwkID = ref(nwkStore.nwk);
const warningDialog = ref<boolean>(false);
const warningDialogTitle = ref<string>(
    "Warnung. Wollen sie wirklich fortfahren?"
);
const warningDialogText = ref<string>("");
const stelleToAssignUnassign = ref<Praktikumsstelle>();

watch(
    () => nwkStore.nwk,
    () => {
        assignedNwkID.value =
            nwkStore.nwk ?? new Nwk("", "", "", "", [], false, "", "");
    }
);

function asPraktikumsstelleList(list: unknown): Praktikumsstelle[] {
    return list as Praktikumsstelle[];
}

function drop(stelle: Praktikumsstelle) {
    if (!stelle || !stelle.id || stelle.assignedNwk) return;

    // Check if Studiums or Ausbildungspraktikumsstelle
    if (
        stelle.ausbildungsrichtung == undefined &&
        assignedNwkID.value.studiengang == undefined
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich " +
            assignedNwkID.value.vorname +
            " " +
            assignedNwkID.value.nachname +
            " auf eine Studiumspraktikumsstelle setzen, obwohl er/sie Auszubildende/r ist?\n";
    }
    if (
        stelle.studiengang == undefined &&
        assignedNwkID.value.ausbildungsrichtung == undefined
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich " +
            assignedNwkID.value.vorname +
            " " +
            assignedNwkID.value.nachname +
            " auf eine Ausbildungspraktikumsstelle setzen, obwohl er/sie Student*in ist?\n";
    }

    // Check if studiengang is the same
    if (
        stelle.studiengang &&
        assignedNwkID.value.studiengang != "FISI" &&
        stelle.studiengang != assignedNwkID.value.studiengang
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich eine/n " +
            assignedNwkID.value.studiengang +
            " Student*in auf eine " +
            stelle.studiengang +
            " Stelle setzen?\n";
    }

    // Check if requested Nwk is the same
    if (
        stelle.namentlicheAnforderung &&
        stelle.namentlicheAnforderung?.toUpperCase() !=
            assignedNwkID.value.vorname.toUpperCase() +
                " " +
                assignedNwkID.value.nachname.toUpperCase()
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich " +
            assignedNwkID.value.vorname +
            " " +
            assignedNwkID.value.nachname +
            " auf diese Stelle setzen obwohl explizit " +
            stelle.namentlicheAnforderung +
            " angefordert wurde?\n";
    }

    // Check if Nwk is in the right semester
    if (
        stelle.studiengang != undefined &&
        assignedNwkID.value.studiengang != "FISI" &&
        stelle.studiensemester
    ) {
        const expectedSemester: number = +stelle.studiensemester.substring(
            8,
            10
        );
        const actualSemester = calculateSemester();
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
        assignedNwkID.value.studiengang == "FISI" &&
        stelle.ausbildungsjahr
    ) {
        const expectedLehrjahr: number = +stelle.ausbildungsjahr.substring(
            4,
            6
        );
        const actualLehrjahr = calculateLehrjahr();
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
    if (!stelleToAssignUnassign.value || !stelleToAssignUnassign.value.id)
        return;
    stelleToAssignUnassign.value.assignedNwk = assignedNwkID.value;
    PraktikumsstellenService.assignNwk(
        stelleToAssignUnassign.value.id,
        stelleToAssignUnassign.value.assignedNwk.id
    );
    EventBus.$emit("assignedNwk", stelleToAssignUnassign.value.assignedNwk);
    resetWarningDialog();
}
function resetWarningDialog() {
    warningDialogText.value = "";
    warningDialog.value = false;
}

function calculateSemester() {
    if (assignedNwkID.value.studiengang == "FISI") return 0;
    let semester: number;
    const startYear: number =
        +assignedNwkID.value.jahrgang.substring(0, 2) + 2000;
    const currentYear: number = new Date().getFullYear();
    const difference = currentYear - startYear;
    semester = difference * 2;
    if (new Date().getMonth() > 8) semester += 1;
    if (new Date().getMonth() < 3) semester -= 1;
    return semester;
}

function calculateLehrjahr() {
    if (assignedNwkID.value.studiengang != "FISI") return 0;
    let lehrjahr: number;
    const startYear: number =
        +assignedNwkID.value.jahrgang.substring(0, 2) + 2000;
    const currentYear: number = new Date().getFullYear();
    lehrjahr = currentYear - startYear;
    if (new Date().getMonth() > 8) {
        lehrjahr += 1;
    } else {
        lehrjahr -= 1;
    }
    return lehrjahr;
}

function noDropAllowed() {
    useSnackbarStore().showMessage({
        message: "Die Zuweisung ist hier nicht erlaubt.",
        level: Levels.ERROR,
    });
}
</script>
<style scoped lang="scss">
.spacer {
    padding-bottom: 10px;
}
.custom-panel {
    background-color: #f0f0f0;
    border: 2px solid #ccc;
    margin: 2px;
}
</style>