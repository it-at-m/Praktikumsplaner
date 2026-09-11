<template>
  <div>
    <v-row>
      <v-col>
        <ausbilder-input
          v-model="model.name"
          :is-required="isRequired"
          required-symbol="*"
          :disabled="disabled"
        />
      </v-col>
      <v-col cols="1" />
      <v-col>
        <ausbilder-email-input
          v-model="model.email"
          :is-required="isRequired"
          required-symbol="*"
          :disabled="disabled"
        ></ausbilder-email-input>
      </v-col>
      <v-col cols="1" />
    </v-row>
    <v-row>
      <v-col>
        <ausbilder-erw-fuehrungszeugnis-checkbox
          v-model="model.erwFuehrungszeugnisVorhanden"
          :disabled="disabled"
        ></ausbilder-erw-fuehrungszeugnis-checkbox>
      </v-col>
      <v-col cols="1" />
      <v-col>
        <minderjaehrig-moeglich-radio-group
          v-if="showAusbildungInputs"
          v-model="model.minderjaehrigMoeglich"
          :is-required="isRequired"
          required-symbol="*"
          :disabled="disabled"
        ></minderjaehrig-moeglich-radio-group>
      </v-col>
      <v-col cols="1">
        <minderjaehrig-moeglich-tooltip></minderjaehrig-moeglich-tooltip>
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import type Ausbilder from "@/types/Ausbilder.ts";

import AusbilderEmailInput from "@/components/praktikumsplaetze/Meldung/AusbilderEmailInput.vue";
import AusbilderErwFuehrungszeugnisCheckbox from "@/components/praktikumsplaetze/Meldung/AusbilderErwFuehrungszeugnisCheckbox.vue";
import AusbilderInput from "@/components/praktikumsplaetze/Meldung/AusbilderInput.vue";
import MinderjaehrigMoeglichRadioGroup from "@/components/praktikumsplaetze/Meldung/MinderjaehrigMoeglichRadioGroup.vue";
import MinderjaehrigMoeglichTooltip from "@/components/praktikumsplaetze/Meldung/MinderjaehrigMoeglichTooltip.vue";

const model = defineModel<Ausbilder>({ required: true });
const {
  isRequired = false,
  showAusbildungInputs,
  disabled = false,
} = defineProps<{
  isRequired?: boolean;
  showAusbildungInputs: boolean;
  disabled?: boolean;
}>();
</script>
