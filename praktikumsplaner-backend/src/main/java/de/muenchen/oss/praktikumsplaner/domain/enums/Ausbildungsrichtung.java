package de.muenchen.oss.praktikumsplaner.domain.enums;

import lombok.Getter;

@Getter
public enum Ausbildungsrichtung {
    FISI("Fachinformatiker*in für Systemintegration"),
    QE2("Verwaltungswirt*in"),
    KFB("Kaufleute für Büromanagement"),
    VFAK("Verwaltungsfachangestellte*r kommunal");

    private final String longName;

    Ausbildungsrichtung(final String longName) {
        this.longName = longName;
    }

    public static Ausbildungsrichtung fromBildungsrichtung(final Bildungsrichtung bildungsrichtung) {
        if (bildungsrichtung.getArt() != Bildungsrichtung.Art.AUSBILDUNG) {
            throw new IllegalArgumentException("Bildungsrichtung " + bildungsrichtung + " ist keine Ausbildungsrichtung");
        }
        return Ausbildungsrichtung.valueOf(bildungsrichtung.name());
    }
}
