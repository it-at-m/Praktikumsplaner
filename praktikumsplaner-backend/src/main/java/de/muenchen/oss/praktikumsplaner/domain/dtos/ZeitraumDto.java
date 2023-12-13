package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ZeitraumDto(@NotNull LocalDate startZeitpunkt,
                          @NotNull LocalDate endZeitpunkt) {
}
