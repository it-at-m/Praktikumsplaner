<template>
    <v-card
        width="100%"
        elevation="4"
        outlined
    >
        <v-row
            no-gutters
            align="center"
        >
            <v-col cols="auto">
                <initials-avatar :nwk-name="getFullName(props.nwk)" />
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
            <v-col class="text-right mr-2">
                <v-btn
                    icon
                    @click.stop="show = !show"
                >
                    <v-icon>{{
                        show ? "mdi-chevron-up" : "mdi-chevron-down"
                    }}</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <v-expand-transition>
            <v-chip-group
                v-if="show"
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
        </v-expand-transition>
    </v-card>
</template>
<script setup lang="ts">
import InitialsAvatar from "@/components/InitialsAvatar.vue";
import Nwk from "@/types/Nwk";
import { ref, computed } from "vue";
import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";

const props = defineProps<{
    nwk: Nwk;
}>();

const germanDays = computed(() => {
    return new GermanWeekdayMapper().getGermanDays(props.nwk.vorlesungstage);
});

const show = ref<boolean>(false);

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
</script>
<style scoped>
</style>