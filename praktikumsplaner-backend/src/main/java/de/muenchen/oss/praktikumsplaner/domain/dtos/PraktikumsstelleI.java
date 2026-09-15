package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import java.util.List;
import java.util.Set;

public interface PraktikumsstelleI {
    Bildungsrichtung richtung();

    String dienststelle();

    String taetigkeiten();

    Dringlichkeit dringlichkeit();

    String namentlicheAnforderung();

    boolean projektarbeit();

    boolean planstelleVorhanden();

    Boolean programmierkenntnisse();

    String wuensche();

    Set<Ausbildungsjahr> ausbildungsjahr();

    Set<Studiensemester> studiensemester();

    List<AusbilderDto> ausbilder();

}
