package de.muenchen.oss.praktikumsplaner.domain;

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
public class Praktikumsstelle extends BaseEntity{

    public String referat;

    @NotNull
    public String dienststelle;

    @NotNull
    public String oertlicheAusbiler;

    @NotNull
    public String email;

    @NotNull
    public String taetigkeiten;

    @NotNull
    public String projektarbeit;

    @NotNull
    public String programmierkenntnisse;

    @NotNull
    public String ausbildungsjahr;

    @NotNull
    public String studiensemester;

    @NotNull
    public String dringlichkeit;

    @NotNull
    public String ausbildungsrichtung;

    @NotNull
    public String studienart;

    public String namentlicheAnforderung;


}
