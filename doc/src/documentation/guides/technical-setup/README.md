# Technisches Setup

Im Folgenden wird das technische Setup beschrieben.

**Inhaltsverzeichnis**

[[TOC]]

## lokale Infrastruktur

Für die Entwicklung wird eine lokale Infrastruktur im stack Ordner bereitgestellt.

### Voraussetzungen

Folgende Programme / Tools müssen installiert sein:

- Docker (zur Nutzung der bereitgestellten Images)

### Verwendung der Anwendung mittels Docker

Im Ordner `stack` gibt es ein `docker compose`-File über dass alle notwendigen Container aufgebaut werden.
Dazu den Befehl `docker compose --profile frontend up -d` verwenden.

```mermaid
graph LR;

subgraph Praktiumsplaner

    subgraph Dockercontext
        Frontend
        Backend
        DBBackend[DB-Backend]
        Keycloak
        DB-Keycloak
        Init-Keycloak
    
        Frontend -->|fetch data| Backend
        Backend --> |check permission| Keycloak
        Backend -->|persisting| DBBackend
        Frontend -->|authenticate| Keycloak
        Keycloak -->|persisting| DB-Keycloak
        Init-Keycloak -->|configures| Keycloak
    end
    
 end
 
 Browser -->|localhost:8080| Frontend
```

**⚠ Hinweis**

Beim ersten Start wird das Frontend nicht erfolgreich mit starten. Das liegt daran dass der Keycloak noch beim Start
from Frontend noch nicht vollständig eingerichtet ist. Die Einrichtung vom Keycloak ist abgeschlossen wenn der Container
`init-keycloak``wieder gestoppt ist. Danach kann das Frontend gestartet werden.

Sobald alle Services, ausgenommen vom `init-*` gestartet sind kann auf die Anwendung via http://localhost:8080
zugegriffen werden. Zur Authentifizierung den Benutzer `testuser` mit dem Passwort `test` verwenden.

**⚠ Proxyhinweis**

Wenn im Browser ein Proxy eingerichtet ist bitte darauf achten dass dieser nicht `kubernetes.docker.internal` auflöst.

### Frontendentwicklung (in Progress)

*TBD*

### Backendentwicklung (in Progress)

#### Anbindung an Postgresql-DB in Docker

```mermaid
graph LR

subgraph Praktiumsplaner

    subgraph IDE
        IDEBackend[Backend]
    end

    subgraph Dockercontext
        DBBackend[DB Backend]
    end

    IDEBackend -->|localhost:5432| DBBackend
    
 end
```

In der bereitgestellten Infrastruktur gibt es eine Datenbank für das Backend. Um das Backend bei der Entwicklung damit zu
verbinden muss das Profil `db-postgres` verwendet werden. Es ist so konfiguriert, dass standardmäßig eine Verbindung
zur Infrastruktur aufgebaut wird.
