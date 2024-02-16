<template>
    <div>
        <v-dialog
            v-model="visible"
            persistent
            width="600"
        >
            <template #activator="{ props }">
                <v-btn
                    color="primary"
                    v-bind="props"
                    >Meldezeitraum Anlegen</v-btn
                >
            </template>
            <v-card>
                <v-card-title>Meldezeitraum Anlegen</v-card-title>
                <v-card-text>
                    <v-col>
                        <v-form ref="form">
                            <v-text-field
                                v-model="meldezeitraum.zeitraumName"
                                label="Zeitraumname"
                                :rules="zeitraumNameRules"
                                variant="outlined"
                                class="mb-3"
                            ></v-text-field>

                            <zeitraum-picker
                                :value="meldezeitraum.zeitraum"
                                :label="'Meldezeitraum'"
                            ></zeitraum-picker>
                        </v-form>
                    </v-col>
                </v-card-text>
                <v-card-actions>
                    <v-btn
                        variant="outlined"
                        color="primary"
                        class="ml-7 mb-2"
                        @click="clickAbbrechen()"
                    >
                        Zurück
                    </v-btn>
                    <VSpacer></VSpacer>
                    <v-btn
                        color="primary"
                        variant="elevated"
                        class="mr-7 mb-2"
                        @click="clickSpeichern()"
                    >
                        Speichern
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import ZeitraumPicker from "@/components/meldezeitraeume/ZeitraumPicker.vue";
import { useRules } from "@/composables/rules";
import Meldezeitraum from "@/types/Meldezeitraum";
import Zeitraum from "@/types/Zeitraum";

const visible = ref(false);
const meldezeitraum = ref<Meldezeitraum>(new Meldezeitraum("", new Zeitraum()));
const form = ref<HTMLFormElement>();
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

function resetForm() {
    meldezeitraum.value = new Meldezeitraum("", new Zeitraum());
    form.value?.resetValidation();
}

function clickSpeichern() {
    if (form.value?.validate()) {
        MeldezeitraumService.create(meldezeitraum.value)
            .then(() => {
                emits("meldezeitraumAdded", meldezeitraum.value);
            })
            .finally(() => {
                clickAbbrechen();
            });
    }
}

function clickAbbrechen() {
    resetForm();
    visible.value = false;
}
</script>
