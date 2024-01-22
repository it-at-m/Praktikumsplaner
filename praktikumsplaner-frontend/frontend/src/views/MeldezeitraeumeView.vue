<template>
    <v-container
        v-security.allow="['ROLE_AUSBILDUNGSLEITUNG']"
        class="finishedBackground"
    >
        <PageTitle
            back-button-url="/"
            page-header-text="Meldezeiträume"
        ></PageTitle>
        <CreateMeldezeitraum
            v-model="model"
            @meldezeitraumAdded="reloadMeldezeitraeume"
        ></CreateMeldezeitraum>
        <v-container>
            <v-row>
                <v-col>
                    <meldezeitraum-list :value="current">
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
                    <meldezeitraum-list :value="upcoming"
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
                    <meldezeitraum-list :value="passed"
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
import CreateMeldezeitraum from "@/components/meldezeitraeume/CreateMeldezeitraum.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import { onBeforeMount, onMounted, ref } from "vue";
import MeldezeitraumService from "@/api/MeldezeitraumService";
import Meldezeitraum from "@/types/Meldezeitraum";
import MeldezeitraumList from "@/components/meldezeitraeume/MeldezeitraumList.vue";
import { useUserStore } from "@/stores/user";
import { UserService } from "@/api/UserService";

const userService = new UserService();
const userStore = useUserStore();

const model = ref<boolean>(true);
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
    MeldezeitraumService.getCurrentMeldezeitraum().then((response) => {
        current.value = response;
    });

    MeldezeitraumService.getPassedMeldezeitraueme().then((response) => {
        passed.value = response;
    });

    MeldezeitraumService.getUpcomingMeldezeitraueme().then((response) => {
        upcoming.value = response;
    });
}
</script>

