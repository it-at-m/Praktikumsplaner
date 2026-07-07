package de.muenchen.oss.praktikumsplaner.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bildungsrichtung {
    // Ausbildung
    FISI(RichtungsArt.AUSBILDUNG, "Fachinformatiker*in für Systemintegration"),
    QE2(RichtungsArt.AUSBILDUNG, "Verwaltungswirt*in"),
    KFB(RichtungsArt.AUSBILDUNG, "Kaufleute für Büromanagement"),
    VFAK(RichtungsArt.AUSBILDUNG, "Verwaltungsfachangestellte*r kommunal"),
    // Studium
    BSC(RichtungsArt.STUDIUM, "Bachelor of Science - Informatik"),
    BWI(RichtungsArt.STUDIUM, "Wirtschaftsinformatik kommunal"),
    VI(RichtungsArt.STUDIUM, "Diplomverwaltungsinformatiker*in"),
    LLB(RichtungsArt.STUDIUM, "Bachelor of Laws"),
    PUMA(RichtungsArt.STUDIUM, "Public Management/Public Sector"),
    QE3(RichtungsArt.STUDIUM, "Diplomverwaltungswirt*in");

    private final RichtungsArt art;
    private final String longName;
}
