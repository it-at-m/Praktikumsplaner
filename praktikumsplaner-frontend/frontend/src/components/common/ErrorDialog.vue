<template>
    <v-dialog
        :key="props.value"
        v-model="visible"
        persistent
        width="550"
    >
        <v-card>
            <v-card-title>
                {{ props.dialogtitle }}
            </v-card-title>
            <v-card-text>
                <v-icon :color="iconcolor">{{ props.icontext }}</v-icon>
                {{ props.dialogtext }}
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    text
                    @click="close"
                >
                    Schliessen
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
    icontext: string;
    iconcolor: string;
    dialogtitle: string;
    dialogtext: string;
    /**
     * Control-flag
     */
    value: boolean;
}>();
const emits = defineEmits<{
    (e: "close"): void;
    (e: "input", v: boolean): void;
}>();

const visible = computed({
    get: () => props.value,
    set: (v) => emits("input", v),
});

function close(): void {
    emits("close");
}
</script>

<style scoped>

</style>