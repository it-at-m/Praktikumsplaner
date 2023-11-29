package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.StartDateBeforeEndDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@StartDateBeforeEndDate(startDate = "startZeitpunkt", endDate = "endZeitpunkt", message = "Startdatum muss vor dem Enddatum liegen.")
public record CreateMeldezeitraumDto(
        @Size(max = 255, message = "Name für Zeitraum darf nicht länger als {max} Zeichen sein.")
        @NotBlank
        String zeitraumName,
        @NotNull LocalDate startZeitpunkt,
        @NotNull LocalDate endZeitpunkt)
{}
