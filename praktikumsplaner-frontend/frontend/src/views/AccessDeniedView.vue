<template>
    <div>
        <v-icon
            large
            color="red"
            >mdi-alert-octagon</v-icon
        >
        <h1>Zugriff verweigert</h1>
        <p>
            Du hast nicht die erforderlichen Rechte, um auf diese Seite
            zuzugreifen.
        </p>
        <v-btn
            color="primary"
            @click="goBack"
            >Zurück zur Startseite</v-btn
        >
    </div>
</template>

<script>
export default {
    methods: {
        goBack() {
            this.$router.push({ name: "home" });
        },
    },
};
</script>
