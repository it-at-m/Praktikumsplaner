# ADR-006 frontend components without complex logic

## Status

<adr-status status='accepted' />

## Context

By components are meant here vue components, in the form of single-file components. These contain a template,
a script part and styling.

## Decision

There should be no functional logic in components. The code of a component should be limited to databinding.
The logic, apart from databinding, is settled accomplished by composables or services.

### Todo

- components of open pull requests have to be adopted
- at the time of the decision there were no completed PRs so no existing code is to be adapted

## Consequences

The logic is easier to test because it can be tested independently of the components. The logic may need to be
written more generically because it is not coupled to the components.