package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Ausbilder;
import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbilderDto;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
            final boolean programmierkenntnisse,
            final boolean projektarbeit,
            final boolean minderjaehrigMoeglich,
            final UUID meldezeitraumId,
            final Nwk assignedNwk) {
        Praktikumsstelle praktikumsstelle = new Praktikumsstelle();
        praktikumsstelle.setId(UUID.randomUUID());
        praktikumsstelle.setDienststelle(dienststelle);
        praktikumsstelle.setAusbilder(new ArrayList<>(List.of(new Ausbilder(ausbilder, email, false, minderjaehrigMoeglich))));
        praktikumsstelle.setTaetigkeiten(taetigkeiten);
        praktikumsstelle.setWuensche(wuensche);
        praktikumsstelle.setDringlichkeit(dringlichkeit);
        praktikumsstelle.setRichtung(richtung);
        praktikumsstelle.setAusbildungsjahr(ausbildungsjahr == null ? null : new HashSet<>(ausbildungsjahr));
        praktikumsstelle.setStudiensemester(studiensemester == null ? null : new HashSet<>(studiensemester));
        praktikumsstelle.setProgrammierkenntnisse(programmierkenntnisse);
        praktikumsstelle.setProjektarbeit(projektarbeit);
        praktikumsstelle.setAssignedNwk(assignedNwk);
        praktikumsstelle.setMeldezeitraumID(meldezeitraumId);
        return praktikumsstelle;
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
            final boolean programmierkenntnisse,
            final boolean projektarbeit,
            final boolean minderjaehrigMoeglich,
            final UUID meldezeitraumId,
            final Nwk assignedNwk,
            final List<Ausbilder> additionalAusbilder) {
        final Praktikumsstelle praktikumsstelle = createPraktikumsstelleEntity(
                dienststelle, ausbilder, email, taetigkeiten, wuensche, dringlichkeit, richtung, ausbildungsjahr,
                studiensemester, programmierkenntnisse, projektarbeit, minderjaehrigMoeglich, meldezeitraumId, assignedNwk);
        final List<Ausbilder> ausbilderList = new ArrayList<>(praktikumsstelle.getAusbilder());
        ausbilderList.addAll(additionalAusbilder);
        praktikumsstelle.setAusbilder(ausbilderList);
        return praktikumsstelle;
    }

    public PraktikumsstelleDto createPraktikumsstelleDto(final Praktikumsstelle stelle) {
        return PraktikumsstelleDto.builder()
                .id(stelle.getId())
                .richtung(stelle.getRichtung())
                .dienststelle(stelle.getDienststelle())
                .taetigkeiten(stelle.getTaetigkeiten())
                .dringlichkeit(stelle.getDringlichkeit())
                .namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .projektarbeit(stelle.isProjektarbeit())
                .planstelleVorhanden(stelle.isPlanstelleVorhanden())
                .programmierkenntnisse(stelle.isProgrammierkenntnisse())
                .wuensche(stelle.getWuensche())
                .ausbildungsjahr(stelle.getAusbildungsjahr())
                .studiensemester(stelle.getStudiensemester())
                .ausbilder(stelle.getAusbilder().stream()
                        .map(ausbilder -> new AusbilderDto(ausbilder.name(), ausbilder.email(),
                                ausbilder.erwFuehrungszeugnisVorhanden(), ausbilder.minderjaehrigMoeglich()))
                        .toList())
                .assignedNwk(stelle.getAssignedNwk() == null ? null : createNwkDto(stelle.getAssignedNwk()))
                .meldezeitraumID(stelle.getMeldezeitraumID())
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
