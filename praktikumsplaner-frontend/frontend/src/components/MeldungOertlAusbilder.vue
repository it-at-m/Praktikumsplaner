<template>
    <v-dialog
        v-model="visible"
        persistent
    >
        <template #activator="{ on }">
            <v-btn
                color="primary"
                v-on="on"
            >
                Meldung
            </v-btn>
        </template>
        <v-form
            ref="form"
            lazy-validation
        >
            <v-card>
                <v-card-title>Praktikumsstelle Melden</v-card-title>
                <v-list>
                    <v-list-item>
                        <v-text-field
                            v-model="praktikumsstelle.referat"
                            label="Referat"
                        ></v-text-field>
                        <v-text-field
                            v-model="praktikumsstelle.dienststelle"
                            label="Dienststelle"
                            :rules="requiredRule"
                        ></v-text-field>
                    </v-list-item>
                    <v-list-item>
                        <v-text-field
                            v-model="praktikumsstelle.oertlicheAusbiler"
                            label="Örtliche Ausbilder*in"
                            :rules="requiredRule"
                        ></v-text-field>
                        <v-text-field
                            v-model="praktikumsstelle.email"
                            label="Kontakt Email örtliche Ausbilder*in"
                            :rules="requiredRule"
                        ></v-text-field>
                    </v-list-item>
                    <v-list-item>
                        <v-text-field
                            v-model="praktikumsstelle.taetigkeiten"
                            label="Tätigkeiten"
                            :rules="requiredRule"
                        ></v-text-field>
                        <v-text-field
                            v-model="praktikumsstelle.dringlichkeit"
                            label="Dringlichkeit"
                            :rules="requiredRule"
                        ></v-text-field>
                    </v-list-item>
                    <v-list-item>
                        <v-text-field
                            v-model="praktikumsstelle.namentlicheAnforderung"
                            label="Namentliche Anforderung"
                        ></v-text-field>
                    </v-list-item>
                </v-list>
                <v-card-actions>
                    <v-select
                        v-model="stuzubiSelection"
                        label="Eine Praktikumsstelle kann nur für eine von beiden Möglichkeiten gemeldet werden"
                        :items="stuzubiSelectionItems"
                        @change="changeSelectedStuzubi()"
                    >
                    </v-select>
                    <v-col cols="10"></v-col>
                </v-card-actions>
                <v-list v-show="isAusbildung">
                    <v-list-item>
                        <v-text-field
                            v-model="praktikumsstelle.projektarbeit"
                            label="Projektarbeit"
                            :rules="ausbildungsRule"
                        >
                        </v-text-field>
                        <v-text-field
                            v-model="praktikumsstelle.ausbildungsjahr"
                            label="Ausbildungsjahr"
                            :rules="ausbildungsRule"
                        >
                        </v-text-field>
                        <v-text-field
                            v-model="praktikumsstelle.ausbildungsrichtung"
                            label="Ausbildungsrichtung"
                            :rules="ausbildungsRule"
                        >
                        </v-text-field>
                    </v-list-item>
                </v-list>
                <v-spacer></v-spacer>
                <v-list v-show="isStudium">
                    <v-list-item>
                        <v-text-field
                            v-model="praktikumsstelle.programmierkenntnisse"
                            label="Programmierkenntnisse"
                            :rules="studiumsRule"
                        >
                        </v-text-field>
                        <v-text-field
                            v-model="praktikumsstelle.studiensemester"
                            label="Studiensemester"
                            :rules="studiumsRule"
                        >
                        </v-text-field>
                        <v-text-field
                            v-model="praktikumsstelle.studienart"
                            label="Studienart"
                            :rules="studiumsRule"
                        >
                        </v-text-field>
                    </v-list-item>
                </v-list>
                <v-card-actions>
                    <v-spacer />
                    <v-btn
                        text
                        @click="cancel()"
                    >
                        Abbrechen
                    </v-btn>
                    <v-btn
                        color="primary"
                        @click="uploadPraktikumsstelle()"
                    >
                        Hochladen
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-form>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref, computed } from "vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import MeldungService from "@/api/MeldungService";
import { useRules } from "@/composables/rules";

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const visible = ref<boolean>();
const stuzubiSelection = ref<string>();
const stuzubiSelectionItems = ref<string[]>(["Ausbildung", "Studium"]);
const isAusbildung = ref<boolean>(false);
const isStudium = ref<boolean>(false);
const form = ref<HTMLFormElement>();
const snackbarStore = useSnackbarStore();
const validationRules = useRules();

const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein!")];

const ausbildungsRule = computed(() => {
    return [
        validationRules.notEmptyRuleAndVisible(
            isAusbildung.value,
            "Darf nicht leer sein!"
        ),
    ];
});

const studiumsRule = computed(() => {
    return [
        validationRules.notEmptyRuleAndVisible(
            isStudium.value,
            "Darf nicht leer sein!"
        ),
    ];
});

function cancel() {
    visible.value = false;
    form.value?.reset();
}

function changeSelectedStuzubi() {
    if (stuzubiSelection.value == stuzubiSelectionItems.value[0]) {
        isStudium.value = false;
        isAusbildung.value = true;
    }
    if (stuzubiSelection.value == stuzubiSelectionItems.value[1]) {
        isAusbildung.value = false;
        isStudium.value = true;
    }
}

function uploadPraktikumsstelle() {
    if (!form.value?.validate()) return;
    console.log(praktikumsstelle.value);
    console.log(isStudium);
    console.log(isAusbildung);
    if (isStudium.value) {
        MeldungService.uploadStudiumsPraktikumsstelle(praktikumsstelle.value)
            .then(() =>
                snackbarStore.showMessage({
                    message: "NWKs erfolgreich Angelegt!",
                    level: Levels.INFO,
                })
            )
            .catch((error) => {
                snackbarStore.showMessage({
                    message: error,
                    level: Levels.ERROR,
                });
            })
            .finally(() => {
                cancel();
            });
    } else if (isAusbildung.value) {
        MeldungService.uploadAusbildungsPraktikumsstelle(praktikumsstelle.value)
            .then(() =>
                snackbarStore.showMessage({
                    message: "NWKs erfolgreich Angelegt!",
                    level: Levels.INFO,
                })
            )
            .catch((error) => {
                snackbarStore.showMessage({
                    message: error,
                    level: Levels.ERROR,
                });
            })
            .finally(() => {
                cancel();
            });
    }
}
</script>

<style scoped>
</style>