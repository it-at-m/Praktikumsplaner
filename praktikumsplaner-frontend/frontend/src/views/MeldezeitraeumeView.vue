<template>
    <v-container class="finishedBackground">
        <page-title
            back-button-url="/"
            page-header-text="Meldezeiträume"
        >
        </page-title>
        <v-row>
            <v-col cols="9"></v-col>
            <v-col>
                <create-meldezeitraum
                    v-model="model"
                    @meldezeitraum-added="reloadMeldezeitraeume"
                ></create-meldezeitraum>
            </v-col>
        </v-row>
        <v-container class="box">
            <v-skeleton-loader
                v-if="loading"
                type="heading, divider, list-item, heading, divider, list-item, heading"
            >
            </v-skeleton-loader>
            <v-row>
                <v-col>
                    <meldezeitraum-list
                        v-if="!loading"
                        :value="current"
                        @deleted="reloadMeldezeitraeume"
                    >
                        <template #card-title-icon>
                            <v-icon>mdi-star</v-icon>
                        </template>
                        <template #notfoundmessage>
                            <p>Kein aktueller Meldezeitraum gefunden.</p>
                        </template>
                    </meldezeitraum-list>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row>
                <v-col>
                    <meldezeitraum-list
                        v-if="!loading"
                        :value="upcoming"
                        @deleted="reloadMeldezeitraeume"
                        ><template #header>
                            <h3>Kommende Meldezeiträume</h3>
                        </template>
                        <template #notfoundmessage>
                            <p>Keine kommenden Meldezeiträume gefunden.</p>
                        </template>
                    </meldezeitraum-list>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row>
                <v-col>
                    <meldezeitraum-list
                        v-if="!loading"
                        :value="passed"
                        @deleted="reloadMeldezeitraeume"
                        ><template #header>
                            <h3>Vergangene Meldezeiträume</h3>
                        </template>
                        <template #notfoundmessage>
                            <p>Keine vergangenen Meldezeiträume gefunden.</p>
                        </template>
                    </meldezeitraum-list>
                </v-col>
            </v-row>
        </v-container>
    </v-container>
</template>

<script setup lang="ts">
import { onBeforeMount, onMounted, ref } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import { UserService } from "@/api/UserService";
import PageTitle from "@/components/common/PageTitle.vue";
import CreateMeldezeitraum from "@/components/meldezeitraeume/CreateMeldezeitraum.vue";
import MeldezeitraumList from "@/components/meldezeitraeume/MeldezeitraumList.vue";
import { useUserStore } from "@/stores/user";
import Meldezeitraum from "@/types/Meldezeitraum";

const userService = new UserService();
const userStore = useUserStore();

const model = ref<boolean>(true);
const loading = ref<boolean>(false);
const current = ref<Meldezeitraum[]>([]);
const upcoming = ref<Meldezeitraum[]>([]);
const passed = ref<Meldezeitraum[]>([]);

onMounted(() => {
    reloadMeldezeitraeume();
});
onBeforeMount(() => {
    userService.getPermissions().then((userinfo) => {
        userStore.setUsername(userinfo.name);
        if (userinfo.user_roles) {
            userStore.setRoles(userinfo.user_roles);
        }
    });
});

function reloadMeldezeitraeume() {
    MeldezeitraumService.getCurrentMeldezeitraum(loading).then((response) => {
        current.value = response;
    });

    MeldezeitraumService.getPassedMeldezeitraueme(loading).then((response) => {
        passed.value = response;
    });

    MeldezeitraumService.getUpcomingMeldezeitraueme(loading).then(
        (response) => {
            upcoming.value = response;
        }
    );
}
</script>
