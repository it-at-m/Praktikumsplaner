import "@mdi/font/css/materialdesignicons.css";
import "@fontsource/roboto";
import Vue from "vue";
import Vuetify from "vuetify/lib";

Vue.use(Vuetify);

const theme = {
    themes: {
        light: {
            primary: "#005A9F",
            secondary: "#FFCC00",
            accent: "#7BA4D9",
            success: "#69BE28",
            error: "#FF0000",
            errorExcel: "#EB96A0",
        },
    },
    options: {
        customProperties: true,
    },
};

export default new Vuetify({
    theme: theme,
});
