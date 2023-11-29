package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.DayOfWeek;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateNWKDTO(
        @NotNull(message = "Der Vorname ist erforderlich") @Size(min = 2, max = 255, message = "Der Vorname darf nur zwischen {min} und {max} Zeichen lang sein") String vorname,
        @NotNull(message = "Der Nachname ist erforderlich") @Size(min = 2, max = 255, message = "Der Nachname darf nur zwischen {min} und {max} Zeichen lang sein") String nachname,
        @NotNull(message = "Der Studiengang ist erforderlich") Studiengang studiengang,
        @NotNull(message = "Der Jahrgang ist erforderlich") @Pattern(regexp = "\\d\\d/\\d\\d") String jahrgang,
        Set<DayOfWeek> vorlesungstage) {
}
