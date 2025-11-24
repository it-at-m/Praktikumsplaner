package de.muenchen.oss.praktikumsplaner.domain.enums;

public enum Ausbildungsrichtung {
    FISI("Fachinformatiker*in für Systemintegration"),
    QE2("Verwaltungswirt*in"),
    KFB("Kaufleute für Büromanagement"),
    VFAK("Verwaltungsfachangestellte*r kommunal");

    private final String longName;

    Ausbildungsrichtung(String longName) {
        this.longName = longName;
    }

    public String getLongName() {
        return longName;
    }
}
