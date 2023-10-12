package de.muenchen.oss.praktikumsplaner.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
/*
    * NWK is short for Nachwuchskraft (young talent)
 */
public class NWK extends BaseEntity {

    @NotNull(message = "Der Vorname ist erforderlich")
    // TO-DO: Maximales Char Limit vorübergehend auf 255 limitiert
    @Size(min = 2, max = 255, message = "Der Vorname darf nur zwischen {min} und {max} Zeichen lang sein")
    @Column(name = "vorname")
    public String vorname;

    @NotNull(message = "Der Nachname ist erforderlich")
    // TO-DO: Maximales Char Limit vorübergehend auf 255 limitiert
    @Size(min = 2, max = 255, message = "Der Nachname darf nur zwischen {min} und {max} Zeichen lang sein")
    @Column(name = "nachname")
    public String nachname;

    @NotNull(message = "Der Studiengang ist erforderlich")
    @Column(name = "studiengang")
    public Studiengang studiengang;

    @NotNull(message = "Der Jahrgang ist erforderlich")
    @Pattern(regexp = "\\d\\d/\\d\\d")
    @Column(name = "jahrgang")
    public String jahrgang;

    @Column(name = "vorlesungstage")
    // Matched Tag + Tag + ... bis maximal 6 Tage dastehen
    @Pattern(regexp = "(Mo|Di|Mi|Do|Fr|Sa)( \\+ (Mo|Di|Mi|Do|Fr|Sa)){0,5}")
    public String vorlesungstage;
}
