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
                    ) in praktikumsstellen"
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
                                    @drop="drop(praktikumsstelle)"
                                    @dragover.prevent
                                    @dragenter.prevent
                                >
                                    <PraktikumsstelleCard
                                        :praktikumsstelle="praktikumsstelle"
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
import { onMounted, ref, watch } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstellenService from "@/api/PraktikumsstellenService";
import { useNwkStore } from "@/stores/nwkStore";
import Nwk from "@/types/Nwk";
import { EventBus } from "@/stores/event-bus";
import YesNoDialogWithoutActivator from "@/components/common/YesNoDialogWithoutActivator.vue";
import PraktikumsstelleCard from "@/components/PraktikumsstelleCard.vue";

const praktikumsstellen = ref<Map<string, Praktikumsstelle[]>>();
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
            nwkStore.nwk ?? new Nwk("", "", "", "", "", "", false);
    }
);

onMounted(() => {
    getAllPraktikumsstellen();
});
function getAllPraktikumsstellen() {
    PraktikumsstellenService.getAllPraktikumsstellen().then(
        (fetchedStellen) => {
            praktikumsstellen.value = fetchedStellen;
        }
    );
}

function asPraktikumsstelleList(list: unknown): Praktikumsstelle[] {
    return list as Praktikumsstelle[];
}

function drop(stelle: Praktikumsstelle) {
    if (!stelle || !stelle.id || stelle.assignedNwk) return;

    // Check if Studiums or Ausbildungspraktikumsstelle
    if (
        stelle.ausbildungsrichtung == undefined &&
        assignedNwkID.value.studiengang == "FISI"
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich " +
            assignedNwkID.value.vorname +
            " " +
            assignedNwkID.value.nachname +
            " auf eine Studiumspraktikumsstelle setzen, obwohl er/sie Auszubildende/r ist?\n";
    }
    if (
        stelle.studienart == undefined &&
        assignedNwkID.value.studiengang != "FISI"
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
        stelle.studienart &&
        assignedNwkID.value.studiengang != "FISI" &&
        stelle.studienart != assignedNwkID.value.studiengang
    ) {
        warningDialogText.value +=
            "Wollen sie wirklich eine/n " +
            assignedNwkID.value.studiengang +
            " Student*in auf eine " +
            stelle.studienart +
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
        stelle.studienart != undefined &&
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