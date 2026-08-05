package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ServiceTestHelper {
    public Nwk createNwkEntity(final String vorname, final String nachname, final Bildungsrichtung richtung,
            final String jahrgang, final Set<DayOfWeek> vorlesungstage, final boolean isActive) {
        Nwk newNwk = new Nwk();
        newNwk.setId(UUID.randomUUID());
        newNwk.setVorname(vorname);
        newNwk.setNachname(nachname);
        newNwk.setRichtung(richtung);
        newNwk.setJahrgang(jahrgang);
        newNwk.setVorlesungstage(vorlesungstage);
        newNwk.setActive(isActive);
        return newNwk;
    }

    public NwkDto createNwkDto(final Nwk nwk) {
        return NwkDto.builder()
                .id(nwk.getId())
                .vorname(nwk.getVorname())
                .nachname(nwk.getNachname())
                .richtung(nwk.getRichtung())
                .jahrgang(nwk.getJahrgang())
                .vorlesungstage(nwk.getVorlesungstage())
                .active(nwk.isActive())
                .build();
    }

    public MeldezeitraumDto createMeldezeitraumDto(final LocalDate start, final LocalDate end, final String name) {
        return MeldezeitraumDto.builder()
                .id(UUID.randomUUID())
                .zeitraumName(name)
                .zeitraum(ZeitraumDto.builder().startZeitpunkt(start).endZeitpunkt(end).build()).build();
    }

    public Praktikumsstelle createPraktikumsstelleEntity(
            final String dienststelle,
            final String ausbilder,
            final String email,
            final String taetigkeiten,
            final String wuensche,
            final Dringlichkeit dringlichkeit,
            final Bildungsrichtung richtung,
            final Set<Ausbildungsjahr> ausbildungsjahr,
            final Set<Studiensemester> studiensemester,
            final String programmierkenntnisse,
            final boolean projektarbeit,
            final boolean minderjaehrigMoeglich,
            final UUID meldezeitraumId,
            final Nwk assignedNwk) {
        Praktikumsstelle praktikumsstelle = new Praktikumsstelle();
        praktikumsstelle.setId(UUID.randomUUID());
        praktikumsstelle.setDienststelle(dienststelle);
        praktikumsstelle.setOertlicheAusbilder(ausbilder);
        praktikumsstelle.setEmail(email);
        praktikumsstelle.setTaetigkeiten(taetigkeiten);
        praktikumsstelle.setWuensche(wuensche);
        praktikumsstelle.setDringlichkeit(dringlichkeit);
        praktikumsstelle.setRichtung(richtung);
        praktikumsstelle.setAusbildungsjahr(ausbildungsjahr == null ? null : new HashSet<>(ausbildungsjahr));
        praktikumsstelle.setStudiensemester(studiensemester == null ? null : new HashSet<>(studiensemester));
        praktikumsstelle.setProgrammierkenntnisse(programmierkenntnisse);
        praktikumsstelle.setProjektarbeit(projektarbeit);
        praktikumsstelle.setAssignedNwk(assignedNwk);
        praktikumsstelle.setMinderjaehrigMoeglich(minderjaehrigMoeglich);
        praktikumsstelle.setMeldezeitraumID(meldezeitraumId);
        return praktikumsstelle;
    }

    public PraktikumsstelleDto createPraktikumsstelleDto(final Praktikumsstelle stelle) {
        return PraktikumsstelleDto.builder()
                .id(stelle.getId())
                .dienststelle(stelle.getDienststelle())
                .oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail())
                .taetigkeiten(stelle.getTaetigkeiten())
                .wuensche(stelle.getWuensche())
                .dringlichkeit(stelle.getDringlichkeit())
                .namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .programmierkenntnisse(stelle.getProgrammierkenntnisse())
                .projektarbeit(stelle.isProjektarbeit())
                .ausbildungsjahr(stelle.getAusbildungsjahr())
                .studiensemester(stelle.getStudiensemester())
                .richtung(stelle.getRichtung())
                .assignedNwk(stelle.getAssignedNwk() == null ? null : createNwkDto(stelle.getAssignedNwk()))
                .planstelleVorhanden(stelle.isPlanstelleVorhanden())
                .minderjaehrigMoeglich(stelle.isMinderjaehrigMoeglich())
                .meldezeitraumID(stelle.getMeldezeitraumID())
                .erwFuehrungszeugnisVorhanden(stelle.isErwFuehrungszeugnisVorhanden())
                .build();
    }

    public Meldezeitraum createMeldezeitraum(final LocalDate start, final LocalDate end, final String name) {
        Meldezeitraum meldezeitraum = new Meldezeitraum();
        meldezeitraum.setId(UUID.randomUUID());
        meldezeitraum.setStartZeitpunkt(start);
        meldezeitraum.setEndZeitpunkt(end);
        meldezeitraum.setZeitraumName(name);
        return meldezeitraum;
    }
}
