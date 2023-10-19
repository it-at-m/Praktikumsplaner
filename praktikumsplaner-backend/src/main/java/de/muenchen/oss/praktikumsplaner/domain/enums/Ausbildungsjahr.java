package de.muenchen.oss.praktikumsplaner.domain.enums;

public enum Ausbildungsjahr {
    JAHR1("1. Jahr"), JAHR2("2. Jahr"),
    JAHR3("3. Jahr");
    private final String bezeichnung;
    Ausbildungsjahr(final String bezeichnung){
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

}
