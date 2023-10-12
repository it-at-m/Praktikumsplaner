# ADR-002 Keine `@Column`-Annotation wenn nicht unbedingt erforderlich

## Status

<adr-status status='accepted' />

## Context

Der Code soll leserlich sein und keine unnötigen Elemente enthalten. Es soll vermieden werden redundante Informationen
zu haben. Das Standardverhalten von Hibernate in der Anwendung ist, dass der Name der Tabellenspalte und der Name der
Property gleich sind.

## Decision

Die `@Column`-Annotation von Hibernate wird nur verwendet wenn sie unbedingt erforderlich ist. Das ist zum Beispiel der 
Fall, wenn der Name der Property in der Klasse ein Anderer ist als der Name der Spalte in der Tabelle.

In der Regel sollten die Namen zusammenpassen.

Zusätzliche Spaltendefinitionen erfolgen nicht mittels Hibernate da wir Flyway verwenden zur Versionierung des
Datenbankschemas.

### Todo

- Entfernung von entsprechenden Annotationen, bei den vorhanden Entitäten, bei den offenen PRs
- zum Zeitpunkt der Entscheidung gab es noch keine abgeschlossenen PRs, weshalb kein bestehender Code anzupassen ist
- Die Entität `TheEntity` spielt hierbei keine Rolle da diese zeitnah entfernt wird.

## Consequences

Umbenennungen ziehen sich durch den ganzen Code (Entitäten, DTOs). Dass zeigt noch einmal wie wichtig eine durchdachte
Benennung ist.

Man muss sich dessen bewusst sein dass Spaltenname und Propertynamen gleich sein müssen. Beim Blick in die Datenbank
ist sofort bekannt welche Property dazu gehört im Code. Dadurch sollte aber auch die Verständlichkeit steigen weil es
nicht unterschiedliche Begriffe für gleiche Sachverhalte gibt.
