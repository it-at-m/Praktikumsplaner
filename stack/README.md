# Stack

- Services
  - require Keycloak for single sign on
- DBs:
  - in memory (jdbc-url is in local-docker-backend.env)
  - Postgres for Keycloak
- Keycloak

## Docker

Use docker compose to start the infrastructure

```
# Starts the backendservice
docker compose up -d

# Includes the frontend service on startup
docker compose --profile frontend up -d
```

additional configuration for the services is done via `*.env`-Files

### Keycloak

An admin user and a test user ist created by `init-keycloak`. You can change the configuration via the keycloak ui.

#### configuration migration

Realm, client user and other configuration should be done by the migration client. Its config files are located in
`keycloak\migartion`. The main file ist `keycloak-changelog.yml`. It contains the list of migration files that
should be applied. For more information check https://mayope.github.io/keycloakmigration/migrations/client/.


