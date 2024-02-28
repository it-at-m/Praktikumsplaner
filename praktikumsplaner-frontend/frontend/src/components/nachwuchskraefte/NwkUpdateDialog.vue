<template>
    <div>
        <v-btn
            prepend-icon="mdi-pencil-outline"
            color="primary"
            variant="outlined"
            @click="visible = true"
            >Bearbeiten</v-btn
        >
        <v-dialog
            v-model="visible"
            persistent
            max-width="550"
        >
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >NWK bearbeiten</v-card-title
                    >
                    <v-list>
                        <v-list-item>
                            <v-container>
                                <name-input v-model="nwkToUpdate"></name-input>
                            </v-container>
                        </v-list-item>
                        <v-list-item>
                            <v-container>
                                <v-row>
                                    <v-col cols="6">
                                        <jahrgang-input
                                            v-model="nwkToUpdate"
                                        ></jahrgang-input>
                                    </v-col>
                                    <v-col cols="6">
                                        <vorlesungstage-selector
                                            v-model="nwkToUpdate"
                                        ></vorlesungstage-selector>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-list-item>
                        <v-list-item>
                            <v-container>
                                <studienrichtung-or-ausbildungsrichtung-select
                                    v-model="nwkToUpdate"
                                ></studienrichtung-or-ausbildungsrichtung-select>
                            </v-container>
                        </v-list-item>
                    </v-list>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            variant="outlined"
                            @click="cancel()"
                        >
                            Abbrechen
                        </v-btn>
                        <v-btn
                            color="primary"
                            variant="flat"
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
import { ref } from "vue";

import NwkService from "@/api/NwkService";
import JahrgangInput from "@/components/common/JahrgangInput.vue";
import NameInput from "@/components/common/NameInput.vue";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import StudienrichtungOrAusbildungsrichtungSelect from "@/components/common/StudienrichtungOrAusbildungsrichtungSelect.vue";
import VorlesungstageSelector from "@/components/nachwuchskraefte/VorlesungstageSelect.vue";
import Nwk from "@/types/Nwk";

const visible = ref<boolean>(false);
const loading = ref<boolean>(false);
const form = ref<HTMLFormElement>();

const properties = defineProps<{
    nwk: Nwk;
}>();

const nwkToUpdate = ref<Nwk>(properties.nwk);

const emits = defineEmits<{
    (e: "updated"): void;
}>();

function cancel() {
    visible.value = false;
}

function updateNwk() {
    form.value?.validate();
    if (!form.value?.isValid) return;
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
