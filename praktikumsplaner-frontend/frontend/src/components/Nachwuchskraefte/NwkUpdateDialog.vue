<template>
    <div>
        <v-dialog
            v-model="visible"
            persistent
            max-width="550"
        >
            <template #activator="{ on }">
                <v-btn
                    color="primary"
                    v-on="on"
                >
                    <v-icon>mdi-pencil-circle-outline</v-icon>
                    Bearbeiten
                </v-btn>
            </template>
            <v-form ref="form">
                <v-card>
                    <v-card-title class="text-h5 font-weight-bold"
                        >NWK bearbeiten</v-card-title
                    >
                    <v-list>
                        <v-list-item>
                            <v-text-field
                                v-model="nwkToUpdate.vorname"
                                label="Vorname"
                                outlined
                            ></v-text-field>
                        </v-list-item>
                        <v-list-item>
                            <v-text-field
                                v-model="nwkToUpdate.nachname"
                                label="Nachname"
                                outlined
                            ></v-text-field>
                        </v-list-item>
                        <v-list-item>
                            <v-text-field
                                v-model="nwkToUpdate.jahrgang"
                                label="Jahrgang"
                                outlined
                            ></v-text-field>
                        </v-list-item>
                        <v-list-item>
                            <v-select
                                v-model="nwkToUpdate.studiengang"
                                label="Studienrichtung"
                                :items="Studiengang"
                                item-value="value"
                                item-text="name"
                                outlined
                                clearable
                                :rules="rules"
                                @click:clear="
                                    nwkToUpdate.studiengang = undefined
                                "
                            ></v-select>
                        </v-list-item>
                        <v-list-item>
                            <v-select
                                v-model="nwkToUpdate.ausbildungsrichtung"
                                label="Ausbildungsrichtung"
                                :items="Ausbildungsrichtung"
                                item-value="value"
                                item-text="name"
                                outlined
                                clearable
                                :rules="rules"
                                @click:clear="
                                    nwkToUpdate.ausbildungsrichtung = undefined
                                "
                            ></v-select>
                        </v-list-item>
                    </v-list>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                            color="primary"
                            outlined
                            @click="cancel()"
                        >
                            Abbrechen
                        </v-btn>
                        <v-btn
                            color="primary"
                            @click="updateNwk()"
                        >
                            Akzeptieren
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-form>
        </v-dialog>
    </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import NwkService from "@/api/NwkService";
import Nwk from "@/types/Nwk";
import { Studiengang } from "@/types/Studiengang";
import { Ausbildungsrichtung } from "@/types/Ausbildungsrichtung";

const visible = ref<boolean>(false);
const form = ref<HTMLFormElement>();

const props = defineProps<{
    nwk: Nwk;
}>();

const nwkToUpdate = ref<Nwk>(props.nwk);

const rules = computed(() => {
    return [isStudiumOrAusbildung.value];
});

const isStudiumOrAusbildung = computed(() => {
    return (
        (nwkToUpdate.value.studiengang != undefined &&
            nwkToUpdate.value.ausbildungsrichtung == undefined) ||
        (nwkToUpdate.value.studiengang == undefined &&
            nwkToUpdate.value.ausbildungsrichtung != undefined) ||
        "Es muss eine Studienrichtung oder eine Ausbildungsrichtung angegeben werden."
    );
});

const emits = defineEmits<{
    (e: "updated"): void;
}>();

function cancel() {
    visible.value = false;
}

function updateNwk() {
    if (!form.value?.validate()) {
        return;
    }
    NwkService.updateNwk(nwkToUpdate.value).then(() => {
        emits("updated");
        cancel();
    });
}
</script>

<style scoped>

</style>