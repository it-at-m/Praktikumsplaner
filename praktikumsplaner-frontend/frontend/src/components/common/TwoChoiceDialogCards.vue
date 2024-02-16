<template>
    <v-dialog
        v-model="visible"
        persistent
        width="800"
    >
        <template #activator="{ props }">
            <template v-if="props.icontext">
                <v-btn
                    color="primary"
                    v-on="props"
                >
                    <v-icon> {{ icontext }} </v-icon>
                    {{ buttontext }}
                </v-btn>
            </template>
            <template v-else>
                <v-btn
                    color="primary"
                    v-bind="props"
                >
                    {{ buttontext }}
                </v-btn>
            </template>
        </template>
        <v-card>
            <v-card-title>
                {{ props.dialogtitle }}
            </v-card-title>
            <v-spacer />
            <v-card-subtitle>
                {{ props.dialogsubtitle }}
            </v-card-subtitle>
            <v-card-text>
                <v-row>
                    <v-col cols="6">
                        <v-card
                            class="mx-auto my-2"
                            max-width="300"
                            variant="outlined"
                            rounded
                            @click="choiceOne"
                        >
                            <v-card-title class="mt-1">
                                {{ props.choiceOneTitle }}
                            </v-card-title>
                            <v-card-subtitle class="mb-4">
                                {{ props.choiceOneSubtitle }}</v-card-subtitle
                            >
                        </v-card>
                    </v-col>
                    <v-col cols="6">
                        <v-card
                            class="mx-auto my-2"
                            max-width="300"
                            variant="outlined"
                            rounded
                            @click="choiceTwo"
                        >
                            <v-card-title class="mt-1">
                                {{ props.choiceTwoTitle }}
                            </v-card-title>
                            <v-card-subtitle class="mb-4">
                                {{ props.choiceTwoSubtitle }}
                            </v-card-subtitle>
                        </v-card>
                    </v-col>
                </v-row>
            </v-card-text>
            <v-divider />
            <v-card-actions>
                <v-spacer />
                <v-btn
                    variant="text"
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

const props = defineProps<{
    buttontext: string;
    icontext?: string;
    dialogtitle: string;
    dialogsubtitle: string;
    choiceOneTitle: string;
    choiceOneSubtitle?: string;
    choiceTwoTitle: string;
    choiceTwoSubtitle?: string;
    /**
     * Control-flag
     */
    modelValue: boolean;
}>();

const emits = defineEmits<{
    (e: "choiceOne"): void;
    (e: "choiceTwo"): void;
    (e: "update:modelValue", v: boolean): void;
}>();

const visible = computed({
    get: () => props.modelValue,
    set: (v) => emits("update:modelValue", v),
});

function choiceOne(): void {
    emits("choiceOne");
}
function choiceTwo(): void {
    emits("choiceTwo");
}
function close(): void {
    visible.value = false;
}
</script>
