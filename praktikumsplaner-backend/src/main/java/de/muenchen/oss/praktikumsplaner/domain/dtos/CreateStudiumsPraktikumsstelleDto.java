package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateStudiumsPraktikumsstelleDto(@NotNull String dienststelle,

        @NotNull String oertlicheAusbilder,

        boolean erwFuehrungszeugnisVorhanden,

        @Email @NotNull String email,

        @NotNull String taetigkeiten,

        @NotNull Dringlichkeit dringlichkeit,

        String namentlicheAnforderung,

        @NotNull String programmierkenntnisse,

        String wuensche,

        boolean planstelleVorhanden,

        @NotNull Set<Studiensemester> studiensemester,

        @NotNull Studiengang studiengang) {
}
