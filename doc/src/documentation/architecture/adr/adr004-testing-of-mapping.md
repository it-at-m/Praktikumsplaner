# ADR-004 Reduce mapstructmapper tests to a minimum

## Status

<adr-status status='accepted' />

## Context

Testing is all about showing the functionality of our application. We rely on the fact that the
frameworks and libraries we use during development are sufficiently tested. Therefore, we can focus on testing our
logic while writing tests, especially unit tests.

## Decision

Mappers are only tested if we defined explicit mapping logic.
This explicit mapping logic is present, for example, when we define default implementations in interfaces or map
fields with different names via the `@mapping` annotation or define an expression for the mapping.

Via configuration of the mapstructprocessor for the `maven-compile-plugin` it is defined that all fields in the target
object are considered for mapping. If a field is not part of the mapping declaration, an error should occur.

### Todo
- add compiler argument at `maven-compile-plugin`: `-Amapstruct.unmappedTargetPolicy=ERROR`
- ignore target properties when mapping if they are not fillable by the source object:
`@Mapping(target="ignorableProp", ignore=true)`  

## Consequences

There are fewer tests to write, but we rely more on Mapstruct to work correctly. More knowledge about Mapstruct is
required, since assumptions are less checked.