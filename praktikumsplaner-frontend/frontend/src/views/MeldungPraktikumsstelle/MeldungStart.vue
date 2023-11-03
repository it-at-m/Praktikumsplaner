<template>
    <v-container class="spacing-left">
        <h3>Praktikumstellen Meldung</h3>
        <div class="flex-container">
            <p class="p-spacing">
                Wollen Sie eine Stelle für einen Auszubildenden melden
            </p>
            <v-checkbox
                :input-value="isCheckedAusbildung"
                class="checkbox-spacing"
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
        <v-row class="bottom-buttons">
            <v-col>
                <v-btn
                    color="primary"
                    outlined
                    :to="{ path: '/' }"
                >
                    ZURÜCK
                </v-btn>
            </v-col>
            <v-col cols="8" />
            <v-col>
                <v-btn
                    v-if="isCheckedAusbildung || isCheckedStudium"
                    color="primary"
                    @click="redirect"
                >
                    WEITER
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import router from "@/router";
import { EventBus } from "@/EventBus";

const isCheckedAusbildung = ref<boolean>(false);
const isCheckedStudium = ref<boolean>(false);

onMounted(() => {
    EventBus.$emit("changeAppHeader", "Praktikumsstellen Meldung");
});

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
.spacing-left {
    margin-left: 30px;
}
.checkbox-spacing {
    margin-right: 5px;
}
.flex-container {
    display: flex;
    align-items: center;
    margin-top: 30px;
}
.p-spacing {
    margin-right: 20px;
    margin-top: 10px;
}
.bottom-buttons {
    margin-top: 40%;
}
</style>
