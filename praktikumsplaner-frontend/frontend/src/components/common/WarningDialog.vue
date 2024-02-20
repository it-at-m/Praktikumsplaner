<template>
    <v-dialog
        v-model="computedVisible"
        max-width="500px"
    >
        <v-card>
            <v-card-title> {{ currentWarning?.title }} </v-card-title>
            <v-card-text>
                {{ currentWarning?.message }}
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    color="primary"
                    variant="elevated"
                    @click="accept"
                    >Akzeptieren</v-btn
                >
                <v-btn
                    color="error"
                    variant="elevated"
                    @click="reject"
                    >Ablehnen</v-btn
                >
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

import Warning from "@/types/Warning";

const emits = defineEmits<{
    (e: "accepted"): void;
    (e: "rejected"): void;
}>();

const props = defineProps<{
    warnings: Warning[];
    visible: boolean;
}>();

const currentIndex = ref(0);

const computedVisible = computed(() => {
    if (props.visible && props.warnings.length <= 0) {
        emits("accepted");
        return false;
    }
    return props.visible;
});

const currentWarning = computed(() => {
    if (currentIndex.value < props.warnings.length) {
        return props.warnings[currentIndex.value];
    }
    return null;
});

function accept() {
    currentIndex.value++;
    if (currentIndex.value >= props.warnings.length) {
        currentIndex.value = 0;
        emits("accepted");
    }
}

function reject() {
    currentIndex.value = 0;
    emits("rejected");
}
</script>
