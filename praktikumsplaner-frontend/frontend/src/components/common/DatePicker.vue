<template>
    <v-menu max-width="100%">
        <template #activator="{ on }">
            <v-text-field
                :prepend-icon="props.prependIcon"
                :label="props.label"
                :value="formatDate(props.value)"
                v-on="on"
            ></v-text-field>
        </template>
        <v-date-picker
            v-model="date"
            :title="props.label"
        ></v-date-picker>
    </v-menu>
</template>


<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
    prependIcon: string;
    label: string;
    value?: string;
}>();

const emits = defineEmits<{
    (e: "input", v: string | undefined): void;
}>();

const date = computed({
    get() {
        return props.value;
    },
    set(date) {
        emits("input", date ? date : undefined);
    },
});

function formatDate(dateString: string | undefined): string {
    return dateString == undefined
        ? "-"
        : new Date(dateString).toLocaleDateString();
}
</script>