<template>
  <v-btn
    :prepend-icon="mdiPlus"
    color="primary"
    @click="visible = true"
  >
    Hinzufügen
  </v-btn>
  <v-dialog
    v-model="visible"
    persistent
    width="600"
  >
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
          @click="close()"
        >
          Zurück
        </v-btn>
        <v-spacer></v-spacer>
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
  <progress-circular-overlay :loading="loading"></progress-circular-overlay>
</template>

<script setup lang="ts">
import { mdiPlus } from "@mdi/js";
import { ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import ZeitraumPicker from "@/components/meldezeitraeume/ZeitraumPicker.vue";
import { useRules } from "@/composables/rules";
import Meldezeitraum from "@/types/Meldezeitraum";
import Zeitraum from "@/types/Zeitraum";

const visible = ref(false);
const loading = ref(false);
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

const emits =
  defineEmits<
    (e: "meldezeitraumAdded", meldezeitraum: Meldezeitraum) => void
  >();

function clickSpeichern() {
  form.value?.validate().then((validation: { valid: boolean }) => {
    if (!validation.valid) return;

    MeldezeitraumService.create(meldezeitraum.value, loading).then(() => {
      emits("meldezeitraumAdded", meldezeitraum.value);
      close();
    });
  });
}

function close() {
  meldezeitraum.value = new Meldezeitraum("", new Zeitraum());
  form.value?.resetValidation();
  visible.value = false;
}
</script>
