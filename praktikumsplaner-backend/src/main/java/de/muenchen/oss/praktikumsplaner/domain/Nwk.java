package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.StudiengangOrAusbildungsrichtungConstraint;
import de.muenchen.oss.praktikumsplaner.domain.converter.DayOfWeekSetConverter;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.DayOfWeek;
import java.util.Set;
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
@StudiengangOrAusbildungsrichtungConstraint(studiengang = "getStudiengang", ausbildungsrichtung = "getAusbildungsrichtung")
/*
 * Nwk is short for Nachwuchskraft (young talent)
 */
public class Nwk extends BaseEntity {

    @NotNull(message = "Der Vorname ist erforderlich")
    @Size(min = 2, max = 255, message = "Der Vorname darf nur zwischen {min} und {max} Zeichen lang sein")
    private String vorname;

    @NotNull(message = "Der Nachname ist erforderlich")
    @Size(min = 2, max = 255, message = "Der Nachname darf nur zwischen {min} und {max} Zeichen lang sein")
    private String nachname;

    @Enumerated(EnumType.STRING)
    private Studiengang studiengang;

    @Enumerated(EnumType.STRING)
    private Ausbildungsrichtung ausbildungsrichtung;

    @NotNull(message = "Der Jahrgang ist erforderlich")
    @Pattern(regexp = "\\d\\d/\\d\\d")
    private String jahrgang;

    @Convert(converter = DayOfWeekSetConverter.class)
    private Set<DayOfWeek> vorlesungstage;

    private boolean active;
}
