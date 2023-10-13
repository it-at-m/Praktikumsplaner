# ADR-005 dtos as java records

## Status

<adr-status status='accepted' />

## Context

DTO classes are classes that transport data between layers or services. They do not contain any business
functionality. The sender generates the data and the receiver process it. Therefore, a write access after the
creation is no longer necessary.

## Decision

Records provide this functionality by themselves. After creation, only read access is possible. By using the builder
pattern we do not have to use the all arguments constructor all time, neither have to write constructors with
reduced argument list. We can use lomboks `@Builder` annotation.

### Todo

- dtos of open pull requests have to be adopted
- at the time of the decision there were no completed PRs so no existing code is to be adapted

## Consequences

We reduce unwanted side effects in this way because the properties of a DTO cannot be changed. If
objectfactories are used that fill only a part of the object and other components finalize the object later
finalize the object later, the builder must be used.