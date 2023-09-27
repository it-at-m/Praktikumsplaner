<template>
    <v-dialog
        :key="props.value"
        v-model="visible"
        persistent
        width="800"
    >
        <template #activator="{ on }">
            <template v-if="props.buttontext">
                <v-btn
                    color="primary"
                    v-on="on"
                >
                    {{ buttontext }}
                </v-btn>
            </template>
            <template v-else-if="props.icontext">
                <v-btn
                    text
                    color="primary"
                    v-on="on"
                >
                    <v-icon large>
                        {{ props.icontext }}
                    </v-icon>
                </v-btn>
            </template>
        </template>
        <v-card>
            <v-card-title>
                {{ props.dialogtitle }}
            </v-card-title>
            <v-card-text>
                {{ props.dialogtext }}
            </v-card-text>
            <v-card-actions>
                <v-spacer />
                <v-btn
                    id="yesnodialog-btn-no"
                    text
                    @click="no"
                >
                    Nein
                </v-btn>
                <v-btn
                    id="yesnodialog-btn-yes"
                    color="primary"
                    @click="yes"
                >
                    Ja
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { computed } from "vue";
/**
 * Der YesNo-Dialog ist ein generischer Dialog zur binären Abfrage beim Nutzer.
 * So kann z.B. die Bestätigung für das Löschen einer Entität damit realisiert werden.
 *
 * Da das Bestätigen einer Aktion in der Regel mit einem Button zusammenhängt, bietet der
 * YesNoDialog diesen gleich mit an. Über `buttontext` und `icontext` kann dieser konfiguriert werden.
 *
 * Wenn sowohl kein `buttontext` als auch `icontext` nicht gesetzt sind, kann der YesNoDialog auch
 * als reiner Dialog verwendet werden. Hierzu wird das Value vom Dialog durchgereicht.
 *
 * Die Bestätigung des Dialogs wird über ein `yes` Event signalisiert. Analog erfolgt die
 * Signalisierung der Abweisung durch ein `no` Event.
 *
 * Beispiel:
 * <yes-no-dialog
 *    v-model="deleteDialog"
 *    buttontext="Löschen"
 *    dialogtitle="Löschen?"
 *    dialogtext="Wollen Sie die Entität wirklich löschen?"
 *    @no="deleteDialog = false"
 *    @yes="deleteSome"></yes-no-dialog>
 */

const props = defineProps<{
    buttontext?: string;
    icontext?: string;
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
