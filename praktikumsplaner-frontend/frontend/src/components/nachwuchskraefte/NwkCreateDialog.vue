<template>
    <div>
        <v-dialog
            v-model="visible"
            persistent
            max-width="550"
        >
            <template #activator="{ on }">
                <v-btn
                    color="primary"
                    v-on="on"
                >
                    <v-icon>mdi-plus</v-icon>
                    Erstellen
                </v-btn>
            </template>
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >NWK anlegen</v-card-title
                    >
                    <v-list>
                        <v-row>
                            <v-col>
                                <v-list-item>
                                    <v-text-field
                                        v-model="nwk.vorname"
                                        label="Vorname"
                                        :rules="nameRule"
                                        outlined
                                    ></v-text-field>
                                </v-list-item>
                            </v-col>
                            <v-col>
                                <v-list-item>
                                    <v-text-field
                                        v-model="nwk.nachname"
                                        label="Nachname"
                                        :rules="nameRule"
                                        outlined
                                    ></v-text-field>
                                </v-list-item>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                                <v-list-item>
                                    <v-text-field
                                        v-model="nwk.jahrgang"
                                        label="Jahrgang"
                                        :rules="jahrgangRule"
                                        outlined
                                    ></v-text-field>
                                </v-list-item>
                            </v-col>
                            <v-col>
                                <v-list-item>
                                    <VorlesungstageSelector
                                        v-model="nwk"
                                    ></VorlesungstageSelector>
                                </v-list-item>
                            </v-col>
                        </v-row>
                        <v-row>
                            <StudienrichtungOrAusbildungsrichtungSelect
                                :nwk="nwk"
                            ></StudienrichtungOrAusbildungsrichtungSelect>
                        </v-row>
                    </v-list>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            outlined
                            @click="cancel()"
                        >
                            Abbrechen
                        </v-btn>
                        <v-btn
                            color="primary"
                            @click="saveNwk()"
                        >
                            Akzeptieren
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-form>
        </v-dialog>
        <progress-circular-overlay
            :loading="loading"
        ></progress-circular-overlay>
    </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import NwkService from "@/api/NwkService";
import Nwk from "@/types/Nwk";
import { Studiengang } from "@/types/Studiengang";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import { useRules } from "@/composables/rules";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import NwkCreateDialog from "@/components/nachwuchskraefte/NwkCreateDialog.vue";
import NwkCreate from "@/types/NwkCreate";
import FetchUtils from "@/api/FetchUtils";
import VorlesungstageSelector from "@/components/nachwuchskraefte/VorlesungstageSelect.vue";
import StudienrichtungOrAusbildungsrichtungSelect from "@/components/common/StudienrichtungOrAusbildungsrichtungSelect.vue";

const visible = ref<boolean>(false);
const loading = ref<boolean>(false);
const form = ref<HTMLFormElement>();
const validationRules = useRules();
const nameRule = [
    validationRules.minLengthRule(
        2,
        "Der Name muss mindestens 2 Zeichen lang sein."
    ),
    validationRules.maxLengthRule(
        255,
        "Der Name darf maximal 255 Zeichen lang sein."
    ),
    validationRules.notEmptyRule("Der Name darf nicht leer sein."),
];
const jahrgangRule = [
    validationRules.notEmptyRule("Der Jahrgang darf nicht leer sein."),
    validationRules.regexRule(
        /^([0-9]{2})\/([0-9]{2})$/,
        "Der Jahrgang muss im Format XX/XX angegeben werden."
    ),
];

const nwk = ref<NwkCreate>(new NwkCreate("", "", "", [], undefined, undefined));

const emits = defineEmits<{
    (e: "nwkCreated"): void;
}>();

function cancel() {
    visible.value = false;
}

function saveNwk() {
    if (!form.value?.validate()) {
        return;
    }
    loading.value = true;
    cancel();
    NwkService.saveNwk(nwk.value).finally(() => {
        loading.value = false;
        emits("nwkCreated");
    });
}
</script>

<style scoped>

</style>