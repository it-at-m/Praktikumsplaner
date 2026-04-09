package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateAusbildungsPraktikumsstelleDto(@NotNull String dienststelle,

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

        @NotNull Set<Ausbildungsjahr> ausbildungsjahr,

        @NotNull Ausbildungsrichtung ausbildungsrichtung,

        boolean minderjaehrigMoeglich) {
}
