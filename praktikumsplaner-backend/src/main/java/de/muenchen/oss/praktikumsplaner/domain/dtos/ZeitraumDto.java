package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ZeitraumDto(@NotNull LocalDate startZeitpunkt,
                          @NotNull LocalDate endZeitpunkt) {
}
