<template>
    <v-dialog
        id="error"
        :model-value="show"
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
                    Schließen
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { useUserErrorStore } from "@/stores/user-error";

const show = computed(() => errorStore.visible);
const message = computed(() => errorStore.message ?? "");
const title = computed(() => errorStore.title ?? "");

const errorStore = useUserErrorStore();

function close(): void {
    errorStore.hideUserError();
}
</script>

<style scoped>
.message {
    white-space: pre-line;
    overflow: auto;
}
</style>
