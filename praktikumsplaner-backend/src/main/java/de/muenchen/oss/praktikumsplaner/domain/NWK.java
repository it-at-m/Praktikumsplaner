package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.domain.converter.DayOfWeekSetConverter;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
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
/*
 * NWK is short for Nachwuchskraft (young talent)
 */
public class NWK extends BaseEntity {

    @NotNull(message = "Der Vorname ist erforderlich")
    @Size(min = 2, max = 255, message = "Der Vorname darf nur zwischen {min} und {max} Zeichen lang sein")
    public String vorname;

    @NotNull(message = "Der Nachname ist erforderlich")
    @Size(min = 2, max = 255, message = "Der Nachname darf nur zwischen {min} und {max} Zeichen lang sein")
    public String nachname;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Der Studiengang ist erforderlich")
    public Studiengang studiengang;

    @NotNull(message = "Der Jahrgang ist erforderlich")
    @Pattern(regexp = "\\d\\d/\\d\\d")
    public String jahrgang;

    @Convert(converter = DayOfWeekSetConverter.class)
    public Set<DayOfWeek> vorlesungstage;

    @NotNull
    public boolean active;

    @PrePersist
    public void prePersist() {
        this.active = true;
    }
}
