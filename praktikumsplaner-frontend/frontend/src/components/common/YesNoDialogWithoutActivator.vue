<template>
    <v-dialog
        v-model="visible"
        persistent
        width="800"
    >
        <v-card>
            <v-card-title>
                {{ properties.dialogtitle }}
            </v-card-title>
            <v-card-text
                v-for="(text, index) in properties.dialogtext.split('\n')"
                :key="index"
            >
                {{ text }}
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    id="yesnodialog-btn-no"
                    variant="outlined"
                    @click="no"
                    >Nein
                </v-btn>
                <v-btn
                    id="yesnodialog-btn-yes"
                    variant="flat"
                    color="primary"
                    @click="yes"
                    >Ja
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>
<script setup lang="ts">
import { computed } from "vue";

const properties = defineProps<{
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
    get: () => properties.value,
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
