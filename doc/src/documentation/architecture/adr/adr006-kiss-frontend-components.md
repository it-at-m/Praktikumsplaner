# ADR-006 Frontendkomponenten ohne komplexe Logik

## Status

<adr-status status='accepted' />

## Context

Mit Komponenten sind hier vue-Komponenten, in Form von Single-File-Components, gemeint. Diese beinhalten ein Template,
einen script-Teil und Styling.

## Decision

In Komponenten soll es keine funktionale Logik geben. Der Code einer Komponente soll sich auf das Databinding
beschränken. Die Logik abseits des Databindings wird über Composables oder Services erledigt.

### Todo

- bei Komponenten von offenen PRs müssen ggf. Anpassungen vorgenommen werden
- zum Zeitpunkt der Entscheidung gab es noch keine abgeschlossenen PRs so dass kein bestehender Code anzupassen ist

## Consequences

Die Logik ist leichter testbar da sie unabhängig von der Komponenten getestet werden kann. Es muss darauf geachtet
werden die Logik ggf. generischer zu schreiben da sie nicht an die Komponenten gekoppelt ist.