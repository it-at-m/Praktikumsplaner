package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateMeldezeitraumDto(
        @Size(max = 255, message = "Name für Zeitraum darf nicht länger als {max} Zeichen sein.") @NotBlank String zeitraumName,
        ZeitraumDto zeitraum) {
}
