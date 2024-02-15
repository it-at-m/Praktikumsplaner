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
                        <v-list v-model:selected="selectedPraktikumsstellen">
                            <v-list-item>
                              v-for="praktikumsstelle in props.praktikumsstellenMap.get(
                                abteilung
                              )"

                              :key="praktikumsstelle.id"
                              :ripple="false"
                              >
                              <PraktikumsstelleCard
                                  :key="praktikumsstelle.assignedNwk?.id"
                                  :value="praktikumsstelle"
                                  :praktikumsstelle="praktikumsstelle"
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
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstelleCard from "@/components/assign/PraktikumsstelleCardZuweisung.vue";
import { ref } from "vue";

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
    background-color: #f0f0f0;
    border: 2px solid #ccc;
    margin: 2px;
}
</style>