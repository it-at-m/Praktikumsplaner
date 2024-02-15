<template>
    <v-dialog
        id="error"
        v-model="show"
        persistent
        width="800"
    >
        <v-card>
            <v-card-title>
                {{ title }}
            </v-card-title>
            <v-card-text>
                <p class="message">
                    {{ message }}
                </p>
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    color="primary"
                    @click="close"
                >
                    Schliessen
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
import { useErrorStore } from "@/stores/error";

const show = ref(false);
const message = ref("");
const title = ref("");

const errorStore = useErrorStore();

watch(
    () => errorStore.title,
    () => (title.value = errorStore.title ?? "")
);

watch(
    () => errorStore.message,
    () => (message.value = errorStore.message ?? "")
);

watch(
    () => errorStore.show,
    () => {
        if (errorStore.show) {
            show.value = false;
            setTimeout(() => {
                show.value = true;
                errorStore.show = false;
            }, 100);
        }
    }
);

function close(): void {
    show.value = false;
}
</script>

<style scoped>
.message {
    white-space: pre-line;
    overflow: auto;
}
</style>