# ADR-004 Tests zu Mapstructmappern auf ein Minimum reduzieren

## Status

<adr-status status='accepted' />

## Context

Beim Testen geht es uns darum, die Funktionsfähigkeit unserer Anwendung zu zeigen. Wir verlassen uns darauf, dass die
Frameworks und Bibliotheken die wir bei der Entwicklung verwenden ausreichend getestet sind. Daher können wir uns
beim Schreiben von Tests, speziell Unittests, darauf fokussieren den unsere Logik zu überprüfen.

## Decision

Bei den Tests zu den Mappern, die wir unter Verwendung von Mapstruct definieren, schreiben wir Tests, wenn wir
explizite Mappinglogik definieren. Diese explizite Mappinglogik liegt zum Beispiel dann vor, wenn wir in Interfaces
default-Implementierungen definieren oder über die Annotation `@Mapping` Felder mit unterschiedlichen Namen mappen oder
einen Ausdruck für das Mapping definieren.

Über Konfiguration vom Mapstructprozessor für das `maven-compile-plugin` wird definiert dass alle Felder im Zielobjekt
beim Mapping beachtet wurde. Ist ein Feld nicht Teil der Mappingdeklaration, soll es zu einem Fehler kommen.

### Todo
- Compilerargument beim `maven-compile-plugin` ergänzen: `-Amapstruct.unmappedTargetPolicy=ERROR`
- beim Mapping TargetProperties ignorieren wenn diese nicht durch das Sourceobjekt befüllbar sind:
`@Mapping(target="ignorableProp", ignore=true)`  

## Consequences

Es sind weniger Tests zu schreiben, dafür verlassen wir uns mehr auf die korrekte Funktionsweise von Mapstruct. Es ist
auch mehr Wissen über Mapstruct erforderlich da etwaige Annahme weniger geprüft werden.