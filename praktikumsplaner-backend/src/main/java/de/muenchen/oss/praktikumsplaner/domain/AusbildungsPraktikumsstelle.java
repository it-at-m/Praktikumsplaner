package de.muenchen.oss.praktikumsplaner.domain;

import static java.sql.Types.VARCHAR;

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
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@AusbildungsAnnotation(studiengang = "getAusbildungsrichtung", message = "Keine korrekte Ausbildungsrichtung")
public class AusbildungsPraktikumsstelle extends BasePraktikumsstelle {

    @NotNull
    public boolean projektarbeit;

    @NotNull
    public boolean planstelleVorhanden;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Ausbildungsjahr ausbildungsjahr;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Studiengang ausbildungsrichtung;
}
