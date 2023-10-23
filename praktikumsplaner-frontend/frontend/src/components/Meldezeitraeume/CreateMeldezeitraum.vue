<template>
    <v-container>
        <v-row justify="center">
            <v-dialog
                v-model="dialog"
                max-width="1024"
                persistent
            >
                <template #activator="{ on }">
                    <v-btn
                        x-large
                        elevation="24"
                        color="primary"
                        v-on="on"
                        ><v-icon>mdi-plus</v-icon>Meldezeitraum anlegen</v-btn
                    >
                </template>
                <v-card>
                    <v-form ref="form">
                        <v-card-title>
                            <h2>Meldezeitraum anlegen</h2>
                            <v-spacer></v-spacer>
                            <v-card-actions>
                                <v-btn
                                    color="secondary"
                                    variant="text"
                                    @click="clickAbbrechen()"
                                >
                                    Schließen
                                </v-btn>
                                <v-btn
                                    color="primary"
                                    variant="text"
                                    @click="clickSpeichern()"
                                >
                                    Anlegen
                                </v-btn>
                            </v-card-actions>
                        </v-card-title>
                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col
                                        cols="12"
                                        sm="6"
                                        md="4"
                                    >
                                        <v-text-field
                                            v-model="meldezeitraum.zeitraumName"
                                            prepend-icon="mdi-pencil"
                                            label="Zeitraumname"
                                            :rules="zeitraumNameRules"
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12">
                                        <ZeitraumPicker
                                            :value="zeitraum"
                                            @input="zeitraumChange"
                                        ></ZeitraumPicker>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-card-text>
                    </v-form>
                </v-card>
            </v-dialog>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";
import { ref } from "vue";
import Meldezeitraum from "@/types/Meldezeitraum";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import { useRules } from "@/composables/rules";
import ZeitraumPicker from "@/components/Meldezeitraeume/ZeitraumPicker.vue";
import Zeitraum from "@/types/Zeitraum";

const meldezeitraum = ref<Meldezeitraum>(new Meldezeitraum(""));
const zeitraum = ref<Zeitraum>(new Zeitraum());

const form = ref<HTMLFormElement>();
const dialog = ref<boolean>(false);
const meldezeitraumService = new MeldezeitraumService();
const maxLength = 255;
const validationRules = useRules();

const zeitraumNameRules = [
    validationRules.maxLengthRule(
        maxLength,
        "Der Name darf maximal " + maxLength + " Zeichen lang sein."
    ),
    validationRules.notEmptyRule("Der Zeitraumname darf nicht leer sein."),
];

const emits = defineEmits<{
    (e: "meldezeitraumAdded", meldezeitraum: Meldezeitraum): void;
}>();

function zeitraumChange(changedZeitraum: Zeitraum) {
    zeitraum.value = changedZeitraum;
    meldezeitraum.value.startZeitpunkt = changedZeitraum.startZeitpunkt;
    meldezeitraum.value.endZeitpunkt = changedZeitraum.endZeitpunkt;
}

function resetForm() {
    meldezeitraum.value = new Meldezeitraum("");
    zeitraum.value = new Zeitraum();
    form.value?.resetValidation();
}

function clickSpeichern() {
    if (form.value?.validate()) {
        dialog.value = false;
        meldezeitraumService
            .create(meldezeitraum.value)
            .then(() => {
                useSnackbarStore().showMessage({
                    message: "Meldezeitraum erfolgreich angelegt",
                    level: Levels.INFO,
                });
                emits("meldezeitraumAdded", meldezeitraum.value);
            })
            .catch((error) => {
                useSnackbarStore().showMessage({
                    message: error.message,
                    level: Levels.ERROR,
                });
            })
            .finally(() => {
                resetForm();
            });
    }
}

function clickAbbrechen() {
    resetForm();
    dialog.value = false;
}
</script>

<style scoped>

</style>