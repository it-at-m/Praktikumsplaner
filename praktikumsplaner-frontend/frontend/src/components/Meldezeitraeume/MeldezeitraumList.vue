<template>
    <v-col>
        <slot name="header"></slot>
        <v-list v-if="props.value.length > 0">
            <v-card
                v-for="meldezeitraum in props.value"
                :key="meldezeitraum.id"
            >
                <v-divider></v-divider>
                <v-list-item two-line>
                    <v-list-item-content>
                        <v-list-item-title class="justify-center">
                            {{ meldezeitraum.zeitraumName }}
                            <slot name="card-title-icon"></slot>
                        </v-list-item-title>
                        <v-list-item-subtitle>
                            <v-icon>mdi-calendar-start</v-icon>
                            {{
                                useFormatter().formatDateFromString(
                                    meldezeitraum.zeitraum.startZeitpunkt
                                )
                            }}

                            <v-icon>mdi-calendar-end</v-icon>
                            {{
                                useFormatter().formatDateFromString(
                                    meldezeitraum.zeitraum.endZeitpunkt
                                )
                            }}
                        </v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
                <v-divider></v-divider>
            </v-card>
        </v-list>
        <slot
            v-else
            name="notfoundmessage"
        ></slot>
    </v-col>
</template>

<script setup lang="ts">
import Meldezeitraum from "@/types/Meldezeitraum";
import { useFormatter } from "@/composables/formatter";

const props = defineProps<{
    value: Meldezeitraum[];
}>();
</script>