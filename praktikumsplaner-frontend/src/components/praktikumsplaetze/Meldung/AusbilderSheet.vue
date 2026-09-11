<template>
  <v-sheet
    border
    rounded
  >
    <v-row>
      <v-col>
        <span class="text-h6">örtliche*r Ausbilder*in</span>
      </v-col>
      <v-col class="d-flex justify-end">
        <v-btn
          v-if="model.length < 2"
          variant="text"
          :disabled="disabled"
          @click="addSecondAusbilder"
        >
          zweiten Ausbilder hinzufügen
        </v-btn>
      </v-col>
    </v-row>
    <ausbilder-form
      v-model="firstAusbilder"
      is-required
      :show-ausbildung-inputs="showAusbildungInputs"
      :disabled="disabled"
    />
    <template v-if="model.length > 1">
      <v-row>
        <v-col>
          <span class="text-h6">zweite*r örtliche*r Ausbilder*in</span>
        </v-col>
        <v-col class="d-flex justify-end">
          <v-btn
            variant="text"
            :disabled="disabled"
            @click="removeSecondAusbilder"
          >
            zweiten Ausbilder entfernen
          </v-btn>
        </v-col>
      </v-row>
      <ausbilder-form
        v-model="secondAusbilder"
        is-required
        :show-ausbildung-inputs="showAusbildungInputs"
        :disabled="disabled"
      />
    </template>
  </v-sheet>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";

import AusbilderForm from "@/components/praktikumsplaetze/Meldung/AusbilderForm.vue";
import Ausbilder from "@/types/Ausbilder.ts";

const model = defineModel<Ausbilder[]>({ required: true });
const { showAusbildungInputs, disabled = false } = defineProps<{
  showAusbildungInputs: boolean;
  disabled?: boolean;
}>();

onMounted(() => {
  if (model.value.length === 0) {
    model.value.push(Ausbilder.empty());
  }
});

const firstAusbilder = computed({
  get: () => model.value[0] ?? Ausbilder.empty(),
  set: (newValue) => {
    model.value[0] = newValue;
  },
});
const secondAusbilder = computed({
  get: () => model.value[1] ?? Ausbilder.empty(),
  set: (newValue) => {
    model.value[1] = newValue;
  },
});

function addSecondAusbilder() {
  secondAusbilder.value = Ausbilder.empty();
}

function removeSecondAusbilder() {
  model.value.splice(1, 1);
}
</script>
