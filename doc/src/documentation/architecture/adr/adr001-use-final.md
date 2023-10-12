# ADR-001 Benutzung von final

## Status

<adr-status status='accepted' />

## Decision

Parameter und lokale Variablen sollen immer als `final` markiert werden sofern nicht eine Veränderung der Referenz
erforderlich ist.

Für lokale Variablen soll, soweit möglich, von Lombok `val` verwendet werden.

### Todo

- die offenen PRs müssen angepasst werden
- zum Zeitpunkt der Entscheidung gab es noch keine abgeschlossenen PRs so dass kein bestehender Code anzupassen ist

## Consequences

Es kann dazu führen dass, durch die vermehrte Verwendung von `final`, der Code etwas schlechter Lesbar ist. Man muss sich jetzt
bewusst dafür entscheiden eine Variable für das Schreiben wieder freizugeben.