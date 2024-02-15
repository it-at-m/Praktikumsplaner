<template>
    <v-dialog
        :value="visible"
        max-width="500px"
    >
        <v-card>
            <v-card-title> {{ currentWarning?.title }} </v-card-title>
            <v-card-text>
                {{ currentWarning?.message }}
            </v-card-text>
            <v-card-actions>
                <v-btn
                    color="primary"
                    @click="accept"
                    >Akzeptieren</v-btn
                >
                <v-btn
                    color="error"
                    @click="reject"
                    >Ablehnen</v-btn
                >
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
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

const currentWarning = computed(() => {
    if (currentIndex.value < props.warnings.length) {
        return props.warnings[currentIndex.value];
    }
    return null;
});

const accept = () => {
    currentIndex.value++;
    if (currentIndex.value >= props.warnings.length) {
        currentIndex.value = 0;
        emits("accepted");
    }
};

const reject = () => {
    currentIndex.value = 0;
    emits("rejected");
};

onMounted(() => {
    if (props.visible && props.warnings.length <= 0) {
        currentIndex.value = 0;
        emits("accepted");
    }
});
</script>
