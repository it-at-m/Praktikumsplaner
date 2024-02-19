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
                            <NameInput v-model="nwk"></NameInput>
                        </v-row>
                        <v-row>
                            <v-col>
                                <v-list-item>
                                    <JahrgangInput
                                        v-model="nwk"
                                    ></JahrgangInput>
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
                                v-model="nwk"
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
import { ref } from "vue";
import NwkService from "@/api/NwkService";
import { useRules } from "@/composables/rules";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import NwkCreate from "@/types/NwkCreate";
import VorlesungstageSelector from "@/components/nachwuchskraefte/VorlesungstageSelect.vue";
import StudienrichtungOrAusbildungsrichtungSelect from "@/components/common/StudienrichtungOrAusbildungsrichtungSelect.vue";
import NameInput from "@/components/common/NameInput.vue";
import JahrgangInput from "@/components/common/JahrgangInput.vue";

const visible = ref<boolean>(false);
const loading = ref<boolean>(false);
const form = ref<HTMLFormElement>();
const validationRules = useRules();

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