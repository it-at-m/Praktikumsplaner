package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.RichtungValid;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
@RichtungValid
public record CreatePraktikumsstelleDto(
        @NotNull String dienststelle,

        @NotNull String oertlicheAusbilder,

        boolean erwFuehrungszeugnisVorhanden,

        @Email @NotNull String email,

        @NotNull String taetigkeiten,

        @NotNull Dringlichkeit dringlichkeit,

        String namentlicheAnforderung,

        // Unified Richtung (replaces former studiengang/ausbildungsrichtung)
        @NotNull Bildungsrichtung richtung,

        // STUDIUM-only
        String programmierkenntnisse,

        String wuensche,

        boolean planstelleVorhanden,

        // STUDIUM-only
        Set<Studiensemester> studiensemester,

        // AUSBILDUNG-only
        Set<Ausbildungsjahr> ausbildungsjahr,

        // AUSBILDUNG-only
        Boolean minderjaehrigMoeglich,

        // Optional for Ausbildungsleitung-create
        UUID meldezeitraumID) {
}
