<template>
    <v-snackbar
        id="snackbar"
        v-model="show"
        :color="backgroundColor"
        :timeout="timeout"
    >
        <v-row class="snackbarContent">
            <v-col class="message"> {{ message }}</v-col>
            <v-col>
                <v-btn
                    v-if="backgroundColor !== 'success'"
                    :color="btnTextColor"
                    text
                    @click="show = false"
                >
                    Schließen
                </v-btn>
            </v-col>
        </v-row>
    </v-snackbar>
</template>

<script setup lang="ts">
import { ref, watch, computed } from "vue";
import { useSnackbarStore } from "@/stores/snackbar";

const snackbarStore = useSnackbarStore();

const defaultTimeout = 5000;

const show = ref(false);
const timeout = ref(defaultTimeout);
const message = computed(() => snackbarStore.message ?? "");
const backgroundColor = ref("info");
const btnTextColor = ref("infoBtnText");

watch(
    () => snackbarStore.level,
    () => {
        backgroundColor.value = snackbarStore.level;
        if (backgroundColor.value === "error") {
            timeout.value = 0;
            btnTextColor.value = "errorBtnText";
        } else if (backgroundColor.value === "warning") {
            timeout.value = defaultTimeout;
            btnTextColor.value = "warningBtnText";
        } else if (backgroundColor.value === "info") {
            timeout.value = defaultTimeout;
            btnTextColor.value = "infoBtnText";
        } else {
            timeout.value = defaultTimeout;
        }
    }
);

watch(
    () => snackbarStore.show,
    () => {
        if (snackbarStore.show) {
            show.value = false;
            setTimeout(() => {
                show.value = true;
                snackbarStore.show = false;
            }, 100);
        }
    }
);
</script>
<style>
.snackbarContent {
    white-space: nowrap;
    overflow: auto;
}
.message {
    margin: 8px;
}
</style>