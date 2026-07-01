package de.muenchen.oss.praktikumsplaner.service;

// removed legacy Ausbildungs/StudiumsPraktikumsstelle imports after unification
import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleViewDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
        if (nwk == null) {
            return null;
        }
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

    public MeldezeitraumDto createMeldezeitraumDto(LocalDate start, LocalDate end, String name) {
        return MeldezeitraumDto.builder()
                .id(UUID.randomUUID())
                .zeitraumName(name)
                .zeitraum(ZeitraumDto.builder().startZeitpunkt(start).endZeitpunkt(end).build()).build();
    }

    public Praktikumsstelle createAusbildungsPraktikumsstelleEntity(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten, final String wuensche,
            final Dringlichkeit dringlichkeit, final Set<Ausbildungsjahr> ausbildungsjahr, final Bildungsrichtung bildungsrichtung,
            final boolean projektarbeit, final boolean minderjaehrigMoeglich, final UUID meldezeitraumId, final Nwk assignedNwk) {
        Praktikumsstelle p = new Praktikumsstelle();
        p.setId(UUID.randomUUID());
        p.setDienststelle(dienststelle);
        p.setOertlicheAusbilder(ausbilder);
        p.setEmail(email);
        p.setTaetigkeiten(taetigkeiten);
        p.setWuensche(wuensche);
        p.setDringlichkeit(dringlichkeit);
        p.setAusbildungsjahr(ausbildungsjahr);
        p.setRichtung(Bildungsrichtung.valueOf(bildungsrichtung.name()));
        p.setProjektarbeit(projektarbeit);
        p.setAssignedNwk(assignedNwk);
        p.setMinderjaehrigMoeglich(minderjaehrigMoeglich);
        p.setMeldezeitraumID(meldezeitraumId);
        return p;
    }

    public Praktikumsstelle createStudiumsPraktikumsstelleEntity(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten, final String wuensche,
            final Dringlichkeit dringlichkeit, final Set<Studiensemester> semester,
            final Bildungsrichtung studiengang, final String programmierkenntnisse, final UUID meldezeitraumId, final Nwk assignedNwk) {
        Praktikumsstelle p = new Praktikumsstelle();
        p.setId(UUID.randomUUID());
        p.setDienststelle(dienststelle);
        p.setOertlicheAusbilder(ausbilder);
        p.setEmail(email);
        p.setTaetigkeiten(taetigkeiten);
        p.setWuensche(wuensche);
        p.setDringlichkeit(dringlichkeit);
        p.setStudiensemester(semester);
        p.setProgrammierkenntnisse(programmierkenntnisse);
        p.setRichtung(Bildungsrichtung.valueOf(studiengang.name()));
        p.setAssignedNwk(assignedNwk);
        p.setMeldezeitraumID(meldezeitraumId);
        return p;
    }

    public PraktikumsstelleViewDto createPraktikumsstelleDto(Praktikumsstelle stelle) {
        return PraktikumsstelleViewDto.builder()
                .id(stelle.getId())
                .dienststelle(stelle.getDienststelle())
                .oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail())
                .taetigkeiten(stelle.getTaetigkeiten())
                .wuensche(stelle.getWuensche())
                .dringlichkeit(stelle.getDringlichkeit())
                .namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .programmierkenntnisse(stelle.getProgrammierkenntnisse())
                .studiensemester(stelle.getStudiensemester())
                .ausbildungsjahr(stelle.getAusbildungsjahr())
                .richtung(stelle.getRichtung())
                .richtungLongName(stelle.getRichtung().getLongName())
                .art(stelle.getRichtung().getArt())
                .assignedNwk(createNwkDto(stelle.getAssignedNwk()))
                .planstelleVorhanden(stelle.isPlanstelleVorhanden())
                .minderjaehrigMoeglich(stelle.getMinderjaehrigMoeglich())
                .projektarbeit(stelle.isProjektarbeit())
                .meldezeitraumID(stelle.getMeldezeitraumID())
                .build();
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
