<template>
    <v-dialog
        :key="props.value"
        v-model="visible"
        persistent
        width="800"
    >
        <v-card>
            <v-card-title>
                {{ props.dialogtitle }}
            </v-card-title>
            <v-card-text
                v-for="(text, index) in props.dialogtext.split('\n')"
                :key="index"
            >
                {{ text }}
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    id="yesnodialog-btn-no"
                    text="Nein"
                    @click="no"
                >
                </v-btn>
                <v-btn
                    text="Ja"
                    id="yesnodialog-btn-yes"
                    color="primary"
                    @click="yes"
                >
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>
<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
    dialogtitle: string;
    dialogtext: string;
    /**
     * Steuerflag für den Dialog
     */
    value: boolean;
}>();

const emits = defineEmits<{
    (e: "no"): void;
    (e: "yes"): void;
    (e: "input", v: boolean): void;
}>();

const visible = computed({
    get: () => props.value,
    set: (v) => emits("input", v),
});

function no(): void {
    emits("no");
}
function yes(): void {
    emits("yes");
}
</script>

<style scoped></style>
