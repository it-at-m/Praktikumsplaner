package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class NwkDTO {

    @NotNull(message = "Der Vorname ist erforderlich")
    // TO-DO: Maximales Char Limit noch nicht festgelegt
    @Size(max = 255, message = "Der Vorname darf nur {max} Zeichen lang sein")
    public String vorname;

    @NotNull(message = "Der Nachname ist erforderlich")
    // TO-DO: Maximales Char Limit noch nicht festgelegt
    @Size(max = 255, message = "Der Nachname darf nur {max} Zeichen lang sein")
    public String nachname;

    @NotNull(message = "Der Studiengang ist erforderlich")
    @Pattern(regexp = "BWI|BSc|VI|FISI")
    public String studiengang;

    @NotNull(message = "Der Jahrgang ist erforderlich")
    @Pattern(regexp = "\\d\\d/\\d\\d")
    public String jahrgang;

    /*
     * // Teil der Vorlage, aber nicht in den Anforderungen beschrieben
     *
     * @Pattern(regexp = "^(Mo|Di|Mi|Do|Fr|Sa)( \\+ (Mo|Di|Mi|Do|Fr|Sa)){1,5}")
     * // Matched Tag + Tag + ... bis maximal 6 Tage dastehen
     * public String vorlesungstage;
     */
}
