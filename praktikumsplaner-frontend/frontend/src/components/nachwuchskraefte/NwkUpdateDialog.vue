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
                    <v-icon>mdi-pencil-outline</v-icon>
                    Bearbeiten
                </v-btn>
            </template>
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >NWK bearbeiten</v-card-title
                    >
                    <v-list>
                        <v-list-item>
                            <v-text-field
                                v-model="nwkToUpdate.vorname"
                                label="Vorname"
                                :rules="nameRule"
                                outlined
                            ></v-text-field>
                        </v-list-item>
                        <v-list-item>
                            <v-text-field
                                v-model="nwkToUpdate.nachname"
                                label="Nachname"
                                :rules="nameRule"
                                outlined
                            ></v-text-field>
                        </v-list-item>
                        <v-list-item>
                            <v-text-field
                                v-model="nwkToUpdate.jahrgang"
                                label="Jahrgang"
                                :rules="jahrgangRule"
                                outlined
                            ></v-text-field>
                        </v-list-item>
                        <v-list-item>
                            <v-select
                                v-model="nwkToUpdate.studiengang"
                                label="Studienrichtung"
                                :items="Studiengang"
                                item-value="value"
                                item-text="name"
                                outlined
                                clearable
                                :rules="isStudiumOrAusbildungRule"
                                @click:clear="
                                    nwkToUpdate.studiengang = undefined
                                "
                            ></v-select>
                        </v-list-item>
                        <v-list-item>
                            <v-select
                                v-model="nwkToUpdate.ausbildungsrichtung"
                                label="Ausbildungsrichtung"
                                :items="Ausbildungsrichtung"
                                item-value="value"
                                item-text="name"
                                outlined
                                clearable
                                :rules="isStudiumOrAusbildungRule"
                                @click:clear="
                                    nwkToUpdate.ausbildungsrichtung = undefined
                                "
                            ></v-select>
                        </v-list-item>
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
                            @click="updateNwk()"
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
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import { useRules } from "@/composables/rules";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";
import Nwk from "@/types/Nwk";
import { Studiengang } from "@/types/Studiengang";

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

const props = defineProps<{
    nwk: Nwk;
}>();

const nwkToUpdate = ref<Nwk>(props.nwk);

const isStudiumOrAusbildungRule = computed(() => {
    return [
        (nwkToUpdate.value.studiengang != undefined &&
            nwkToUpdate.value.ausbildungsrichtung == undefined) ||
            (nwkToUpdate.value.studiengang == undefined &&
                nwkToUpdate.value.ausbildungsrichtung != undefined) ||
            "Es muss eine Studienrichtung oder eine Ausbildungsrichtung angegeben werden.",
    ];
});

const emits = defineEmits<{
    (e: "updated"): void;
}>();

function cancel() {
    visible.value = false;
}

function updateNwk() {
    if (!form.value?.validate()) {
        return;
    }
    loading.value = true;
    cancel();
    NwkService.updateNwk(nwkToUpdate.value)
        .then(() => {
            emits("updated");
        })
        .finally(() => {
            loading.value = false;
        });
}
</script>

<style scoped></style>
