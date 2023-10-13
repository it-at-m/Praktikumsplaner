# ADR-001 use final

## Status

<adr-status status='accepted' />

## Decision

Parameters and local variables should have the `final` modifier unless a change of the reference is required.

For local variables we use `val` of the lombok library.

### Todo

- the open PRs need to be adapted
- right now, there is no existing code needs to be adapted

## Consequences

The increased use of `final` can make the code less readable. You have to do it now consciously decide to release a
variable for writing again.