<template>
    <v-container>
        <v-row>
            <v-col>
                <v-list-item>
                    <v-text-field
                        v-model="nwk.vorname"
                        label="Vorname"
                        :rules="nameRule"
                        outlined
                    ></v-text-field>
                </v-list-item>
            </v-col>
            <v-col>
                <v-list-item>
                    <v-text-field
                        v-model="nwk.nachname"
                        label="Nachname"
                        :rules="nameRule"
                        outlined
                    ></v-text-field>
                </v-list-item>
            </v-col>
        </v-row>
    </v-container>
</template>
<script setup lang="ts">
import { useRules } from "@/composables/rules";
import NwkCreate from "@/types/NwkCreate";
import { computed } from "vue";

const validationRules = useRules();

const nameRule = [
    validationRules.minLengthRule(
        2,
        "Der Name muss mindestens 2 Zeichen lang sein."
    ),
    validationRules.maxLengthRule(
        255,
        "Der Name darf maximal 255 Zeichen lang sein."
    ),
    validationRules.notEmptyRule("Der Name darf nicht leer sein."),
];

const props = defineProps<{
    value: NwkCreate;
}>();

const emits = defineEmits<{
    (e: "updated", nwkToCreate: NwkCreate): void;
}>();

const nwk = computed({
    get: () => props.value,
    set: (newValue) => emits("updated", newValue),
});
</script>