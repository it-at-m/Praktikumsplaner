package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
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

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@AusbildungsAnnotation(studiengang = "getAusbildungsrichtung", message = "Keine korrekte Ausbildungsrichtung")
public class AusbildungsPraktikumsstelle extends BaseEntity {

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
    public boolean projektarbeit;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Ausbildungsjahr ausbildungsjahr;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Studiengang ausbildungsrichtung;

    @NotNull
    public UUID meldezeitraumID;
}
