<template>
    <v-menu max-width="100%">
        <template #activator="{ props }">
            <v-text-field
                ref="dateField"
                :prepend-icon="properties.prependIcon"
                :label="properties.label"
                :model-value="formatDate(properties.value)"
                :rules="properties.rules"
                v-bind="props"
            ></v-text-field>
        </template>
        <v-date-picker
            v-model="date"
            :title="properties.label"
        ></v-date-picker>
    </v-menu>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

const properties = defineProps<{
    prependIcon: string;
    label: string;
    rules: ((value: string) => string | boolean)[];
    value?: string;
}>();

const emits = defineEmits<{
    (e: "input", v: string | undefined): void;
}>();

const dateField = ref();

const date = computed({
    get() {
        return properties.value;
    },
    set(date) {
        emits("input", date ? date : undefined);
    },
});

function formatDate(dateString: string | undefined): string {
    if (dateString == undefined) {
        return "-";
    }
    const date = new Date(dateString);
    return (
        date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear()
    );
}

function validate() {
    dateField.value?.validate();
}

defineExpose({ validate });
</script>
