<template>
    <v-container>
        <v-card
            class="full-width-card card"
            elevation="6"
            :ripple="false"
            @click="show = !show"
        >
            <v-card-title
                >Stelle bei
                {{ properties.modelValue.dienststelle }}</v-card-title
            >
            <v-card-subtitle
                v-if="properties.modelValue.namentlicheAnforderung"
            >
                Namentliche Anforderung:
                {{ properties.modelValue.namentlicheAnforderung }}
            </v-card-subtitle>
            <v-icon
                v-if="properties.modelValue.planstelleVorhanden"
                size="x-large"
                class="icon-top-right-position"
                icon="mdi-account-star"
            ></v-icon>
            <v-card-text class="pt-0 mt-0 mb-0 pb-0">
                <p style="white-space: pre-line">
                    {{ getCardText(properties.modelValue) }}
                </p></v-card-text
            >
            <v-col cols="12"></v-col>
            <v-btn
                :icon="show ? 'mdi-chevron-up' : 'mdi-chevron-down'"
                class="icon-bottom-right-position"
                elevation="0"
                @click.stop="show = !show"
            >
            </v-btn>
            <v-expand-transition>
                <div v-show="show">
                    <v-divider></v-divider>
                    <v-card-text>
                        <p style="white-space: pre-line">
                            {{ getCardDetailText(properties.modelValue) }}
                        </p>
                    </v-card-text>
                </div>
            </v-expand-transition>
        </v-card>
    </v-container>
</template>
<script setup lang="ts">
import { ref } from "vue";

import { useTextGenerator } from "@/composables/textGenerator";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const properties = defineProps<{
    modelValue: Praktikumsstelle;
}>();

const show = ref<boolean>(false);
const generator = useTextGenerator();

function getCardText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardText(stelle);
}

function getCardDetailText(stelle: Praktikumsstelle): string {
    return generator.getPraktikumsstellenCardDetailText(stelle);
}
</script>
<style scoped>
.card {
    padding-right: 45px;
}

.icon-top-right-position {
    position: absolute;
    top: 20px;
    right: 20px;
}
.icon-bottom-right-position {
    position: absolute;
    bottom: 10px;
    right: 10px;
}
</style>
