<template>
    <v-col>
        <h2>{{ header }}</h2>
        <v-form
            ref="form"
            class="d-flex justify-center align-center form"
        >
            <v-container>
                <v-row>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                        <v-text-field
                            v-model="meldezeitraum.zeitraumName"
                            label="Zeitraumname"
                            :rules="zeitraumNameRules"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12">
                        <ZeitraumPicker :value="meldezeitraum"></ZeitraumPicker>
                    </v-col>
                </v-row>
            </v-container>
        </v-form>
        <v-btn
            outlined
            text
            color="primary"
            class="buttonEnd float-md-left"
            @click="clickAbbrechen()"
        >
            Zurück
        </v-btn>
        <v-btn
            class="buttonEnd float-md-right"
            color="primary"
            variant="text"
            @click="clickSpeichern()"
        >
            Speichern
        </v-btn>
    </v-col>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import Meldezeitraum from "@/types/Meldezeitraum";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import ZeitraumPicker from "@/components/Meldezeitraeume/ZeitraumPicker.vue";
import { sleep } from "@antfu/utils";
import router from "@/router";
import { useHeaderStore } from "@/stores/header";
import { useRules } from "@/composables/rules";

const meldezeitraum = ref<Meldezeitraum>(new Meldezeitraum(""));
const headerStore = useHeaderStore();
const form = ref<HTMLFormElement>();
const header = "Meldezeitraum";
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
    meldezeitraum.value = new Meldezeitraum("");
    form.value?.resetValidation();
}

function clickSpeichern() {
    if (form.value?.validate()) {
        MeldezeitraumService.create(meldezeitraum.value)
            .then(() => {
                emits("meldezeitraumAdded", meldezeitraum.value);
            })
            .finally(() => {
                resetForm();
                sleep(1000).then(() => {
                    router.push("/");
                });
            });
    }
}

function clickAbbrechen() {
    resetForm();
    router.push("/");
}

onMounted(() => {
    headerStore.setHeader(header);
});
</script>

<style scoped>
.buttonEnd {
    margin-top: 42rem;
    margin-bottom: 20px;
}
</style>