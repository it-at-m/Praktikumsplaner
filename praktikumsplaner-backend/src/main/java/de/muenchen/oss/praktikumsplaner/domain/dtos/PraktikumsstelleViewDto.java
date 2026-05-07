package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Praktikumsart;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
public record PraktikumsstelleViewDto(
        @NotNull UUID id,
        @NotNull String dienststelle,
        @NotNull String oertlicheAusbilder,
        boolean erwFuehrungszeugnisVorhanden,
        @Email @NotNull String email,
        @NotNull String taetigkeiten,
        @NotNull Dringlichkeit dringlichkeit,
        String namentlicheAnforderung,
        String programmierkenntnisse,
        String wuensche,
        boolean planstelleVorhanden,
        NwkDto assignedNwk,
        UUID meldezeitraumID,
        @NotNull Praktikumsart art,
        @NotNull Bildungsrichtung richtung,
        @NotNull String richtungLongName,
        Set<Ausbildungsjahr> ausbildungsjahr,
        Boolean minderjaehrigMoeglich,
        boolean projektarbeit,
        Set<Studiensemester> studiensemester) implements PraktikumsstelleDto {
}
