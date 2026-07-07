# UI Test IDs (`data-test`)

This guide describes how the frontend is instrumented for automated UI tests
(e.g. Selenium). The underlying decision is documented in
[ADR-010](../../architecture/adr/adr010-data-test-attributes.md).

## Goal

Provide **stable, text-independent selectors** for every relevant UI element so
that end-to-end tests do not break when styling, DOM structure or copy changes.

## The attribute

- We use the attribute **`data-test`**.
- Selenium selects elements via the CSS selector `[data-test='<id>']`.
- For raw input access use `[data-test='<id>'] input`,
  `[data-test='<id>'] textarea` etc., because Vuetify renders a wrapper around
  the native control.

```java
// Selenium (Java) example
driver.findElement(By.cssSelector("[data-test='nwk-create-dialog-open-btn']")).click();
driver.findElement(By.cssSelector("[data-test='nwk-vorname-input'] input")).sendKeys("Max");
```

## Central source of truth

All IDs live in `praktikumsplaner-frontend/src/testIds.ts` as typed constants
grouped by area. Templates import `testIds` and bind them:

```vue
<script setup lang="ts">
import { testIds } from "@/testIds";
</script>

<template>
  <v-btn :data-test="testIds.nwk.createDialogOpenBtn" />
</template>
```

Never inline raw `data-test` string literals in a template. Always add a
constant to `testIds.ts` first and reference it. This keeps the IDs
discoverable, typo-free and refactor-safe.

## Naming convention

- kebab-case values.
- Hierarchical, from area to concrete element: `<area>-<component>-<element>`.
- Suffix by element type where helpful: `-btn`, `-input`, `-select`,
  `-checkbox`, `-radio`, `-dialog`, `-table`, `-row`, `-link`.

Examples:

| Constant                          | Rendered `data-test` value   |
| --------------------------------- | ---------------------------- |
| `testIds.nav.nachwuchskraefte`    | `nav-nachwuchskraefte`       |
| `testIds.nwk.createDialogOpenBtn` | `nwk-create-dialog-open-btn` |
| `testIds.nwk.vornameInput`        | `nwk-vorname-input`          |
| `testIds.common.dataTableSearch`  | `data-table-search`          |
| `testIds.dialog.yesNoYesBtn`      | `yesno-dialog-yes-btn`       |

## What gets an ID

Instrumented:

- interactive elements: buttons, text fields, text areas, selects,
  autocompletes, checkboxes, radio groups, file inputs, date pickers;
- structural anchors: dialogs, data tables + rows, navigation entries,
  page title, list cards, the global snackbar and error dialog.

Not instrumented:

- purely decorative elements (icons, tooltips, spacers, dividers).

## Reusable components

Wrapper components such as `NameInput` or `DienststellenInput` carry a **fixed**
`data-test`. This is intentional and safe: the same wrapper never appears twice
on screen at the same time, because the dialogs and views that host them are
mutually exclusive. A test therefore always addresses a unique element.

## Adding a new element

1. Add a constant to the appropriate area object in `src/testIds.ts`.
2. Bind it in the template via `:data-test="testIds.<area>.<name>"`.
3. For inputs, remember tests select the inner control via
   `[data-test='...'] input`.
