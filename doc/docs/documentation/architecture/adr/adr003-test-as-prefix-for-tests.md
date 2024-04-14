# ADR-003 `test` as a prefix for test case methods

## Status

<adr-status status='accepted' />

## Context

The code should have a certain uniformity. This also applies to the tests.
Experiences show that there were different spellings for the method names of the test cases.
Some test methods started with `test` others did not.

## Decision

All test methods start with the prefix `test`.

Corresponding to lowerCamelCase, the method name continues afterward with an uppercase letter.

### Todo

- The code for existing PRs has to be adapted
- At the time of the decision, there were no completed PRs, so no existing code had to be adapted

## Consequences

Other than code uniformity and a slightly longer method name, no implications are noticeable.