package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.StudiumsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
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
public class StudiumsPraktikumsstelle extends BaseEntity {

    @NotNull
    public String dienststelle;

    @NotNull
    public String oertlicheAusbilder;

    @NotNull
    @Email
    public String email;

    @NotNull
    public String taetigkeiten;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Dringlichkeit dringlichkeit;

    public String namentlicheAnforderung;

    @Enumerated(EnumType.STRING)
    public Referat referat;

    @NotNull
    public boolean programmierkenntnisse;

    @NotNull
    public boolean planstelleVorhanden;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Studiensemester studiensemester;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Studiengang studienart;

}
