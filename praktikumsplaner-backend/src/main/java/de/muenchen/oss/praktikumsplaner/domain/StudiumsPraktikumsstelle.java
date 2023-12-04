package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.StudiumsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
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
@StudiumsAnnotation(studiengang = "getStudienart", message = "Keine korrekte Studienart")
public class StudiumsPraktikumsstelle extends BasePraktikumsstelle {

    @NotNull
    private boolean programmierkenntnisse;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Studiensemester studiensemester;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Studiengang studienart;

}
