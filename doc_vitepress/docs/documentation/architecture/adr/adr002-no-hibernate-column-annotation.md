# ADR-002 Don't use `@Column`-annotation

## Status

<adr-status status='accepted' />

## Context

The code should be readable and contain no unnecessary elements.
Consequently, we should avoid redundant information.
The default behavior of Hibernate in our application is that the name of the table column and the name of the property are equal.

## Decision

Hibernate's `@Column` annotation is only used when absolutely necessary, for example when the name of the table column and the name of the property differ.

Mainly, the name should match.

Additional column definitions are not done by Hibernate, as we use Flyway for versioning database schemas.

### Todo

- Removal of `@Column` annotations from entities in open pull requests
- Right now, there is no existing code that needs to be adapted
- The entity `TheEntity` is not addressed because it will be removed soon

## Consequences

Renaming will affect the entire code (entities, dtos). This shows once again how important a well-thought-out naming is.

It is very important that the column name and the property name must be the same.
This way, while examining the database, it is immediately apparent which property in the source code corresponds to each part of it.
This should also increase comprehension because there are no different terms for the same things.