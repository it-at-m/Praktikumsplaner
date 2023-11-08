import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue2";
import legacy from "@vitejs/plugin-legacy";
import { fileURLToPath, URL } from "url";
import { VuetifyResolver } from "unplugin-vue-components/resolvers";
import Components from "unplugin-vue-components/vite";
import AutoImport from "unplugin-auto-import/vite";
import eslint from "vite-plugin-eslint";

export default defineConfig({
    plugins: [
        vue(),
        eslint({
            fix: true,
        }),
        Components({
            resolvers: [
                VuetifyResolver(), // Fängt alle Komponenten ab, die mit `V` beginnen, was zu Problemen mit anderen Dependencies führen kann
            ],
            dts: false,
        }),
        AutoImport({
            imports: ["vue", "vitest"],
            ignore: ["createApp", "resolveComponent", "chai"],
        }),
        legacy({
            targets: ["defaults", "not IE 11"],
        }),
    ],
    server: {
        port: 8081,
    },
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    build: {
        //Wenn CCS Split Probleme macht
        //cssCodeSplit: false
    },
});
