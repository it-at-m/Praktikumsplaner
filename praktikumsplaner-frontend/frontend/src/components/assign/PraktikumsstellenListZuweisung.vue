<template>
    <div>
        <v-container>
            <v-expansion-panels multiple>
                <v-expansion-panel
                    v-for="abteilung in props.praktikumsstellenMap.keys()"
                    :key="abteilung"
                    class="custom-panel"
                >
                    <v-expansion-panel-title>{{
                        abteilung
                    }}</v-expansion-panel-title>
                    <v-expansion-panel-text>
                      <v-list>
                        <v-list-item
                            v-for="praktikumsstelle in props.praktikumsstellenMap.get(abteilung)"
                            :key="praktikumsstelle.id"
                            :ripple="false"
                        >
                          <PraktikumsstelleCard
                              :model-value="praktikumsstelle"
                              :key="praktikumsstelle.id"
                          ></PraktikumsstelleCard>
                        </v-list-item>
                      </v-list>

                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </v-container>
    </div>
</template>
<script setup lang="ts">
import { ref } from "vue";

import PraktikumsstelleCard from "@/components/assign/PraktikumsstelleCardZuweisung.vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const props = defineProps<{
    praktikumsstellenMap: Map<string, Praktikumsstelle[]>;
}>();

const selectedPraktikumsstellen = ref<Praktikumsstelle[]>([]);
</script>
<style scoped>
.spacer {
    padding-bottom: 10px;
}
.custom-panel {
    margin: 2px;
}
</style>
