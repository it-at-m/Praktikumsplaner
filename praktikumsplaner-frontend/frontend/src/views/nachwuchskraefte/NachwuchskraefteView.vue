<template>
    <v-container>
        <page-title
            page-header-text="Nachwuchskräfte"
            back-button-url="/"
        ></page-title>
        <v-row>
            <v-col cols="7"></v-col>
            <v-col>
                <excel-import-nwk></excel-import-nwk>
            </v-col>
            <v-col>
                <v-btn
                    disabled
                    class="primary"
                >
                    <v-icon>mdi-plus</v-icon>
                    Hinzufügen
                </v-btn>
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
import ExcelImportNwk from "@/components/nachwuchskraefte/ExcelImportNwk.vue";
import PageTitle from "@/components/common/PageTitle.vue";
import ActiveNwkList from "@/components/nachwuchskraefte/ActiveNwkList.vue";
</script>

<style scoped>
.box {
    margin: 1%;
    border: 2px solid #0000001a;
}
</style>