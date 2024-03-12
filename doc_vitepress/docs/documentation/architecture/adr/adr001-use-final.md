# ADR-001 use final

## Status

<adr-status status='accepted' />

## Decision

Parameters and local variables should have the `final` modifier unless a change of reference is required.

For local variables, we use `val` from the library.

### Todo

- The open PRs need to be adapted
- Right now, there is no existing code that needs to be adapted

## Consequences

The increased use of `final` can make the code less readable.
The decision to make a variable writable again has to be made consciously.