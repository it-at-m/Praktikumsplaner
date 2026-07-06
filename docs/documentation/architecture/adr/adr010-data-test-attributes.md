# ADR-010 use data-test attributes for UI testing

## Status

<adr-status status='accepted' />

## Context

The UI is to be tested end-to-end with Selenium. Selenium locates elements via
selectors. Selecting elements by CSS classes (e.g. Vuetify utility classes),
tag structure or visible text is brittle: class names change with styling,
the DOM structure produced by Vuetify changes between versions, and visible
text changes with i18n or copy edits. Every such change silently breaks the
UI tests.

We therefore need selectors that are:

- stable across styling, refactoring and library upgrades,
- independent of visible text,
- explicitly maintained as a test contract.

## Decision

Every relevant UI element receives a dedicated `data-test` attribute.

- Attribute name: **`data-test`** (not `id`, not `data-testid`, not `data-cy`).
  Using `data-test` keeps a clear separation between production concerns
  (`id`, `class`) and the test contract, and works with a simple
  `[data-test='...']` selector in Selenium.
- The values are centrally defined in
  `praktikumsplaner-frontend/src/testIds.ts` as typed constants. Templates
  reference these constants instead of inlining string literals, so that the
  IDs are discoverable, refactor-safe and free of typos.
- Granularity: interactive elements (buttons, inputs, selects, checkboxes,
  radio groups, file inputs) **and** structural anchor elements (dialogs,
  data tables and their rows, navigation entries, page titles, list cards,
  snackbar/error dialogs) are instrumented. Purely decorative elements
  (icons, tooltips, spacers) are not.
- Naming convention: kebab-case, hierarchical from area to element, in the
  form `<area>-<component>-<element>`, e.g.
  `nwk-create-dialog-open-btn`, `praktikumsstelle-dienststelle-input`,
  `data-table-search`. The convention is documented in the
  [UI test IDs guide](../../guides/ui-test-ids/index.md).

### How `data-test` reaches the DOM

Vuetify components pass unknown attributes (fallthrough attributes) to their
root element. A `data-test` on `<v-btn>` / `<v-text-field>` therefore appears
on the rendered root element. For inputs, Selenium can select the input via
`[data-test='...'] input` when the raw `<input>` is required.

Reusable wrapper components (e.g. `NameInput`, `DienststellenInput`) carry a
fixed `data-test` value. This is safe because two instances of the same
wrapper are never visible at the same time (dialogs and views are mutually
exclusive on screen).

### Todo

- Apply `data-test` to all existing views and components.
- New components must add `data-test` for interactive and anchor elements and
  register the ID in `testIds.ts`.

## Consequences

- UI tests become robust against restyling, DOM refactoring and Vuetify
  upgrades.
- There is one central, typed source of truth for all test IDs.
- Every new interactive element requires a small extra step (adding a constant
  and referencing it), which has to be enforced in review.
