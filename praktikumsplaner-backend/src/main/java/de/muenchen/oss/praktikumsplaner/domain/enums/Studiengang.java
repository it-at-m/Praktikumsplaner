package de.muenchen.oss.praktikumsplaner.domain.enums;

import lombok.Getter;

@Getter
public enum Studiengang {
    BSC("Bachelor of Science - Informatik"),
    BWI("Wirtschaftsinformatik kommunal"),
    VI("Diplomverwaltungsinformatiker*in"),
    LLB("Bachelor of Laws"),
    PUMA("Public Management/Public Sector"),
    QE3("Diplomverwaltungswirt*in");

    private final String longName;

    Studiengang(final String longName) {
        this.longName = longName;
    }

}
