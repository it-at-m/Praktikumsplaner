package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.domain.converter.AusbildungsjahrConverter;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
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
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class AusbildungsPraktikumsstelle extends BasePraktikumsstelle {

    private boolean projektarbeit;

    private String programmierkenntnisse;

    @NotNull @Convert(converter = AusbildungsjahrConverter.class)
    private Set<Ausbildungsjahr> ausbildungsjahr;

    @NotNull @Enumerated(EnumType.STRING)
    private Ausbildungsrichtung ausbildungsrichtung;

    private boolean minderjaehrigMoeglich;
}
