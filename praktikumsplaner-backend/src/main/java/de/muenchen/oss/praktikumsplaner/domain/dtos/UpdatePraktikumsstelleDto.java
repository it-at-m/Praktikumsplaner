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
public record UpdatePraktikumsstelleDto(
        @NotNull String dienststelle,

        @NotNull String oertlicheAusbilder,

        boolean erwFuehrungszeugnisVorhanden,

        @Email @NotNull String email,

        @NotNull String taetigkeiten,

        @NotNull Dringlichkeit dringlichkeit,

        String namentlicheAnforderung,

        @NotNull Bildungsrichtung richtung,

        String programmierkenntnisse,

        String wuensche,

        boolean planstelleVorhanden,

        Set<Studiensemester> studiensemester,

        Set<Ausbildungsjahr> ausbildungsjahr,

        Boolean minderjaehrigMoeglich,

        @NotNull UUID meldezeitraumID) {
}
