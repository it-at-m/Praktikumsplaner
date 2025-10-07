package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record MeldezeitraumDto(
        @NotNull UUID id,
        @Size(max = 255, message = "Name für Zeitraum darf nicht länger als {max} Zeichen sein.") @NotBlank String zeitraumName,
        ZeitraumDto zeitraum) {
}
