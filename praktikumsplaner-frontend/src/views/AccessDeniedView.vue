<template>
    <v-container>
        <page-title
            back-button-url="/"
            page-header-text="Zurück zur Startseite"
        ></page-title>
        <v-row
            align="center"
            class="ml-auto"
        >
            <v-col cols="auto">
                <v-icon
                    size="large"
                    color="red"
                    >mdi-alert-octagon</v-icon
                >
            </v-col>
            <v-col>
                <h1>Zugriff verweigert</h1>
                <p class="m-2 p-2">
                    Du hast nicht die erforderlichen Rechte, um auf diese Seite
                    zuzugreifen.
                </p>
            </v-col>
        </v-row>
        <div class="d-flex justify-center">
            <img
                :src="catImagePath"
                height="650"
                width="650"
                alt="cat image did not load :("
            />
        </div>
    </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import cat1 from "@/assets/acess_denied_cats/cat1.png";
import cat2 from "@/assets/acess_denied_cats/cat2.png";
import cat3 from "@/assets/acess_denied_cats/cat3.png";
import cat4 from "@/assets/acess_denied_cats/cat4.png";
import cat5 from "@/assets/acess_denied_cats/cat5.png";
import cat6 from "@/assets/acess_denied_cats/cat6.png";
import cat7 from "@/assets/acess_denied_cats/cat7.png";
import cat8 from "@/assets/acess_denied_cats/cat8.png";
import cat9 from "@/assets/acess_denied_cats/cat9.png";
import cat10 from "@/assets/acess_denied_cats/cat10.png";
import cat11 from "@/assets/acess_denied_cats/cat11.png";
import cat12 from "@/assets/acess_denied_cats/cat12.png";
import PageTitle from "@/components/common/PageTitle.vue";

const catImagePath = ref();

onMounted(() => {
    selectRandomCatImage();
});

function selectRandomCatImage() {
    const catImages = [
        cat1,
        cat2,
        cat3,
        cat4,
        cat5,
        cat6,
        cat7,
        cat8,
        cat9,
        cat10,
        cat11,
        cat12,
    ];
    catImagePath.value =
        catImages[Math.floor(Math.random() * catImages.length)];
}
</script>
