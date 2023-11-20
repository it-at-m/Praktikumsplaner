package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;

import java.util.UUID;

public interface PraktikumsstelleDTO {
    UUID id();
    String dienststelle();
    String oertlicheAusbilder();
    String email();
    String taetigkeiten();
    Dringlichkeit dringlichkeit();
    String namentlicheAnforderung();
    Referat referat();
    // Beachten Sie, dass die Methode getDienststelle() in der Schnittstelle jetzt dienststelle() heißt,
    // was der Namenskonvention für die von einem Record generierte Methode entspricht.
}
