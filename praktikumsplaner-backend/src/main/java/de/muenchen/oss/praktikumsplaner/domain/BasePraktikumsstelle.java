package de.muenchen.oss.praktikumsplaner.domain;

import static java.sql.Types.VARCHAR;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@EqualsAndHashCode(callSuper = true)
public abstract class BasePraktikumsstelle extends BaseEntity {

    @NotNull
    @Size(max = 10, message = "Die Dienststelle darf {max} Zeichen lang sein")
    private String dienststelle;

    @NotNull
    @Size(max = 255, message = "Der örtliche Ausbilder darf nur {max} Zeichen lang sein")
    private String oertlicheAusbilder;

    @NotNull
    @Email
    @Size(max = 255, message = "Die Email darf nur {max} Zeichen lang sein")
    private String email;

    @NotNull
    @Size(max = 5000, message = "Die Tätigkeiten dürfen nur {max} Zeichen lang sein")
    private String taetigkeiten;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Dringlichkeit dringlichkeit;

    @Size(max = 255, message = "Die angeforderte Nachwuchskraft darf nur {max} Zeichen lang sein")
    private String namentlicheAnforderung;

    @Enumerated(EnumType.STRING)
    private Referat referat;

    @NotNull
    @JdbcTypeCode(VARCHAR)
    private UUID meldezeitraumID;

    @ManyToOne
    @JoinColumn(name = "assignedNwk")
    private Nwk assignedNwk;

    private boolean planstelleVorhanden;
}
