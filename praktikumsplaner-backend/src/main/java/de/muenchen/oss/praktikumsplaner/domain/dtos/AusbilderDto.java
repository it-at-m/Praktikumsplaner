package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AusbilderDto(
        @NotBlank @Size(max = 255, message = "Der örtliche Ausbilder darf nur {max} Zeichen lang sein") String name,
        @NotBlank @Email @Size(max = 255, message = "Die Email darf nur {max} Zeichen lang sein") String email,
        boolean erwFuehrungszeugnisVorhanden,
        boolean minderjaehrigMoeglich) {
}
