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
                                            label="Titel*"
                                            :rules="zeitraumNameRules"
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12">
                                        <v-row>
                                            <v-col cols="6">
                                                <DatePicker
                                                    ref="startDate"
                                                    v-model="
                                                        meldezeitraum.startZeitpunkt
                                                    "
                                                    prepend-icon="mdi-calendar-start"
                                                    label="Startzeitpunkt"
                                                    :rules="startZeitpunktRules"
                                                >
                                                </DatePicker>
                                            </v-col>
                                            <v-col cols="6">
                                                <DatePicker
                                                    ref="endDate"
                                                    v-model="
                                                        meldezeitraum.endZeitpunkt
                                                    "
                                                    prepend-icon="mdi-calendar-end"
                                                    label="Endzeitpunkt"
                                                    :rules="endZeitpunktRules"
                                                >
                                                </DatePicker>
                                            </v-col>
                                        </v-row>
                                    </v-col>
                                </v-row>
                            </v-container>
                            <small>*Pflichtfelder</small>
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
import { computed, ref, watch } from "vue";
import Meldezeitraum from "@/types/Meldezeitraum";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import { useRules } from "@/composables/rules";
import DatePicker from "@/components/common/DatePicker.vue";

const meldezeitraum = ref<Meldezeitraum>(new Meldezeitraum(""));
const form = ref<HTMLFormElement>();
const endDate = ref<DatePicker>();
const startDate = ref<DatePicker>();
const dialog = ref<boolean>(false);
const meldezeitraumService = new MeldezeitraumService();
const maxLength = 255;
const validationRules = useRules();

watch(
    () => meldezeitraum.value.startZeitpunkt,
    (startZeitpunkt) => {
        console.log("Startzeit geändert auf: " + startZeitpunkt);
        endDate.value?.validate();
    }
);
watch(
    () => meldezeitraum.value.endZeitpunkt,
    (endZeitpunkt) => {
        console.log("Endzeit geändert auf: " + endZeitpunkt);
        startDate.value?.validate();
    }
);

const zeitraumNameRules = [
    validationRules.maxLengthRule(
        maxLength,
        "Der Name darf maximal " + maxLength + " Zeichen lang sein."
    ),
    validationRules.notEmptyRule("Der Zeitraumname darf nicht leer sein."),
];
const endZeitpunktRules = computed(() => {
    return [
        validationRules.notEmptyDateRule(
            "Es muss ein Endzeitpunkt angegeben werden"
        ),
        validationRules.dateAfterRule(
            meldezeitraum.value.startZeitpunkt,
            "Der Endzeitpunkt muss nach dem Startzeitpunkt liegen"
        ),
    ];
});
const startZeitpunktRules = computed(() => {
    return [
        validationRules.notEmptyDateRule(
            "Es muss ein Startzeitpunkt angegeben werden."
        ),
        validationRules.dateBeforeRule(
            meldezeitraum.value.endZeitpunkt,
            "Der Startzeitpunkt muss vor dem Endzeitpunkt liegen"
        ),
    ];
});

const emits = defineEmits<{
    (e: "meldezeitraumAdded", meldezeitraum: Meldezeitraum): void;
}>();

function resetForm() {
    meldezeitraum.value = new Meldezeitraum("");
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
                resetForm();
                emits("meldezeitraumAdded", meldezeitraum.value);
            })
            .catch((error) => {
                useSnackbarStore().showMessage({
                    message: error.message,
                    level: Levels.ERROR,
                });
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