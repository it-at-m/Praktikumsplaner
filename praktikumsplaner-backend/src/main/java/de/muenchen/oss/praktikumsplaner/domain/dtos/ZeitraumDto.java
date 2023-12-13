package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ZeitraumDto(@NotNull LocalDate startZeitpunkt,
                          @NotNull LocalDate endZeitpunkt) {
}
