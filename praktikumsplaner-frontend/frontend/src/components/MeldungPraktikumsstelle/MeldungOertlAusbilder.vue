<template>
    <v-form
        ref="form"
        lazy-validation
    >
        <v-card>
            <v-card-title>Praktikumsstelle Melden</v-card-title>
            <v-list>
                <v-list-item>
                    <v-col>
                        <v-select
                            v-model="praktikumsstelle.referat"
                            :items="Referat"
                            item-value="name"
                            item-text="value"
                            label="Referat"
                            prepend-icon="mdi-office-building-outline"
                            filled
                        ></v-select>
                    </v-col>
                    <v-col>
                        <v-text-field
                            v-model="praktikumsstelle.dienststelle"
                            label="Dienststelle"
                            :rules="requiredRule"
                            prepend-icon="mdi-briefcase-outline"
                            filled
                        ></v-text-field>
                    </v-col>
                </v-list-item>
                <v-list-item>
                    <v-col>
                        <v-text-field
                            v-model="praktikumsstelle.oertlicheAusbiler"
                            label="Örtliche Ausbilder*in"
                            :rules="requiredRule"
                            prepend-icon="mdi-account-tie-hat-outline"
                            filled
                        ></v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                            v-model="praktikumsstelle.email"
                            label="Kontakt E-mail örtliche Ausbilder*in"
                            :rules="emailRule"
                            prepend-icon="mdi-email-outline"
                            filled
                        ></v-text-field>
                    </v-col>
                </v-list-item>
                <v-list-item>
                    <v-col>
                        <v-textarea
                            v-model="praktikumsstelle.taetigkeiten"
                            label="Tätigkeiten"
                            :rules="requiredRule"
                            prepend-icon="mdi-format-list-checks"
                            filled
                        ></v-textarea>
                    </v-col>
                </v-list-item>
                <v-list-item>
                    <v-col>
                        <v-select
                            v-model="praktikumsstelle.dringlichkeit"
                            label="Dringlichkeit"
                            :items="Dringlichkeit"
                            item-value="name"
                            item-text="value"
                            :rules="requiredRule"
                            prepend-icon="mdi-alert-box-outline"
                            filled
                        ></v-select>
                    </v-col>
                    <v-col>
                        <v-text-field
                            v-model="praktikumsstelle.namentlicheAnforderung"
                            label="Namentliche Anforderung"
                            prepend-icon="mdi-account-star-outline"
                            filled
                        ></v-text-field>
                    </v-col>
                </v-list-item>
                <v-list-item>
                    <v-col cols="6">
                        <v-select
                            v-model="stuzubiSelection"
                            label="Studiums- oder Ausbildungspraktikumsstelle"
                            :items="stuzubiSelectionItems"
                            prepend-icon="mdi-ab-testing"
                            filled
                            @change="changeSelectedStuzubi()"
                        >
                        </v-select>
                    </v-col>
                </v-list-item>
            </v-list>
            <v-list v-show="isAusbildung">
                <v-list-item>
                    <v-col>
                        <v-select
                            v-model="praktikumsstelle.projektarbeit"
                            label="Projektarbeit"
                            :items="YesNo"
                            item-value="name"
                            item-text="value"
                            :rules="ausbildungsRule"
                            prepend-icon="mdi-briefcase-variant-outline"
                            filled
                        >
                        </v-select>
                    </v-col>
                    <v-col
                        ><v-select
                            v-model="praktikumsstelle.ausbildungsjahr"
                            label="Ausbildungsjahr"
                            :items="Ausbildungsjahr"
                            item-value="name"
                            item-text="value"
                            :rules="ausbildungsRule"
                            prepend-icon="mdi-calendar-month-outline"
                            filled
                        >
                        </v-select
                    ></v-col>
                    <v-col
                        ><v-select
                            v-model="praktikumsstelle.ausbildungsrichtung"
                            label="Ausbildungsrichtung"
                            :items="Ausbildungsrichtung"
                            item-value="name"
                            item-text="value"
                            :rules="ausbildungsRule"
                            prepend-icon="mdi-school-outline"
                            filled
                        >
                        </v-select
                    ></v-col>
                </v-list-item>
            </v-list>
            <v-list v-show="isStudium">
                <v-list-item>
                    <v-col
                        ><v-select
                            v-model="praktikumsstelle.programmierkenntnisse"
                            label="Programmierkenntnisse"
                            :items="YesNo"
                            item-value="name"
                            item-text="value"
                            :rules="studiumsRule"
                            prepend-icon="mdi-code-braces"
                            filled
                        >
                        </v-select
                    ></v-col>
                    <v-col
                        ><v-select
                            v-model="praktikumsstelle.studiensemester"
                            label="Studiensemester"
                            :items="Studiensemester"
                            item-value="name"
                            item-text="value"
                            :rules="studiumsRule"
                            prepend-icon="mdi-calendar-range-outline"
                            filled
                        >
                        </v-select
                    ></v-col>
                    <v-col
                        ><v-select
                            v-model="praktikumsstelle.studienart"
                            label="Studienart"
                            :items="Studienart"
                            item-value="name"
                            item-text="value"
                            :rules="studiumsRule"
                            prepend-icon="mdi-school-outline"
                            filled
                        >
                        </v-select
                    ></v-col>
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
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import MeldungService from "@/api/MeldungService";
import { useRules } from "@/composables/rules";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import { YesNo } from "@/types/YesNo";
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import { Studiensemester } from "@/types/Studiensemester";
import { Studienart } from "@/types/Studienart";
import { Referat } from "@/types/Referat";
import router from "@/router";

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const stuzubiSelection = ref<string>();
const stuzubiSelectionItems = ref<string[]>(["Ausbildung", "Studium"]);
const isAusbildung = ref<boolean>(false);
const isStudium = ref<boolean>(false);
const form = ref<HTMLFormElement>();
const snackbarStore = useSnackbarStore();
const validationRules = useRules();
const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein!")];
const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein!"),
    validationRules.emailRule(),
];

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
    form.value?.reset();
    router.push("/");
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
    if (isStudium.value) {
        MeldungService.uploadStudiumsPraktikumsstelle(praktikumsstelle.value)
            .then(() =>
                snackbarStore.showMessage({
                    message: "Praktikumsstelle erfolgreich Angelegt!",
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