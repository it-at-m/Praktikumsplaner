package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.RichtungsArt;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
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

    String wuensche();

    NwkDto assignedNwk();

    UUID meldezeitraumID();

    // Unified fields
    // art is derived from richtung on the server side and exposed via DTO
    @NotNull RichtungsArt art();

    // Unified Richtung (covers former Studiengang and Ausbildungsrichtung)
    @NotNull Bildungsrichtung richtung();

    // Convenience long name for UI display
    @NotNull String richtungLongName();

    // AUSBILDUNG-only fields
    Set<Ausbildungsjahr> ausbildungsjahr();

    Boolean minderjaehrigMoeglich();

    boolean projektarbeit();

    // STUDIUM-only fields
    Set<Studiensemester> studiensemester();
}
