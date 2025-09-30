// Plugins
import { fileURLToPath, URL } from "node:url";

import vue from "@vitejs/plugin-vue";
import ViteFonts from "unplugin-fonts/vite";
// Utilities
import { defineConfig } from "vite";
import vuetify, { transformAssetUrls } from "vite-plugin-vuetify";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue({
            template: { transformAssetUrls },
        }),
        // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vite-plugin
        vuetify({
            autoImport: true,
        }),
        ViteFonts({
            fontsource: {
                families: [
                    {
                        name: "Roboto",
                        weights: [100, 300, 400, 500, 700, 900],
                        subset: "latin",
                    },
                ],
            },
        }),
    ],
    define: { "process.env": {} },
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
        extensions: [".js", ".json", ".jsx", ".mjs", ".ts", ".tsx", ".vue"],
    },
    server: {
        host: true,
        port: 8081,
        proxy: {
            "/api": "http://localhost:8083",
            "/actuator": "http://localhost:8083",
        },
        allowedHosts: ["host.docker.internal"], // required to use frontend behind proxy (e.g. API Gateway)
        headers: {
            "x-frame-options": "SAMEORIGIN", // required to use devtools behind proxy (e.g. API Gateway)
        },
    },
});
