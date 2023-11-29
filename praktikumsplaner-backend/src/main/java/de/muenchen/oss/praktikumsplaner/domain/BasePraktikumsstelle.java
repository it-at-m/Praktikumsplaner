package de.muenchen.oss.praktikumsplaner.domain;

import static java.sql.Types.VARCHAR;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    private String dienststelle;

    @NotNull
    private String oertlicheAusbilder;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String taetigkeiten;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Dringlichkeit dringlichkeit;

    private String namentlicheAnforderung;

    @Enumerated(EnumType.STRING)
    private Referat referat;

    @NotNull
    @JdbcTypeCode(VARCHAR)
    private UUID meldezeitraumID;

    @NotNull
    private boolean planstelleVorhanden;
}
