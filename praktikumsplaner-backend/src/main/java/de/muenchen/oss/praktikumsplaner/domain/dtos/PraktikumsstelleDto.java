package de.muenchen.oss.praktikumsplaner.domain.dtos;

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
public record PraktikumsstelleDto(
        @NotNull UUID id,
        @NotNull Bildungsrichtung richtung,
        @NotNull String dienststelle,
        @NotNull String taetigkeiten,
        @NotNull Dringlichkeit dringlichkeit,
        String namentlicheAnforderung,
        boolean projektarbeit,
        boolean planstelleVorhanden,

        String programmierkenntnisse,
        String wuensche,
        Set<Ausbildungsjahr> ausbildungsjahr,
        Set<Studiensemester> studiensemester,

        @NotNull String oertlicheAusbilder,
        @Email @NotNull String email,
        boolean erwFuehrungszeugnisVorhanden,
        boolean minderjaehrigMoeglich,

        NwkDto assignedNwk,
        UUID meldezeitraumID) {
}
