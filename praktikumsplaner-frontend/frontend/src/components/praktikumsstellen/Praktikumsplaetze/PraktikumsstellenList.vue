<template>
    <div>
        <v-container>
            <v-expansion-panels multiple>
                <v-expansion-panel
                    v-for="abteilung in props.praktikumsstellenMap.keys()"
                    :key="abteilung"
                    class="custom-panel"
                >
                    <v-expansion-panel-header>{{
                        abteilung
                    }}</v-expansion-panel-header>
                    <v-expansion-panel-content>
                        <v-list>
                            <v-list-item-group>
                                <v-list-item
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
                                        :assignment="props.assignment"
                                        @input="change"
                                    ></PraktikumsstelleCard>
                                </v-list-item>
                            </v-list-item-group>
                        </v-list>
                    </v-expansion-panel-content>
                </v-expansion-panel>
            </v-expansion-panels>
        </v-container>
    </div>
</template>
<script setup lang="ts">
import Praktikumsstelle from "@/types/Praktikumsstelle";
import PraktikumsstelleCard from "@/components/praktikumsstellen/Praktikumsplaetze/PraktikumsstelleCard.vue";

const props = defineProps<{
    praktikumsstellenMap: Map<string, Praktikumsstelle[]>;
    assignment: boolean;
}>();

function change(p: Praktikumsstelle) {
    //iterating over map and changing value
    for (const [, value] of props.praktikumsstellenMap) {
        for (let i = 0; i < value.length; i++) {
            if (value[i].id === p.id) {
                value[i] = p;
            }
        }
    }
}
</script>
<style scoped lang="scss">
.spacer {
    padding-bottom: 10px;
}
.custom-panel {
    background-color: #f0f0f0;
    border: 2px solid #ccc;
    margin: 2px;
}
</style>