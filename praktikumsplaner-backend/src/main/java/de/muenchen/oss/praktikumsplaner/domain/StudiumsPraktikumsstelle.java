package de.muenchen.oss.praktikumsplaner.domain;

import static java.sql.Types.VARCHAR;

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
@StudiumsAnnotation(studiengang = "getStudienart", message = "Keine korrekte Studienart")
public class StudiumsPraktikumsstelle extends BasePraktikumsstelle {

    @NotNull
    public boolean programmierkenntnisse;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Studiensemester studiensemester;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Studiengang studienart;

    @NotNull
    @JdbcTypeCode(VARCHAR)
    public UUID meldezeitraumID;
}
