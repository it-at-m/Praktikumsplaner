<template>
    <v-dialog
        :model-value="properties.showUndeliveredMailsDialog"
        persistent
        max-width="850px"
    >
        <v-card>
            <v-card-title>
                Die folgenden Praktikumsstellen konnten nicht benachrichtigt
                werden:
            </v-card-title>
            <v-card-text>
                <div
                    v-for="stelle in properties.faultyStellen"
                    :key="stelle.id"
                >
                    <h4>Stelle bei {{ stelle.dienststelle }}</h4>
                    <p>
                        Ausbilder*in: {{ stelle.email }} (Zugewiesene NWK:
                        {{ stelle.assignedNwk }})
                    </p>
                </div>
                <v-divider class="bg-grey" />
            </v-card-text>
            <v-card-actions>
                <v-row class="mb-2">
                    <v-col class="col-auto mr-auto">
                        <v-btn
                            color="primary"
                            @click="closeDialog"
                        >
                            Schließen
                        </v-btn>
                    </v-col>
                </v-row>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>
<script setup lang="ts">
import Praktikumsstelle from "@/types/Praktikumsstelle";

const properties = defineProps<{
    faultyStellen: Praktikumsstelle[];
    showUndeliveredMailsDialog: boolean;
}>();

const emit = defineEmits<{
    (e: "update:showUndeliveredMailsDialog", b: boolean): void;
}>();

function closeDialog(): void {
    emit("update:showUndeliveredMailsDialog", false);
}
</script>
