module.exports = {
    root: true,
    env: {
        node: true,
        "vue/setup-compiler-macros": true,
    },
    plugins: ["vuetify"],
    extends: [
        // JavaScript/TypeScript-Regeln
        "eslint:recommended",
        "plugin:@typescript-eslint/recommended",
        // Vue-Regeln: https://eslint.vuejs.org/rules/
        "plugin:vue/base",
        "plugin:vue/essential",
        "plugin:vue/strongly-recommended",
        "plugin:vue/recommended",
        "@vue/typescript",
        // Vuetify-Regeln: https://github.com/vuetifyjs/eslint-plugin-vuetify
        "plugin:vuetify/base",
        "plugin:vuetify/recommended",
        "plugin:prettier-vue/recommended",
    ],
    settings: {
        "prettier-vue": {
            // Settings for how to process Vue SFC Blocks
            SFCBlocks: {
                /**
                 * Use prettier to process `<template>` blocks or not
                 *
                 * If set to `false`, you may need to enable those vue rules that are disabled by `eslint-config-prettier`,
                 * because you need them to lint `<template>` blocks
                 *
                 * @default true
                 */
                template: true,

                /**
                 * Use prettier to process `<script>` blocks or not
                 *
                 * If set to `false`, you may need to enable those rules that are disabled by `eslint-config-prettier`,
                 * because you need them to lint `<script>` blocks
                 *
                 * @default true
                 */
                script: true,

                /**
                 * Use prettier to process `<style>` blocks or not
                 *
                 * @default true
                 */
                style: true,

                // Settings for how to process custom blocks
                customBlocks: {
                    // Treat the `<docs>` block as a `.markdown` file
                    docs: { lang: "markdown" },

                    // Treat the `<config>` block as a `.json` file
                    config: { lang: "json" },

                    // Treat the `<module>` block as a `.js` file
                    module: { lang: "js" },

                    // Ignore `<comments>` block (omit it or set it to `false` to ignore the block)
                    comments: false,

                    // Other custom blocks that are not listed here will be ignored
                },
            },

            // Use prettierrc for prettier options or not (default: `true`)
            usePrettierrc: true,

            // Set the options for `prettier.getFileInfo`.
            // @see https://prettier.io/docs/en/api.html#prettiergetfileinfofilepath-options
            fileInfoOptions: {
                // Path to ignore file (default: `'.prettierignore'`)
                // Notice that the ignore file is only used for this plugin
                ignorePath: ".testignore",

                // Process the files in `node_modules` or not (default: `false`)
                withNodeModules: false,
            },
        },
    },
    rules: {
        "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
        "space-before-function-paren": 0,
        "vue/component-name-in-template-casing": ["error", "kebab-case"],
        "vue/require-default-prop": "off",
        "vue/script-setup-uses-vars": "error",
    },
    parser: "vue-eslint-parser",
    parserOptions: {
        parser: "@typescript-eslint/parser",
    },
    overrides: [
        {
            files: [
                "**/__tests__/*.{j,t}s?(x)",
                "**/tests/unit/**/*.spec.{j,t}s?(x)",
            ],
            rules: {
                "@typescript-eslint/no-explicit-any": "off",
            },
            env: {
                jest: true,
            },
        },
    ],
};
