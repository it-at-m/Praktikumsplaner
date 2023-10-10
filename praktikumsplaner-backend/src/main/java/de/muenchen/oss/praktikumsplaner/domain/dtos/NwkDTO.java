package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NwkDTO {
    private UUID id;

    @NotNull(message = "Der Vorname ist erforderlich")
    // TO-DO: Maximales Char Limit vorübergehend auf 255 limitiert
    @Size(min = 2, max = 255, message = "Der Vorname darf nur zwischen {min} und {max} Zeichen lang sein")
    public String vorname;

    @NotNull(message = "Der Nachname ist erforderlich")
    // TO-DO: Maximales Char Limit vorübergehend auf 255 limitiert
    @Size(min = 2, max = 255, message = "Der Nachname darf nur zwischen {min} und {max} Zeichen lang sein")
    public String nachname;

    @NotNull(message = "Der Studiengang ist erforderlich")
    @Pattern(regexp = "BWI|BSC|VI|FISI")
    public String studiengang;

    @NotNull(message = "Der Jahrgang ist erforderlich")
    @Pattern(regexp = "\\d\\d/\\d\\d")
    public String jahrgang;

    // Matched Tag + Tag + ... bis maximal 6 Tage dastehen
    @Pattern(regexp = "(Mo|Di|Mi|Do|Fr|Sa)( \\+ (Mo|Di|Mi|Do|Fr|Sa)){0,5}")
    public String vorlesungstage;
}
