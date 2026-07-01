<template>
  <v-card
    width="100%"
    border
  >
    <v-expansion-panels>
      <v-expansion-panel>
        <v-expansion-panel-title :hide-actions="!hasDetails(nwk)">
          <v-row
            no-gutters
            align="center"
            class="full-width"
          >
            <v-col cols="auto">
              <initials-avatar
                :nwk-name="fullName"
                :background-color="nwkColor"
              />
            </v-col>
            <v-col
              cols="6"
              md="8"
              xl="10"
            >
              <v-card-title>
                {{ fullName }}
              </v-card-title>
              <v-card-subtitle>
                {{ subtitle }}
              </v-card-subtitle>
            </v-col>
          </v-row>
        </v-expansion-panel-title>
        <v-expansion-panel-text v-if="hasDetails(nwk)">
          <v-chip-group
            selected-class="primary--text"
            column
          >
            <v-chip
              v-for="day in germanDays"
              :key="day"
              color="primary"
              variant="outlined"
              class="ml-4 mr-0"
            >
              {{ day }}
            </v-chip>
          </v-chip-group>
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card>
</template>
<script setup lang="ts">
import { computed } from "vue";

import InitialsAvatar from "@/components/common/InitialsAvatar.vue";
import { findBildungsrichtungColorByValue } from "@/types/Bildungsrichtung";
import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";
import Nwk, { hasDetails } from "@/types/Nwk";

const properties = defineProps<{
  nwk: Nwk;
}>();

const germanDays = computed(() => {
  return new GermanWeekdayMapper().getGermanDays(properties.nwk.vorlesungstage);
});

const fullName = computed(
  () => properties.nwk.vorname + " " + properties.nwk.nachname
);
const subtitle = computed(() => {
  let subtitle = "Daten konnten nicht geladen werden.";
  if (properties.nwk.richtung) {
    subtitle = properties.nwk.richtung + " / " + properties.nwk.jahrgang;
  }
  return subtitle;
});
const nwkColor = computed(() => {
  let color = "white";
  if (properties.nwk.richtung) {
    color = findBildungsrichtungColorByValue(properties.nwk.richtung);
  }
  return color;
});
</script>

<style scoped>
.full-width {
  max-width: 100%;
}
</style>
