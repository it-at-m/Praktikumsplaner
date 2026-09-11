package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
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

        Boolean programmierkenntnisse,
        String wuensche,
        Set<Ausbildungsjahr> ausbildungsjahr,
        Set<Studiensemester> studiensemester,

        @NotNull @Size(min = 1, max = 2) List<@Valid @NotNull AusbilderDto> ausbilder,

        NwkDto assignedNwk,
        UUID meldezeitraumID) implements PraktikumsstelleI {
}
