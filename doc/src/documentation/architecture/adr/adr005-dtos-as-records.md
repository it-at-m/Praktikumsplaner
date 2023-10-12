# ADR-005 DTOs werden als Records erstellt

## Status

<adr-status status='accepted' />

## Context

DTO-Klassen sind Klassen die Daten zwischen Layern oder Services transportieren. Sie enthalten keine fachliche
Funktionalität. Der Sender erzeugt die Daten und der Empfänger soll diese verarbeiten. Daher ist ein Schreibzugriff
nach dem Erstellen nicht mehr notwendig.

## Decision

Records bieten von sich aus diese Funktionalität. Nach der Erstellung kann nur noch lesend zugegriffen werden. Um nicht
immer den AllArgsConstructor verwenden zu müssen oder eigenen reduzierte Konstruktoren schreiben zu müssen, soll über
Lombok das Builder-Pattern implementiert werden.

### Todo

- DTOs bestehender PRs sind anzupassen
- zum Zeitpunkt der Entscheidung gab es noch keine abgeschlossenen PRs so dass kein bestehender Code anzupassen ist

## Consequences

Wir reduzieren auf diese Weise ungewollte Seiteneffekte da die Eigenschaften eines DTOs nicht veränderbar sind. Wenn
Objectfactories verwendet werden die nur einen Teil des Objektes befüllen und andere Komponenten das Objekt später
finalisieren muss der Builder verwendet werden.