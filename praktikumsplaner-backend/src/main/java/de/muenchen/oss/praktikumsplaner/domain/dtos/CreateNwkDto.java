package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.DayOfWeek;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateNwkDto(
        @NotNull(message = "Der Vorname ist erforderlich") @Size(
                min = 2, max = 255, message = "Der Vorname darf nur zwischen {min} und {max} Zeichen lang sein"
        ) String vorname,
        @NotNull(message = "Der Nachname ist erforderlich") @Size(
                min = 2, max = 255, message = "Der Nachname darf nur zwischen {min} und {max} Zeichen lang sein"
        ) String nachname,
        @NotNull(message = "Die Richtung ist erforderlich") Bildungsrichtung richtung,
        @NotNull(message = "Der Jahrgang ist erforderlich") @Pattern(regexp = "\\d\\d/\\d\\d") String jahrgang,
        Set<DayOfWeek> vorlesungstage) {
}
