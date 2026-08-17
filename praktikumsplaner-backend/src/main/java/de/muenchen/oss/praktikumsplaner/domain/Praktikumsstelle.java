package de.muenchen.oss.praktikumsplaner.domain;

import static java.sql.Types.VARCHAR;

import de.muenchen.oss.praktikumsplaner.domain.converter.AusbildungsjahrConverter;
import de.muenchen.oss.praktikumsplaner.domain.converter.StudiensemesterConverter;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
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
@SuppressWarnings({ "PMD.MissingSerialVersionUID", "PMD.TooManyFields" })
public class Praktikumsstelle extends BaseEntity {
    @NotNull @Enumerated(EnumType.STRING)
    private Bildungsrichtung richtung;
    @NotNull @Size(max = 20, message = "Die Dienststelle darf {max} Zeichen lang sein") @Pattern(regexp = "^[A-Z]{3,4}-[A-Za-z\\d-]+$") private String dienststelle;
    @NotNull @Size(max = 5000, message = "Die Tätigkeiten dürfen nur {max} Zeichen lang sein") private String taetigkeiten;
    @NotNull @Enumerated(EnumType.STRING)
    private Dringlichkeit dringlichkeit;
    @Size(max = 255, message = "Die angeforderte Nachwuchskraft darf nur {max} Zeichen lang sein") private String namentlicheAnforderung;
    private boolean planstelleVorhanden;
    private boolean projektarbeit;

    @Size(max = 5000, message = "Die Wünsche dürfen nur {max} Zeichen lang sein") private String wuensche;
    private String programmierkenntnisse;
    @Convert(converter = AusbildungsjahrConverter.class)
    private Set<Ausbildungsjahr> ausbildungsjahr;
    @Convert(converter = StudiensemesterConverter.class)
    private Set<Studiensemester> studiensemester;

    @NotNull @Size(max = 255, message = "Der örtliche Ausbilder darf nur {max} Zeichen lang sein") private String oertlicheAusbilder;
    @NotNull @Email @Size(max = 255, message = "Die Email darf nur {max} Zeichen lang sein") private String email;
    private boolean erwFuehrungszeugnisVorhanden;
    private boolean minderjaehrigMoeglich;

    @NotNull @JdbcTypeCode(VARCHAR)
    private UUID meldezeitraumID;
    @ManyToOne
    @JoinColumn(name = "assignedNwk")
    private Nwk assignedNwk;
}
