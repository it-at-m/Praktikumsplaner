<template>
    <div>
        <v-dialog
            v-model="visible"
            persistent
            max-width="550"
        >
            <template #activator="{ props }">
                <v-btn
                    color="primary"
                    v-bind="props"
                >
                    <v-icon>mdi-plus</v-icon>
                    Erstellen
                </v-btn>
            </template>
            <v-form
                ref="form"
                class="no-scrollbar"
            >
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >NWK anlegen</v-card-title
                    >
                    <v-list>
                        <v-list-item>
                            <v-container>
                                <name-input v-model="nwk"></name-input>
                            </v-container>
                        </v-list-item>

                        <v-list-item>
                            <v-container>
                                <v-row>
                                    <v-col cols="6">
                                        <jahrgang-input
                                            v-model="nwk"
                                        ></jahrgang-input>
                                    </v-col>
                                    <v-col cols="6">
                                        <vorlesungstage-selector
                                            v-model="nwk"
                                        ></vorlesungstage-selector>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-list-item>
                        <v-list-item>
                            <v-container>
                                <studienrichtung-or-ausbildungsrichtung-select
                                    v-model="nwk"
                                ></studienrichtung-or-ausbildungsrichtung-select>
                            </v-container>
                        </v-list-item>
                    </v-list>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            variant="outlined"
                            @click="close()"
                        >
                            Abbrechen
                        </v-btn>
                        <v-btn
                            color="primary"
                            variant="flat"
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
import { ref } from "vue";

import NwkService from "@/api/NwkService";
import JahrgangInput from "@/components/common/JahrgangInput.vue";
import NameInput from "@/components/common/NameInput.vue";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import StudienrichtungOrAusbildungsrichtungSelect from "@/components/common/StudienrichtungOrAusbildungsrichtungSelect.vue";
import VorlesungstageSelector from "@/components/nachwuchskraefte/VorlesungstageSelect.vue";
import emitter from "@/stores/eventBus";
import NwkCreate from "@/types/NwkCreate";

const visible = ref<boolean>(false);
const loading = ref<boolean>(false);
const form = ref<HTMLFormElement>();

const nwk = ref<NwkCreate>(new NwkCreate("", "", "", [], undefined, undefined));

function close() {
    visible.value = false;
}

function clear() {
    nwk.value = new NwkCreate("", "", "", [], undefined, undefined);
    form.value?.resetValidation();
}

function saveNwk() {
    form.value?.validate();
    if (!form.value?.isValid) return;

    loading.value = true;
    close();
    NwkService.saveNwk(nwk.value)
        .then(() => {
            emitter.emit("nwkCreated");
        })
        .finally(() => {
            loading.value = false;
            clear();
        });
}
</script>

<style scoped>
.no-scrollbar {
    overflow: hidden !important;
}
</style>
