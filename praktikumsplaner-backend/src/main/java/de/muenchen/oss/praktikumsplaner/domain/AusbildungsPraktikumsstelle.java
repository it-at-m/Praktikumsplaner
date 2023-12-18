package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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
@AusbildungsAnnotation(studiengang = "getAusbildungsrichtung", message = "Keine korrekte Ausbildungsrichtung")
public class AusbildungsPraktikumsstelle extends BasePraktikumsstelle {

    private boolean projektarbeit;

    private String programmierkenntnisse;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Ausbildungsjahr ausbildungsjahr;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Studiengang ausbildungsrichtung;
}
