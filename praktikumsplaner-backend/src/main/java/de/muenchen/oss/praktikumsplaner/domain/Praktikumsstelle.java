package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungOrStudium;
import jakarta.persistence.Entity;
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
@AusbildungOrStudium(
        projektarbeit = "getProjektarbeit", ausbildungsjahr = "getAusbildungsjahr", ausbildungsrichtung = "getAusbildungsrichtung", studienart = "getStudienart", studiensemester = "getStudiensemester", programmierkenntnisse = "getProgrammierkenntnisse", message = "Muss entweder Studiums- oder Ausbildungspraktikumsplatz sein"
)
public class Praktikumsstelle extends BaseEntity {

    @NotNull
    public String dienststelle;

    @NotNull
    public String oertlicheAusbiler;

    @NotNull
    public String email;

    @NotNull
    public String taetigkeiten;

    @NotNull
    public String dringlichkeit;

    public String namentlicheAnforderung;

    public String referat;

    public String projektarbeit;

    public String programmierkenntnisse;

    public String ausbildungsjahr;

    public String studiensemester;

    public String ausbildungsrichtung;

    public String studienart;

}
