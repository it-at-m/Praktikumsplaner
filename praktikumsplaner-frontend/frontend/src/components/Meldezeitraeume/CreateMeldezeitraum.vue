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
import { EventBus } from "@/EventBus";
import { sleep } from "@antfu/utils";
import router from "@/router";

const meldezeitraum = ref<Meldezeitraum>(new Meldezeitraum(""));

const form = ref<HTMLFormElement>();
const meldezeitraumService = new MeldezeitraumService();
const header = "Meldezeitraum";

const emits = defineEmits<{
    (e: "meldezeitraumAdded", meldezeitraum: Meldezeitraum): void;
}>();

function resetForm() {
    meldezeitraum.value = new Meldezeitraum("");
    form.value?.resetValidation();
}

function clickSpeichern() {
    if (form.value?.validate()) {
        meldezeitraumService
            .create(meldezeitraum.value)
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
    EventBus.$emit("changeAppHeader", header);
});
</script>

<style scoped>
.buttonEnd {
    margin-top: 42rem;
}
</style>