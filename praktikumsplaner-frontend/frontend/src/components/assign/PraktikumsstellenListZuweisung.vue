<template>
    <v-container v-if="props.praktikumsstellenMap && props.praktikumsstellenMap.size > 0" class="mt-2">
        <v-expansion-panels multiple>
            <v-expansion-panel
                v-for="abteilung in props.praktikumsstellenMap.keys()"
                :key="abteilung"
                class="custom-panel"
            >
                <v-expansion-panel-title>{{
                        abteilung
                    }}
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                    <v-list>
                        <v-list-item
                            v-for="praktikumsstelle in props.praktikumsstellenMap.get(
                                    abteilung
                                )"
                            :key="praktikumsstelle.id"
                            :ripple="false"
                        >
                            <praktikumsstelle-card
                                :key="praktikumsstelle.id"
                                :model-value="praktikumsstelle"
                            ></praktikumsstelle-card>
                        </v-list-item>
                    </v-list>
                </v-expansion-panel-text>
            </v-expansion-panel>
        </v-expansion-panels>
    </v-container>
    <v-container v-else class="d-flex justify-center align-center">
        <v-row justify="center">
            <v-col cols="auto" class="d-flex align-center justify-center">
                <v-icon color="blue" size="large" class="mr-3">mdi-information-outline</v-icon>
                <span>Für den vergangenen Meldezeitraum sind keine Praktikumsstellen vorhanden.</span>
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import PraktikumsstelleCard from "@/components/assign/PraktikumsstelleCardZuweisung.vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const props = defineProps<{
    praktikumsstellenMap: Map<string, Praktikumsstelle[]>;
}>();
</script>
<style scoped>
.custom-panel {
    margin: 2px;
}
</style>
