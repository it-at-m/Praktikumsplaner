<template>
    <v-card
        width="100%"
        elevation="4"
        outlined
    >
        <v-expansion-panels>
            <v-expansion-panel :disabled="!hasDetails(props.nwk)">
                <v-expansion-panel-title>
                    <v-row
                        no-gutters
                        align="center"
                    >
                        <v-col cols="auto">
                            <initials-avatar
                                :nwk-name="getFullName(props.nwk)"
                                :background-color="getNwkColor(props.nwk)"
                            />
                        </v-col>
                        <v-col>
                            <div>
                                <v-card-title>
                                    {{ getFullName(props.nwk) }}
                                </v-card-title>
                                <v-card-subtitle>
                                    {{ getSubtitle(props.nwk) }}
                                </v-card-subtitle>
                            </div>
                        </v-col>
                    </v-row>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                    <v-chip-group
                        active-class="primary--text"
                        column
                    >
                        <v-chip
                            v-for="day in germanDays"
                            :key="day"
                            color="primary"
                            outlined
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
import InitialsAvatar from "@/components/common/InitialsAvatar.vue";
import Nwk, { hasDetails } from "@/types/Nwk";
import { computed } from "vue";
import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";
import { findStudiengangColorByValue } from "@/types/Studiengang";
import { findAusbildungsrichtungColorByValue } from "@/types/Ausbildungsrichtung";

const props = defineProps<{
    nwk: Nwk;
}>();

const germanDays = computed(() => {
    return new GermanWeekdayMapper().getGermanDays(props.nwk.vorlesungstage);
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
.v-expansion-panel--disabled {
    color: var(--v-text-base) !important;
}
</style>