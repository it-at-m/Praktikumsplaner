module.exports = {
    $schema: "https://json.schemastore.org/prettierrc",
    trailingComma: "es5",
    tabWidth: 4,
    singleAttributePerLine: true,
    plugins: ["@ianvs/prettier-plugin-sort-imports"],
    importOrder: [
        "<BUILTIN_MODULES>", // Node-Module
        "",
        "<TYPES>", // Standardtypen
        "",
        "<THIRD_PARTY_MODULES>", // Bibliotheken
        "",
        "<TYPES>^[.]", // Eigens definierte Typen
        "",
        "^@(/.*)$", // Relative Imports mittels @
        "^[.]", // Relative Imports mittels .
    ],
    importOrderTypeScriptVersion: "5.2.2",
    importOrderParserPlugins: ["typescript"],
};
