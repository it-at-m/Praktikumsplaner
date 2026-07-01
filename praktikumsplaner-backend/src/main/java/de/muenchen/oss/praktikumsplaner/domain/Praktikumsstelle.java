package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.RichtungValid;
import de.muenchen.oss.praktikumsplaner.domain.converter.AusbildungsjahrConverter;
import de.muenchen.oss.praktikumsplaner.domain.converter.StudiensemesterConverter;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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
@RichtungValid
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class Praktikumsstelle extends BasePraktikumsstelle {

    @NotNull @Enumerated(EnumType.STRING)
    private Bildungsrichtung richtung; // unified field for former Studiengang/Ausbildungsrichtung

    // STUDIUM-only: required when art == STUDIUM
    private String programmierkenntnisse;

    // STUDIUM-only: required when art == STUDIUM
    @Convert(converter = StudiensemesterConverter.class)
    private Set<Studiensemester> studiensemester;

    // AUSBILDUNG-only: required when art == AUSBILDUNG
    private boolean projektarbeit;

    // AUSBILDUNG-only: required when art == AUSBILDUNG
    @Convert(converter = AusbildungsjahrConverter.class)
    private Set<Ausbildungsjahr> ausbildungsjahr;

    // AUSBILDUNG-only: required when art == AUSBILDUNG
    private Boolean minderjaehrigMoeglich;
}
