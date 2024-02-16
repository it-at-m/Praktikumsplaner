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
            error: "#9A1818",
            warning: "#333333",
            info: "#696969",
            errorExcel: "#EB96A0",
            text: "#000000",
        },
    },
    options: {
        customProperties: true,
    },
};

export default new Vuetify({
    theme: theme,
});
