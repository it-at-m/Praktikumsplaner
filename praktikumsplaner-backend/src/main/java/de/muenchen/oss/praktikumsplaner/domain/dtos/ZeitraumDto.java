package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.StartDateBeforeEndDate;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@StartDateBeforeEndDate(startDate = "startZeitpunkt", endDate = "endZeitpunkt", message = "Startdatum muss vor dem Enddatum liegen.")
public record ZeitraumDto(@NotNull LocalDate startZeitpunkt,
        @NotNull LocalDate endZeitpunkt) {
}
