<template>
    <v-container>
        <h3 style="margin-left: 30px">Praktikumstellen Meldung</h3>
        <div
            style="
                display: flex;
                align-items: center;
                margin-left: 30px;
                margin-top: 30px;
            "
        >
            <p style="margin-right: 20px; margin-top: 10px">
                Wollen Sie eine Stelle für einen Auszubildenden melden
            </p>
            <v-checkbox
                :input-value="isCheckedAusbildung"
                style="margin-right: 5px"
                @change="isCheckedAusbildung = !isCheckedAusbildung"
            >
                <template #label>
                    <span class="v-label-black">ja</span>
                </template>
            </v-checkbox>
            <v-checkbox
                :input-value="isCheckedStudium"
                @change="isCheckedStudium = !isCheckedStudium"
            >
                <template #label>
                    <span class="v-label-black">nein</span>
                </template>
            </v-checkbox>
        </div>
        <v-row
            justify="space-between"
            align="end"
        >
            <v-btn
                color="primary"
                outlined
            >
                Zurück
            </v-btn>
            <v-btn
                v-if="isCheckedAusbildung || isCheckedStudium"
                color="primary"
                @click="redirect"
            >
                Weiter
            </v-btn>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import { ref } from "vue";
import router from "@/router";

const isCheckedAusbildung = ref<boolean>(false);
const isCheckedStudium = ref<boolean>(false);

function redirect(): void {
    if (isCheckedAusbildung.value) {
        router.push("/meldungAusbildung");
    } else if (isCheckedStudium.value) {
        router.push("/meldungStudium");
    }
}
</script>

<style>
.v-label-black {
    color: black;
}
</style>
