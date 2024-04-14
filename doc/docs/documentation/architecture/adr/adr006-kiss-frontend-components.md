# ADR-006 frontend components without complex logic

## Status

<adr-status status='accepted' />

## Context

The term components stands for the vue components, in the form of single-file components, in this context. These contain a template,
a script part and styling.

## Decision

There should be no functional logic in components. The code of a component should be limited to data binding.
The logic, apart from data binding, is implemented in composables or services.

### Todo

- Components of open pull requests have to be adapted
- At the time of the decision, there were no completed PRs, so no existing code had to be adapted

## Consequences

The logic is easier to test because it can be tested independently of the components. The logic may need to be
written more generically because it is not coupled to the components.