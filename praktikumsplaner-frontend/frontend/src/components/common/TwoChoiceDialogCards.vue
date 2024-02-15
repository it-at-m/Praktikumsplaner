<template>
    <v-dialog
        :key="props.value"
        v-model="visible"
        persistent
        width="800"
    >
        <template #activator="{ on }">
            <template v-if="props.icontext">
                <v-btn
                    color="primary"
                    v-on="on"
                >
                    <v-icon> {{ icontext }} </v-icon>
                    {{ buttontext }}
                </v-btn>
            </template>
            <template v-else>
                <v-btn
                    color="primary"
                    v-on="on"
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
                            class="mx-auto"
                            max-width="300"
                            outlined
                            rounded
                            @click="choiceOne"
                        >
                            <v-card-title>
                                {{ props.choiceOne }}
                            </v-card-title>
                            <v-card-subtitle>
                                {{ props.choiceOneSubtitle }}</v-card-subtitle
                            >
                        </v-card>
                    </v-col>
                    <v-col cols="6">
                        <v-card
                            class="mx-auto"
                            max-width="300"
                            outlined
                            rounded
                            @click="choiceTwo"
                        >
                            <v-card-title>
                                {{ props.choiceTwo }}
                            </v-card-title>
                            <v-card-subtitle>
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
                    text="Schliessen"
                    color="primary"
                    @click="close"
                >
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
    choiceOne: string;
    choiceOneSubtitle?: string;
    choiceTwo: string;
    choiceTwoSubtitle?: string;
    /**
     * Control-flag
     */
    value: boolean;
}>();

const emits = defineEmits<{
    (e: "choiceOne"): void;
    (e: "choiceTwo"): void;
    (e: "close"): void;
    (e: "input", v: boolean): void;
}>();

const visible = computed({
    get: () => props.value,
    set: (v) => emits("input", v),
});

function choiceOne(): void {
    emits("choiceOne");
}
function choiceTwo(): void {
    emits("choiceTwo");
}
function close(): void {
    emits("close");
}
</script>
