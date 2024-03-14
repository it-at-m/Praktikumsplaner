<template>
    <div>
        <v-container>
            <v-expansion-panels multiple>
                <v-expansion-panel
                    v-for="abteilung in properties.praktikumsstellenMap.keys()"
                    :key="abteilung"
                    class="custom-panel"
                >
                    <v-expansion-panel-title>{{
                        abteilung
                    }}</v-expansion-panel-title>
                    <v-expansion-panel-text>
                        <v-list>
                            <v-list-item
                                v-for="praktikumsstelle in properties.praktikumsstellenMap.get(
                                    abteilung
                                )"
                                :key="praktikumsstelle.id"
                                :ripple="false"
                            >
                                <praktikumsstelle-card
                                    :key="praktikumsstelle.assignedNwk?.id"
                                    :praktikumsstelle="praktikumsstelle"
                                ></praktikumsstelle-card>
                            </v-list-item>
                        </v-list>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </v-container>
    </div>
</template>
<script setup lang="ts">
import PraktikumsstelleCard from "@/components/praktikumsplaetze/Praktikumsplaetze/PraktikumsstelleCard.vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const properties = defineProps<{
    praktikumsstellenMap: Map<string, Praktikumsstelle[]>;
}>();
</script>
<style scoped>
.custom-panel {
    margin: 2px;
}
</style>
