module.exports = {
    root: true,
    env: {
        node: true,
        "vue/setup-compiler-macros": true,
    },
    plugins: ["vuetify"],
    extends: [
        // JavaScript
        "eslint:recommended",

        // Typescript
        "@vue/eslint-config-typescript",
        "@vue/eslint-config-typescript/recommended",

        // Vue
        "plugin:vue/vue3-essential",
        "plugin:vue/vue3-strongly-recommended",
        "plugin:vue/vue3-recommended",

        // Vuetify
        "plugin:vuetify/base",
        "plugin:vuetify/recommended",

        // Vermeidung Kollision mit Prettier
        "@vue/eslint-config-prettier",
    ],
    rules: {
        "no-console": "error",
        "vue/component-name-in-template-casing": ["error", "kebab-case"],
    },
};
