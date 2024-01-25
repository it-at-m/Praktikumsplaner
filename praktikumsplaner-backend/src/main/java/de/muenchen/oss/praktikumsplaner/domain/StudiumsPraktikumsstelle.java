package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.StudiumsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.converter.StudiensemesterConverter;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
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
@StudiumsAnnotation(studiengangGetMethod = "getStudiengang", message = "Keine korrekte Studiengang")
public class StudiumsPraktikumsstelle extends BasePraktikumsstelle {

    @NotNull
    private String programmierkenntnisse;

    @NotNull
    @Convert(converter = StudiensemesterConverter.class)
    private Set<Studiensemester> studiensemester;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Studiengang studiengang;

}
