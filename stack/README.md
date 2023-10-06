# Stack

- Services
- DB: in memory (jdbc-url is in local-docker-backend.env)

## Docker

Use docker compose to start the infrastructure

```
# Starts the backendservice
docker compose up -d

# Includes the frontend service on startup
docker compose --profile frontend up -d
```

additional configuration for the services is done via `*.env`-Files