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
                            <NameInput v-model="nwk"></NameInput>
                        </v-list-item>

                        <v-list-item>
                            <v-row>
                                <v-col cols="6">
                                    <JahrgangInput
                                        v-model="nwk"
                                    ></JahrgangInput>
                                </v-col>
                                <v-col cols="6">
                                    <VorlesungstageSelector
                                        v-model="nwk"
                                    ></VorlesungstageSelector>
                                </v-col>
                            </v-row>
                        </v-list-item>

                        <v-row>
                            <v-list-item>
                                <StudienrichtungOrAusbildungsrichtungSelect
                                    v-model="nwk"
                                ></StudienrichtungOrAusbildungsrichtungSelect>
                            </v-list-item>
                        </v-row>
                    </v-list>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            outlined
                            @click="close()"
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
import { ref } from "vue";
import NwkService from "@/api/NwkService";
import { useRules } from "@/composables/rules";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import NwkCreate from "@/types/NwkCreate";
import VorlesungstageSelector from "@/components/nachwuchskraefte/VorlesungstageSelect.vue";
import StudienrichtungOrAusbildungsrichtungSelect from "@/components/common/StudienrichtungOrAusbildungsrichtungSelect.vue";
import NameInput from "@/components/common/NameInput.vue";
import JahrgangInput from "@/components/common/JahrgangInput.vue";
import { EventBus } from "@/stores/event-bus";

const visible = ref<boolean>(false);
const loading = ref<boolean>(false);
const form = ref<HTMLFormElement>();
const validationRules = useRules();

const nwk = ref<NwkCreate>(new NwkCreate("", "", "", [], undefined, undefined));

function close() {
    visible.value = false;
}

function clear() {
    nwk.value = new NwkCreate("", "", "", [], undefined, undefined);
    form.value?.resetValidation();
}

function saveNwk() {
    if (!form.value?.validate()) {
        return;
    }
    loading.value = true;
    close();
    NwkService.saveNwk(nwk.value)
        .then(() => {
            EventBus.$emit("nwkCreated");

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