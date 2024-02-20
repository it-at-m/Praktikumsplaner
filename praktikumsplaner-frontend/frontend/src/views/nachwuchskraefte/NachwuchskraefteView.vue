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
                <v-btn
                    prepend-icon="mdi-plus"
                    disabled
                    class="bg-primary"
                    >Hinzufügen</v-btn
                >
            </v-col>
        </v-row>
        <v-container class="box">
            <active-nwk-list></active-nwk-list>
        </v-container>
    </v-container>
</template>

<script setup lang="ts">
import PageTitle from "@/components/common/PageTitle.vue";
import ActiveNwkList from "@/components/nachwuchskraefte/ActiveNwkList.vue";
import ExcelImportNwk from "@/components/nachwuchskraefte/ExcelImportNwk.vue";
</script>

<style scoped>
.box {
    margin: 2% 1% 1%;
    border: 2px solid #0000001a;
}
</style>
