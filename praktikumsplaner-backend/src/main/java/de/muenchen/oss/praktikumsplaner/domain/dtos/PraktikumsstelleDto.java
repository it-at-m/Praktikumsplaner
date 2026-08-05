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
        @NotNull String dienststelle,
        @NotNull String oertlicheAusbilder,
        boolean erwFuehrungszeugnisVorhanden,
        @Email @NotNull String email,
        @NotNull String taetigkeiten,
        @NotNull Dringlichkeit dringlichkeit,
        String namentlicheAnforderung,
        boolean projektarbeit,
        String programmierkenntnisse,
        String wuensche,
        boolean planstelleVorhanden,
        Set<Ausbildungsjahr> ausbildungsjahr,
        Set<Studiensemester> studiensemester,
        @NotNull Bildungsrichtung richtung,
        NwkDto assignedNwk,
        UUID meldezeitraumID,
        boolean minderjaehrigMoeglich) {
}
