package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public interface PraktikumsstelleDTO {
    @NotNull
    UUID id();

    @NotNull
    String dienststelle();

    @NotNull
    String oertlicheAusbilder();

    @Email
    @NotNull
    String email();

    @NotNull
    String taetigkeiten();

    @NotNull
    Dringlichkeit dringlichkeit();

    @NotNull
    boolean planstelleVorhanden();

    String namentlicheAnforderung();

    Referat referat();

    NwkDTO assignedNWK();
}
