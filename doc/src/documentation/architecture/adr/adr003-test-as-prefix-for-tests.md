# ADR-003- `test` als Präfix bei Tests

## Status

<adr-status status='accepted' />

## Context

Der Code sollte eine gewisse Einheitlichkeit aufweisen. Dies betrifft auch die Tests. Es der Erfahrung heraus gab es 
unterschiedliche Schreibweisen für die Methodennamen der Testfälle. Manche Testmethoden begannen mit `test` andere
nicht.

## Decision

Alle Testmethoden beginnen mit dem Präfix `test`. Ensptrechend lowerCamelCase geht der Methodename danach weiter mit
einem Großbuchstaben.

### Todo

- der Code zu bestehenden PRs soll angepasst werden
- zum Zeitpunkt der Entscheidung gab es noch keine abgeschlossenen PRs so dass kein bestehender Code anzupassen ist

## Consequences

Abgesehen von der Einheitlichkeit des Codes und ein leicht längerer Methodenname sind keine Implikationen erkennbar.