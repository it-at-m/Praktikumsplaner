package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public interface PraktikumsstelleDto {
    @SuppressWarnings("PMD.ShortMethodName")
    @NotNull UUID id();

    @NotNull String dienststelle();

    @NotNull String oertlicheAusbilder();

    boolean erwFuehrungszeugnisVorhanden();

    @Email @NotNull String email();

    @NotNull String taetigkeiten();

    @NotNull Dringlichkeit dringlichkeit();

    boolean planstelleVorhanden();

    String namentlicheAnforderung();

    String programmierkenntnisse();

    NwkDto assignedNwk();

    UUID meldezeitraumID();
}
