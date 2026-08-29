# AGENTS.md

This file provides guidance to AI coding agents when working with code in this repository.

## Overview

Praktikumsplaner organizes junior staff ("Nachwuchskräfte") and their internships
during vocational training. It is a two-part application: a Spring Boot backend
(`praktikumsplaner-backend`) and a Vue 3 frontend (`praktikumsplaner-frontend`).
The `stack/` folder holds a Docker Compose setup with all supporting infrastructure
(Keycloak SSO, Postgres, gateway, mail). Documentation lives in `docs/` (VitePress).

The domain language is **German** — entities, DTOs, enums, API paths and most identifiers
use German terms. Keep new code consistent with this. Key terms:
- **Nwk / Nachwuchskraft** — a trainee/junior staff member.
- **Praktikumsstelle** — an internship position. Two kinds: `AusbildungsPraktikumsstelle`
  (apprenticeship) and `StudiumsPraktikumsstelle` (degree program), both extending `BasePraktikumsstelle`.
- **Meldezeitraum** — the reporting/registration period during which positions are offered.
- **Zuweisung** — assignment of an Nwk to a Praktikumsstelle.

## Commands

### Backend (`praktikumsplaner-backend/`, Java 21, Maven)
- Build + run all checks: `mvn verify` (runs Spotless, PMD, SpotBugs/FindSecBugs, JaCoCo).
- Run app locally: `mvn spring-boot:run -Dspring-boot.run.profiles=local,db-postgres,no-security`
  (drop `no-security` to require an API gateway with active security). Serves on port **8082**.
- Run tests: `mvn test`. Single test class: `mvn test -Dtest=NwkServiceTest`.
  Single method: `mvn test -Dtest=NwkServiceTest#testSomething`.
- Auto-format (Spotless): `mvn spotless:apply`. Linting is enforced at build time and will
  fail the build — run this before committing.
- Integration tests (`*IntegrationTest`) use Testcontainers (Postgres) and require Docker running.

### Frontend (`praktikumsplaner-frontend/`, Node >=24.11 <25, npm)
- Install: `npm install`.
- Dev server (no auth, fastest): `npm run dev:no-security`. With security: `npm run dev`
  (requires a running API gateway + Keycloak; log in at the gateway first).
- Build: `npm run build`. Tests: `npm run test` (Vitest). Single test: `npm run test -- <file-or-pattern>`.
- Lint: `npm run lint` (Prettier check + ESLint + `vue-tsc`). Auto-fix: `npm run fix`.

> Note: `docs/` and the frontend README reference older script names (`npm run serve` / `npm run security`);
> the authoritative scripts are in `praktikumsplaner-frontend/package.json` (`dev` / `dev:no-security`).

### Local infrastructure (`stack/`)
- `docker compose up -d` — infra only (Keycloak, Postgres, gateway, mail); run backend/frontend from your IDE.
- `docker compose --profile full up -d` — everything including built backend/frontend images.
- Keycloak realm/users are provisioned by the `init-keycloak` migration container; the frontend
  will fail on first start until `init-keycloak` finishes. App reachable at <http://localhost:8080>;
  test login `testleitung` / `test`. Mailpit UI on 8025, pgAdmin on 5050.

## Architecture

### Backend — layered, package-by-feature under `de.muenchen.oss.praktikumsplaner`
`rest` (controllers) → `service` (business logic) → `repository` (Spring Data JPA) → `domain` (entities).
- `domain/dtos` — request/response DTOs; `domain/mappers` — MapStruct mappers; `domain/enums` and
  `domain/converter` — JPA attribute converters for enums/collections.
- Entities extend `BaseEntity`. Schema is owned by **Flyway** (`resources/db/migration/schema`,
  validate-only Hibernate); test data lives in `db/migration/testdata` and is only loaded under the
  `local` profile. **Never** let Hibernate alter schema — add a new versioned migration instead.
- Security is **role-based** (not permission-based). Roles: `AUSBILDUNGSLEITUNG`, `AUSBILDER`, `NWK`
  (see `security/Authorities.java`). Every controller method must carry `@PreAuthorize(...)` using the
  `Authorities` constants. Roles come from Keycloak via OAuth2 resource-server JWTs.
- API errors: throw `ResponseStatusException` (or use `GlobalExceptionHandler`) with a meaningful
  message string and HTTP status — the frontend surfaces the message text to the user.
- OpenAPI spec is generated from the running app (`springdoc-openapi-maven-plugin`).

### Frontend — Vue 3 + Vuetify + Pinia + vue-router (`src/`)
- `api/` — service modules wrapping `fetch`; all requests go through `FetchUtils.ts`
  (`getGETConfig`/`getPOSTConfig`/…, CSRF via `X-XSRF-TOKEN` cookie, `defaultResponseHandler`
  for centralized 403 / redirect-reload / server-error handling). Backend is reached via the
  `/api/backend-service` prefix (constants in `constants.ts`); the gateway rewrites/proxies it.
- `components/` — single-file components organized by feature (`assign`, `meldezeitraeume`,
  `nachwuchskraefte`, `praktikumsplaetze`, `common`). Components do **data binding only** — keep
  functional logic out of them; put it in `composables/` or `api/` services.
- `composables/security.ts` (`useSecurity`) mirrors backend roles for conditional UI; when
  `VITE_APP_SECURITY` is off all role checks pass (dev convenience).
- `stores/` — Pinia stores (user, snackbar, user-error, event bus). i18n strings via `vue-i18n`.
- Env-driven behavior via Vite modes/`.env*` files (`.env`, `.env.no-security`, `.env.production`).

## Conventions (from `docs/.../architecture/adr/`)
- **DTOs are Java `record`s**, built with Lombok `@Builder` (ADR-005).
- **Mark parameters and local variables `final`** unless reassignment is needed; use Lombok `val`
  for locals (ADR-001).
- **Avoid `@Column`** — JPA property name must equal the DB column name; only annotate when they
  must differ (ADR-002).
- **Test methods are prefixed `test`** in lowerCamelCase (ADR-003).
- **Don't test MapStruct mappers** unless they contain explicit/custom mapping logic; the compiler
  is configured with `unmappedTargetPolicy=ERROR` so unmapped fields fail the build (ADR-004).
- Backend secured via `@PreAuthorize`, frontend via role checks; both role-based (ADR-008).
- Backend returns the error message string to the frontend on errors (ADR-007).
