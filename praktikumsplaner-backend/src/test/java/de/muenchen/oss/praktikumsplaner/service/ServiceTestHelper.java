package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class ServiceTestHelper {
    public Nwk createNwkEntity(final String vorname, final String nachname, final Studiengang studiengang, final Ausbildungsrichtung ausbildungsrichtung,
            final String jahrgang, final Set<DayOfWeek> vorlesungstage, final boolean isActive) {
        Nwk newNwk = new Nwk();
        newNwk.setId(UUID.randomUUID());
        newNwk.setVorname(vorname);
        newNwk.setNachname(nachname);
        newNwk.setStudiengang(studiengang);
        newNwk.setAusbildungsrichtung(ausbildungsrichtung);
        newNwk.setJahrgang(jahrgang);
        newNwk.setVorlesungstage(vorlesungstage);
        newNwk.setActive(isActive);
        return newNwk;
    }

    public MeldezeitraumDto createMeldezeitraumDto(LocalDate start, LocalDate end, String name) {
        return MeldezeitraumDto.builder()
                .id(UUID.randomUUID())
                .zeitraumName(name)
                .zeitraum(ZeitraumDto.builder().startZeitpunkt(start).endZeitpunkt(end).build()).build();
    }

    public AusbildungsPraktikumsstelle createAusbildungsPraktikumsstelleEntity(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten,
            final Dringlichkeit dringlichkeit, final Referat referat, final Ausbildungsjahr semester,
            final Ausbildungsrichtung ausbildungsrichtung, final boolean projektarbeit, final UUID meldezeitraumId, final Nwk assignedNwk) {
        AusbildungsPraktikumsstelle newAusbildungsPraktikumsstelle = new AusbildungsPraktikumsstelle();
        newAusbildungsPraktikumsstelle.setId(UUID.randomUUID());
        newAusbildungsPraktikumsstelle.setDienststelle(dienststelle);
        newAusbildungsPraktikumsstelle.setOertlicheAusbilder(ausbilder);
        newAusbildungsPraktikumsstelle.setEmail(email);
        newAusbildungsPraktikumsstelle.setTaetigkeiten(taetigkeiten);
        newAusbildungsPraktikumsstelle.setDringlichkeit(dringlichkeit);
        newAusbildungsPraktikumsstelle.setReferat(referat);
        newAusbildungsPraktikumsstelle.setAusbildungsjahr(semester);
        newAusbildungsPraktikumsstelle.setAusbildungsrichtung(ausbildungsrichtung);
        newAusbildungsPraktikumsstelle.setProjektarbeit(projektarbeit);
        newAusbildungsPraktikumsstelle.setAssignedNwk(assignedNwk);
        return newAusbildungsPraktikumsstelle;
    }

    public StudiumsPraktikumsstelle createStudiumsPraktikumsstelleEntity(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten,
            final Dringlichkeit dringlichkeit, final Referat referat, final Studiensemester semester,
            final Studiengang studiengang, final String programmierkenntnisse, final UUID meldezeitraumId, final Nwk assignedNwk) {
        StudiumsPraktikumsstelle newStudiumsPraktikumsstelle = new StudiumsPraktikumsstelle();
        newStudiumsPraktikumsstelle.setId(UUID.randomUUID());
        newStudiumsPraktikumsstelle.setDienststelle(dienststelle);
        newStudiumsPraktikumsstelle.setOertlicheAusbilder(ausbilder);
        newStudiumsPraktikumsstelle.setEmail(email);
        newStudiumsPraktikumsstelle.setTaetigkeiten(taetigkeiten);
        newStudiumsPraktikumsstelle.setDringlichkeit(dringlichkeit);
        newStudiumsPraktikumsstelle.setReferat(referat);
        newStudiumsPraktikumsstelle.setStudiensemester(semester);
        newStudiumsPraktikumsstelle.setProgrammierkenntnisse(programmierkenntnisse);
        newStudiumsPraktikumsstelle.setStudiengang(studiengang);
        newStudiumsPraktikumsstelle.setAssignedNwk(assignedNwk);
        return newStudiumsPraktikumsstelle;
    }

    public AusbildungsPraktikumsstelleDto createPraktikumsstelleDto(AusbildungsPraktikumsstelle stelle) {
        return AusbildungsPraktikumsstelleDto.builder()
                .dienststelle(stelle.getDienststelle()).oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail()).taetigkeiten(stelle.getTaetigkeiten())
                .dringlichkeit(stelle.getDringlichkeit()).namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .referat(stelle.getReferat()).projektarbeit(stelle.isProjektarbeit())
                .ausbildungsjahr(stelle.getAusbildungsjahr())
                .ausbildungsrichtung(stelle.getAusbildungsrichtung()).build();
    }

    public StudiumsPraktikumsstelleDto createPraktikumsstelleDto(StudiumsPraktikumsstelle stelle) {
        return StudiumsPraktikumsstelleDto.builder()
                .dienststelle(stelle.getDienststelle()).oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail()).taetigkeiten(stelle.getTaetigkeiten())
                .dringlichkeit(stelle.getDringlichkeit()).namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .referat(stelle.getReferat()).programmierkenntnisse(stelle.getProgrammierkenntnisse())
                .studiensemester(stelle.getStudiensemester()).studiengang(stelle.getStudiengang()).build();
    }

    public Meldezeitraum createMeldezeitraum(LocalDate start, LocalDate end, String name) {
        Meldezeitraum meldezeitraum = new Meldezeitraum();
        meldezeitraum.setId(UUID.randomUUID());
        meldezeitraum.setStartZeitpunkt(start);
        meldezeitraum.setEndZeitpunkt(end);
        meldezeitraum.setZeitraumName(name);
        return meldezeitraum;
    }
}
