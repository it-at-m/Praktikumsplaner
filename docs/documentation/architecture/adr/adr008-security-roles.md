# ADR-008 security-roles

## Status

<adr-status status='accepted' />

## Context

We need to restrict access to certain features of the application so users can only use and see what is meant for them.

Permission-based access would require a non-open source component, and this product should be open source in all aspects.

## Decision

We are using a role-based security model in favor of a permission-based model.

### Todo

- All backend rest controller methods need the `@PreAuthorized`-Annotation for role binding
- All frontend components that should be secured should have the custom v-security directive
- Roles for personas have to be defined and implemented in the keycloak

## Consequences

Access restrictions have to be considered for each feature.
By using role-based access management, the authorization is less complicated,
than it would be with permission-based access management, as the granularity is lower and therefore easier to maintain.