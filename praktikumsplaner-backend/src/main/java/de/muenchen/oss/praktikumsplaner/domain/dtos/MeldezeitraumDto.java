package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
public record MeldezeitraumDto(
        @NotNull UUID id,
        @Size(max = 255, message = "Name für Zeitraum darf nicht länger als {max} Zeichen sein.")
        @NotBlank
        String zeitraumName,
        ZeitraumDto zeitraum)
{}
