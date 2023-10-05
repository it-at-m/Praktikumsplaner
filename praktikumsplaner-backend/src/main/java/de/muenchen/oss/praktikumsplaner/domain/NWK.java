package de.muenchen.oss.praktikumsplaner.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@NoArgsConstructor
public class NWK extends BaseEntity {

    @Column(name = "vorname")
    public String vorname;

    @Column(name = "nachname")
    public String nachname;

    @Column(name = "studiengang")
    public String studiengang;

    @Column(name = "jahrgang")
    public String jahrgang;

    /*
     * @Column(name = "vorlesungstage")
     * public String vorlesungstage;
     */
}
