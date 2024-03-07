<template>
    <v-container>
        <page-title
            page-header-text="Nachwuchskräfte"
            back-button-url="/"
        ></page-title>
        <v-row>
            <v-col cols="8"></v-col>
            <v-col>
                <excel-import-nwk></excel-import-nwk>
            </v-col>
            <v-col>
                <nwk-create-dialog></nwk-create-dialog>
            </v-col>
        </v-row>
        <v-row></v-row>
        <v-container class="box">
            <span> Übersicht</span>
            <active-nwk-list></active-nwk-list>
        </v-container>
    </v-container>
</template>

<script setup lang="ts">
import { onMounted, watch } from "vue";

import PageTitle from "@/components/common/PageTitle.vue";
import ActiveNwkList from "@/components/nachwuchskraefte/ActiveNwkList.vue";
import ExcelImportNwk from "@/components/nachwuchskraefte/ExcelImportNwk.vue";
import NwkCreateDialog from "@/components/nachwuchskraefte/NwkCreateDialog.vue";
import { useSecurity } from "@/composables/security";
import router from "@/router";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const route = router.currentRoute.value;

onMounted(() => {
    if (userStore.username !== undefined) {
        redirectIfUnauthorized();
    } else {
        // This Watcher is responsible for redirecting the user to the AccessDenied view if his roles do not suffice
        watch(
            () => userStore.roles,
            () => {
                redirectIfUnauthorized();
            }
        );
    }
});

function redirectIfUnauthorized() {
    const requiresRoles =
        route.meta.requiresRole != undefined
            ? (route.meta.requiresRole as string[])
            : undefined;
    const security = useSecurity();
    if (
        requiresRoles !== undefined &&
        !security.checkForAnyRole(requiresRoles)
    ) {
        router.push("/AccessDenied");
    }
}
</script>

<style scoped>
.box {
    margin: 2% 1% 1%;
    border: 2px solid #0000001a;
}
</style>
