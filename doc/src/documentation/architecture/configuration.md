# configuration

Besides the spring configuration properties we created some for our own.

All our properties are located in `application.yml` and start with `app`.

## frontend

The configuration is splitted into separate files:
- `application-routing.yml` ... contains the route definitions for the gateway
- `application-security.yml` ... contains the configuration for security

The following `app`-properties were defined for the frontend:

| propertyname                                | description                                                                                                                                      |
|---------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| app.gateway.routing.sso.uri                 | URL to SSO for route with ID `sso`                                                                                                               |
| app.gateway.routing.backend.uri             | URL to the backend service (route with ID `backend`)                                                                                             |
| app.gateway.security.keycloak.issuer-uri    | URL to the sso realm. It used to get the sso config, for example. <br /> Alias for: `spring.security.oauth2.client.provider.keycloak.issuer-uri` |
| app.gateway.security.keycloak.realm         | name of the realm in keycloak                                                                                                                    |
| app.gateway.security.keycloak.client.id     | ID that is used by oauth2 client. <br /> Alias for: `spring.security.oauth2.client.registration.keycloak.client-id`                              |
| app.gateway.security.keycloak.client.secret | secret that is used by oauth2 client. <br /> Alias for: `spring.security.oauth2.client.registration.keycloak.client-secret`                      |
