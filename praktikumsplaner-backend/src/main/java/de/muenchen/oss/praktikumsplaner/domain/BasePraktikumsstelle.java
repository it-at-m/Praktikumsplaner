package de.muenchen.oss.praktikumsplaner.domain;

import static java.sql.Types.VARCHAR;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class BasePraktikumsstelle extends BaseEntity {

    @NotNull
    @Size(max = 10, message = "Die Dienststelle darf {max} Zeichen lang sein")
    public String dienststelle;

    @NotNull
    @Size(max = 255, message = "Der örtliche Ausbilder darf nur {max} Zeichen lang sein")
    public String oertlicheAusbilder;

    @NotNull
    @Email
    @Size(max = 255, message = "Die Email darf nur {max} Zeichen lang sein")
    public String email;

    @NotNull
    @Size(max = 5000, message = "Die Tätigkeiten dürfen nur {max} Zeichen lang sein")
    public String taetigkeiten;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Dringlichkeit dringlichkeit;

    @Size(max = 255, message = "Die angeforderte NWK darf nur {max} Zeichen lang sein")
    public String namentlicheAnforderung;

    @Enumerated(EnumType.STRING)
    public Referat referat;

    @NotNull
    @JdbcTypeCode(VARCHAR)
    public UUID meldezeitraumID;
}
