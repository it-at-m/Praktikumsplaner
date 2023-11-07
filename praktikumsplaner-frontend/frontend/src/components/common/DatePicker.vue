<template>
    <v-menu max-width="100%">
        <template #activator="{ on }">
            <v-text-field
                ref="dateField"
                :prepend-icon="props.prependIcon"
                :label="props.label"
                :value="formatDate(props.value)"
                :rules="props.rules"
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
import { computed, ref } from "vue";

const props = defineProps<{
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
        return props.value;
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