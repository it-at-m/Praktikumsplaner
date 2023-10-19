package de.muenchen.oss.praktikumsplaner.domain.enums;

public enum Studiensemester {
    SEMESTER1("1. Semester"), SEMESTER2("2. Semester"),
    SEMESTER3("3. Semester"), SEMESTER4("4. Semester"),
    SEMESTER5("5. Semester"), SEMESTER6("6. Semester");
    private final String bezeichnung;
    Studiensemester(final String bezeichnung){
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}
