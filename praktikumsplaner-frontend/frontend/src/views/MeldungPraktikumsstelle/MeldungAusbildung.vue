<template>
    <v-form
        ref="form"
        lazy-validation
    >
        <h3 class="spacing-left">Praktikumstellen Meldung</h3>
        <v-container class="spacing-left, spacing-top-30">
            <v-row>
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.ausbildungsrichtung"
                        label="Ausbildungsrichtung*"
                        :items="Ausbildungsrichtung"
                        item-value="name"
                        item-text="value"
                        :rules="requiredRule"
                        :menu-props="customMenuProps"
                        outlined
                        @change="
                            () => {
                                changeVorrZuweisungsZeitraum();
                                zustelleradressverwaltung();
                            }
                        "
                    >
                    </v-select>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.ausbildungsjahr"
                        label="Ausbildungsjahr*"
                        :items="Ausbildungsjahr"
                        item-value="name"
                        item-text="value"
                        :rules="requiredRule"
                        :menu-props="customMenuProps"
                        outlined
                        @change="
                            () => {
                                changeVorrZuweisungsZeitraum();
                                zustelleradressverwaltung();
                            }
                        "
                    >
                    </v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.dienststelle"
                        label="Konkrete Dienststelle*"
                        :rules="requiredRule"
                        outlined
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.referat"
                        :items="Referat"
                        :menu-props="customMenuProps"
                        item-value="name"
                        item-text="value"
                        label="Referat"
                        outlined
                        @change="zustelleradressverwaltung()"
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.programmierkenntnisse"
                        label="Programmierkenntnisse"
                        :items="YesNoBoolean"
                        :menu-props="customMenuProps"
                        item-value="value"
                        item-text="name"
                        outlined
                        @change="zustelleradressverwaltung()"
                    >
                    </v-select>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.projektarbeit"
                        label="Projektarbeit*"
                        :items="YesNoBoolean"
                        :menu-props="customMenuProps"
                        item-value="value"
                        item-text="name"
                        :rules="booleanRule"
                        outlined
                        @change="zustelleradressverwaltung()"
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.oertlicheAusbilder"
                        label="Name örtliche Ausbilder*in*"
                        :rules="requiredRule"
                        outlined
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.email"
                        label="E-mail örtliche Ausbilderin*"
                        :rules="emailRule"
                        outlined
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.namentlicheAnforderung"
                        label="Namentliche Anforderung spez. gewünschter NWK"
                        outlined
                        @change="zustelleradressverwaltung()"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.dringlichkeit"
                        label="Dringlichkeit"
                        :items="Dringlichkeit"
                        :menu-props="customMenuProps"
                        item-value="name"
                        item-text="value"
                        :rules="requiredRule"
                        outlined
                        @change="zustelleradressverwaltung()"
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="zeitraum"
                        label="Zeitraum NWK"
                        hint="Wählen Sie Art und Jahrgang des Stuzubis aus"
                        outlined
                        disabled
                        filled
                        background-color="grey"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col />
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-textarea
                        v-model="praktikumsstelle.taetigkeiten"
                        label="Aufgaben am Praktikumsplatz*"
                        :rules="requiredRule"
                        outlined
                        height="124px"
                        @change="zustelleradressverwaltung()"
                    ></v-textarea>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row justify="space-between">
                <v-col>
                    <v-btn
                        color="primary"
                        outlined
                        :to="{ path: '/meldungAusbilder' }"
                    >
                        ZURÜCK
                    </v-btn>
                </v-col>
                <v-col cols="8" />
                <v-col>
                    <v-btn
                        color="primary"
                        @click="uploadPraktikumsstelle"
                    >
                        SPEICHERN
                    </v-btn>
                </v-col>
            </v-row>
        </v-container>
    </v-form>
</template>

<script setup lang="ts">
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import { onMounted, ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useRules } from "@/composables/rules";
import { useZeitraeume } from "@/composables/voraussichtlicherZuweisungsZeitraum";
import { Ausbildungsjahr } from "@/types/Ausbildungsjahr";
import { Referat } from "@/types/Referat";
import { YesNoBoolean } from "@/types/YesNoBoolean";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import MeldungService from "@/api/MeldungService";
import { Levels } from "@/api/error";
import { useSnackbarStore } from "@/stores/snackbar";
import router from "@/router";
import { useHeaderStore } from "@/stores/header";

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const zeitraeueme = useZeitraeume();
const zeitraum = ref<string>("");
const validationRules = useRules();
const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein!")];
const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein!"),
    validationRules.emailRule(),
];
const booleanRule = [
    validationRules.notEmptyBooleanRule("Darf nicht leer sein!"),
];
const customMenuProps = {
    offsetY: true,
};
const snackbarStore = useSnackbarStore();
const form = ref<HTMLFormElement>();
const headerStore = useHeaderStore();

onMounted(() => {
    headerStore.setHeader("Praktikumsstellen Meldung");
});

function changeVorrZuweisungsZeitraum() {
    zeitraum.value = zeitraeueme.ausbildungsZeitraum(
        praktikumsstelle.value.ausbildungsrichtung,
        praktikumsstelle.value.ausbildungsjahr
    );
}
function cancel() {
    form.value?.reset();
    router.push("/");
}
function uploadPraktikumsstelle() {
    if (!form.value?.validate()) return;
    MeldungService.uploadAusbildungsPraktikumsstelle(praktikumsstelle.value)
        .then(() =>
            snackbarStore.showMessage({
                message: "☑ Speichern war erfolgreich",
                level: Levels.SUCCESS,
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
function zustelleradressverwaltung() {
    headerStore.setHeader("ZAV - Zustelleradressverwaltung");
}
</script>
<style>
.spacing-left {
    margin-left: 30px;
}
</style>