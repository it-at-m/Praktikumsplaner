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
                                :nwk-name="getFullName(properties.nwk)"
                                :background-color="getNwkColor(properties.nwk)"
                            />
                        </v-col>
                        <v-col
                            cols="6"
                            md="8"
                            xl="10"
                        >
                            <v-card-title>
                                {{ getFullName(properties.nwk) }}
                            </v-card-title>
                            <v-card-subtitle>
                                {{ getSubtitle(properties.nwk) }}
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
import { findAusbildungsrichtungColorByValue } from "@/types/Ausbildungsrichtung";
import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";
import Nwk, { hasDetails } from "@/types/Nwk";
import { findStudiengangColorByValue } from "@/types/Studiengang";

const properties = defineProps<{
    nwk: Nwk;
}>();

const germanDays = computed(() => {
    return new GermanWeekdayMapper().getGermanDays(
        properties.nwk.vorlesungstage
    );
});

function getFullName(nwk: Nwk): string {
    return nwk.vorname + " " + nwk.nachname;
}

function getSubtitle(nwk: Nwk): string {
    let subtitle = "Daten konnten nicht geladen werden.";
    if (nwk.studiengang) {
        subtitle = nwk.studiengang + " / " + nwk.jahrgang;
    } else if (nwk.ausbildungsrichtung) {
        subtitle = nwk.ausbildungsrichtung + " / " + nwk.jahrgang;
    }
    return subtitle;
}

function getNwkColor(nwk: Nwk): string {
    let color = "white";
    if (nwk.studiengang && nwk.ausbildungsrichtung == undefined) {
        color = findStudiengangColorByValue(nwk.studiengang);
    } else if (nwk.ausbildungsrichtung && nwk.studiengang == undefined) {
        color = findAusbildungsrichtungColorByValue(nwk.ausbildungsrichtung);
    }
    return color;
}
</script>
<style scoped>
.full-width{
    max-width: 100%;
}
</style>
