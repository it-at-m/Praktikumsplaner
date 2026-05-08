package de.muenchen.oss.praktikumsplaner.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bildungsrichtung {
    // Ausbildung
    FISI(Art.AUSBILDUNG, "Fachinformatiker*in für Systemintegration"),
    QE2(Art.AUSBILDUNG, "Verwaltungswirt*in"),
    KFB(Art.AUSBILDUNG, "Kaufleute für Büromanagement"),
    VFAK(Art.AUSBILDUNG, "Verwaltungsfachangestellte*r kommunal"),
    // Studium
    BSC(Art.STUDIUM, "Bachelor of Science - Informatik"),
    BWI(Art.STUDIUM, "Wirtschaftsinformatik kommunal"),
    VI(Art.STUDIUM, "Diplomverwaltungsinformatiker*in"),
    LLB(Art.STUDIUM, "Bachelor of Laws"),
    PUMA(Art.STUDIUM, "Public Management/Public Sector"),
    QE3(Art.STUDIUM, "Diplomverwaltungswirt*in");

    private final Art art;
    private final String longName;

    public enum Art {
        AUSBILDUNG,
        STUDIUM
    }
}
