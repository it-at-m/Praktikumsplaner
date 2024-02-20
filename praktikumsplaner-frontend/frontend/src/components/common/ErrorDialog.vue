<template>
    <v-dialog
        v-model="visible"
        persistent
        width="550"
    >
        <v-card>
            <v-card-title>
                {{ props.dialogtitle }}
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-col cols="1">
                        <v-icon
                            size="x-large"
                            :color="iconcolor"
                            >{{ props.icontext }}</v-icon
                        >
                    </v-col>
                    <v-col cols="11">
                        {{ props.dialogtext }}
                    </v-col>
                </v-row>
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    variant="text"
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
    modelValue: boolean;
}>();
const emits = defineEmits<{
    (e: "close"): void;
    (e: "update:modelValue", v: boolean): void;
}>();

const visible = computed({
    get: () => props.modelValue,
    set: (v) => emits("update:modelValue", v),
});

function close(): void {
    emits("close");
}
</script>

<style scoped></style>
