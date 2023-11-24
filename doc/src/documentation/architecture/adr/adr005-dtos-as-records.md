# ADR-005 dtos as java records

## Status

<adr-status status='accepted' />

## Context

DTO classes are classes that transport data between layers or services.
They do not contain any business logic. The sender generates the data, and the receiver processes it.
Therefore, write access after the creation is no longer necessary.

## Decision

Records provide this functionality by themselves.

After creation, only read-access is possible.

By using the builder pattern, we do not have to use the all-arguments constructor all the time or have to write constructors with a reduced argument list.

We can use Lombok's `@Builder` annotation.

### Todo

- Dtos of open pull requests have to be adapted
- At the time of the decision, there were no completed PRs, so no existing code had to be adapted

## Consequences

This way, we reduce unwanted side effects because the properties of a DTO cannot be changed. If
objectfactories are used that fill only a part of the object, and other components finalize the object later, the builder must be used.