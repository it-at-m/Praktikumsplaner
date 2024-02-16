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
                    Schließen
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, computed } from "vue";
import { useUserErrorStore } from "@/stores/user-error";

const show = ref(false);
const message = computed(() => errorStore.message ?? "");
const title = computed(() => errorStore.title ?? "");

const errorStore = useUserErrorStore();

watch(
    () => errorStore.visible,
    () => {
        if (errorStore.visible) {
            show.value = true;
            errorStore.visible = false;
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