import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import { createVuetify } from "vuetify";

export default createVuetify({
    theme: {
        defaultTheme: 'light',
        themes: {
            light: {
                colors: {
                    primary: "#005A9F",
                    secondary: "#FFCC00",
                    accent: "#7BA4D9",
                    success: "#69BE28",
                    error: "#FF0000",
                    errorExcel: "#EB96A0",
                    text: "#000000",
                },
            },
        },
    },
});
