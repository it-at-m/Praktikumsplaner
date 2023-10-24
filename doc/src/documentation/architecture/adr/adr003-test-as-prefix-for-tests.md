# ADR-003 `test` as prefix for test case methods

## Status

<adr-status status='accepted' />

## Context

The code should have a certain uniformity. This also applies to the tests. Experiences show that there were
different spellings for the method names of the test cases. Some test methods started with `test` others did
not.

## Decision

All test methods start with the prefix `test`. Corresponding to lowerCamelCase the method name continues afterwards with
an uppercase letter.

### Todo

- the code to existing PRs has to be adapted
- at the time of the decision there were no completed PRs so no existing code has to be adapted

## Consequences

Other than code uniformity and a slightly longer method name, no implications are noticeable.