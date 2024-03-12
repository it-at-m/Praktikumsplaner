package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.MicroServiceApplication;
import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.*;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest(classes = { MicroServiceApplication.class })
@ActiveProfiles({ "no-security", "test" })
public class PraktikumsstellenServiceIntegrationTest {

    @Autowired
    PraktikumsstellenMapper praktikumsstellenMapper;

    @Autowired
    private PraktikumsstellenService service;

    @Autowired
    private StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;

    @Autowired
    private AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;

    @Autowired
    private NwkRepository nwkRepository;

    private final ServiceTestHelper helper = new ServiceTestHelper();

    @Test
    public void testUpdateStudiumsPraktikumsstelleWithoutAssignedNwk() {

        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto studiumsPraktikumsstelle = new StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto(
                "GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, "", Referat.RIT, "", false,
                Set.of(Studiensemester.SEMESTER3), Studiengang.BWI, meldezeitraumDto.id(), null);

        UUID uuid = UUID.randomUUID();

        StudiumsPraktikumsstelle actuallySavedPraktikumsstelle = studiumsPraktikumsstellenRepository
                .save(praktikumsstellenMapper.toEntity(uuid, studiumsPraktikumsstelle));

        StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto correctChangedStudiumsPraktikumsstelle = new StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto(
                "GL14", "Max Mustermann", "Max@Mustermann.com",
                "Planung von Events, Test", Dringlichkeit.DRINGEND, "", Referat.RIT, "", true,
                Set.of(Studiensemester.SEMESTER4), Studiengang.BSC, meldezeitraumDto.id(), null);

        Assertions.assertDoesNotThrow(() -> {
            service.updateStudiumsPraktikumsstelle(actuallySavedPraktikumsstelle.getId(), correctChangedStudiumsPraktikumsstelle);
        });

        StudiumsPraktikumsstelle updatedPraktikumsstelle = studiumsPraktikumsstellenRepository.findById(actuallySavedPraktikumsstelle.getId()).get();

        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.oertlicheAusbilder(), updatedPraktikumsstelle.getOertlicheAusbilder());
        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.email(), updatedPraktikumsstelle.getEmail());
        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.taetigkeiten(), updatedPraktikumsstelle.getTaetigkeiten());
        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.dienststelle(), updatedPraktikumsstelle.getDienststelle());
        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.studiengang(), updatedPraktikumsstelle.getStudiengang());
        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.studiensemester(), updatedPraktikumsstelle.getStudiensemester());
        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.planstelleVorhanden(), updatedPraktikumsstelle.isPlanstelleVorhanden());
        Assertions.assertEquals(correctChangedStudiumsPraktikumsstelle.dringlichkeit(), updatedPraktikumsstelle.getDringlichkeit());

    }

    @Test
    public void testUpdateStudiumsPraktikumsstelleWithAssignedNwk() {

        UUID uuid = UUID.randomUUID();

        Nwk testNwk = helper.createNwkEntity("Max", "Mustermann", Studiengang.BSC, null, "23/27", null, true);

        Nwk actuallySavedNwk = nwkRepository.save(testNwk);

        NwkDto testNwkDto = helper.createNwkDto(actuallySavedNwk);

        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");

        StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto studiumsPraktikumsstelle = new StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto(
                "GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, "", Referat.RIT, "", false,
                Set.of(Studiensemester.SEMESTER3), Studiengang.BWI, meldezeitraumDto.id(), testNwkDto);

        StudiumsPraktikumsstelle actuallySavedPraktikumsstelle = studiumsPraktikumsstellenRepository
                .save(praktikumsstellenMapper.toEntity(uuid, studiumsPraktikumsstelle));

        StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto correctChangedStudiumsPraktikumsstelle = new StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto(
                "GL14", "Max Mustermann", "Max@Mustermann.com",
                "Planung von Events, Test", Dringlichkeit.ZWINGEND, "", Referat.RIT, "", false,
                Set.of(Studiensemester.SEMESTER3), Studiengang.BWI, meldezeitraumDto.id(), testNwkDto);

        StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto incorrectChangedStudiumsPraktikumsstelle = new StudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto(
                "GL15", "Bla Bla", "Blub@blub.blub",
                "Planung von Events, Test Test", Dringlichkeit.DRINGEND, "", Referat.ITM, "", false,
                Set.of(Studiensemester.SEMESTER2), Studiengang.BSC, meldezeitraumDto.id(), testNwkDto);

        Assertions.assertDoesNotThrow(() -> {
            service.updateStudiumsPraktikumsstelle(actuallySavedPraktikumsstelle.getId(), correctChangedStudiumsPraktikumsstelle);
        });

        Assertions.assertThrows(ResourceConflictException.class, () -> {
            service.updateStudiumsPraktikumsstelle(actuallySavedPraktikumsstelle.getId(), incorrectChangedStudiumsPraktikumsstelle);
        });

        StudiumsPraktikumsstelle updatedPraktikumsstelle = studiumsPraktikumsstellenRepository.findById(actuallySavedPraktikumsstelle.getId()).get();

        Assertions.assertNotEquals(incorrectChangedStudiumsPraktikumsstelle.oertlicheAusbilder(), updatedPraktikumsstelle.getOertlicheAusbilder());
        Assertions.assertNotEquals(incorrectChangedStudiumsPraktikumsstelle.email(), updatedPraktikumsstelle.getEmail());
        Assertions.assertNotEquals(incorrectChangedStudiumsPraktikumsstelle.taetigkeiten(), updatedPraktikumsstelle.getTaetigkeiten());
        Assertions.assertNotEquals(incorrectChangedStudiumsPraktikumsstelle.dienststelle(), updatedPraktikumsstelle.getDienststelle());
        Assertions.assertNotEquals(incorrectChangedStudiumsPraktikumsstelle.studiengang(), updatedPraktikumsstelle.getStudiengang());

    }

    @Test
    public void testUpdateAusbildungsPraktikumsstelleWithoutAssignedNwk() {

        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto ausbildungsPraktikumsstelle = new AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto(
                "GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, "", Referat.RIT, false, "", false, Set.of(Ausbildungsjahr.JAHR1), Ausbildungsrichtung.FISI,
                meldezeitraumDto.id(), null);

        UUID uuid = UUID.randomUUID();

        AusbildungsPraktikumsstelle actuallySavedPraktikumsstelle = ausbildungsPraktikumsstellenRepository
                .save(praktikumsstellenMapper.toEntity(uuid, ausbildungsPraktikumsstelle));

        AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto correctChangedAusbildungsPraktikumsstelle = new AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto(
                "GL14", "John asdf", "John@asdf.com",
                "Test", Dringlichkeit.ZWINGEND, "", Referat.RIT, true, "", false, Set.of(Ausbildungsjahr.JAHR1), Ausbildungsrichtung.FISI,
                meldezeitraumDto.id(), null);

        Assertions.assertDoesNotThrow(() -> {
            service.updateAusbildungsPraktikumsstelle(actuallySavedPraktikumsstelle.getId(), correctChangedAusbildungsPraktikumsstelle);
        });

        AusbildungsPraktikumsstelle updatedPraktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(actuallySavedPraktikumsstelle.getId()).get();

        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.oertlicheAusbilder(), updatedPraktikumsstelle.getOertlicheAusbilder());
        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.email(), updatedPraktikumsstelle.getEmail());
        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.taetigkeiten(), updatedPraktikumsstelle.getTaetigkeiten());
        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.dienststelle(), updatedPraktikumsstelle.getDienststelle());
        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.ausbildungsjahr(), updatedPraktikumsstelle.getAusbildungsjahr());
        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.ausbildungsrichtung(), updatedPraktikumsstelle.getAusbildungsrichtung());
        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.planstelleVorhanden(), updatedPraktikumsstelle.isPlanstelleVorhanden());
        Assertions.assertEquals(correctChangedAusbildungsPraktikumsstelle.dringlichkeit(), updatedPraktikumsstelle.getDringlichkeit());

    }

    @Test
    public void testUpdateAusbildungsPraktikumsstelleWithAssignedNwk() {

        UUID uuid = UUID.randomUUID();

        Nwk testNwk = helper.createNwkEntity("Max", "Mustermann", Studiengang.BSC, null, "23/27", null, true);

        Nwk actuallySavedNwk = nwkRepository.save(testNwk);

        NwkDto testNwkDto = helper.createNwkDto(actuallySavedNwk);

        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto ausbildungsPraktikumsstelle = new AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto(
                "GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, "", Referat.RIT, false, "", false, Set.of(Ausbildungsjahr.JAHR1), Ausbildungsrichtung.FISI,
                meldezeitraumDto.id(), testNwkDto);

        AusbildungsPraktikumsstelle actuallySavedPraktikumsstelle = ausbildungsPraktikumsstellenRepository
                .save(praktikumsstellenMapper.toEntity(uuid, ausbildungsPraktikumsstelle));

        AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto correctChangedAusbildungsPraktikumsstelle = new AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto(
                "GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, "", Referat.RIT, false, "", false, Set.of(Ausbildungsjahr.JAHR1), Ausbildungsrichtung.FISI,
                meldezeitraumDto.id(), testNwkDto);

        AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto incorrectChangedAusbildungsPraktikumsstelle = new AusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto(
                "GL15", "John Bla", "Bla@asdf.com",
                "Planasd von Events", Dringlichkeit.NACHRANGIG, "", Referat.ITM, true, "", true, Set.of(Ausbildungsjahr.JAHR1, Ausbildungsjahr.JAHR2),
                Ausbildungsrichtung.FISI, meldezeitraumDto.id(), testNwkDto);

        Assertions.assertDoesNotThrow(() -> {
            service.updateAusbildungsPraktikumsstelle(actuallySavedPraktikumsstelle.getId(), correctChangedAusbildungsPraktikumsstelle);
        });

        Assertions.assertThrows(ResourceConflictException.class, () -> {
            service.updateAusbildungsPraktikumsstelle(actuallySavedPraktikumsstelle.getId(), incorrectChangedAusbildungsPraktikumsstelle);
        });

        AusbildungsPraktikumsstelle updatedPraktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(actuallySavedPraktikumsstelle.getId()).get();

        Assertions.assertNotEquals(incorrectChangedAusbildungsPraktikumsstelle.oertlicheAusbilder(), updatedPraktikumsstelle.getOertlicheAusbilder());
        Assertions.assertNotEquals(incorrectChangedAusbildungsPraktikumsstelle.email(), updatedPraktikumsstelle.getEmail());
        Assertions.assertNotEquals(incorrectChangedAusbildungsPraktikumsstelle.taetigkeiten(), updatedPraktikumsstelle.getTaetigkeiten());
        Assertions.assertNotEquals(incorrectChangedAusbildungsPraktikumsstelle.dienststelle(), updatedPraktikumsstelle.getDienststelle());
        Assertions.assertNotEquals(incorrectChangedAusbildungsPraktikumsstelle.ausbildungsjahr(), updatedPraktikumsstelle.getAusbildungsjahr());
        Assertions.assertNotEquals(incorrectChangedAusbildungsPraktikumsstelle.planstelleVorhanden(), updatedPraktikumsstelle.isPlanstelleVorhanden());
        Assertions.assertNotEquals(incorrectChangedAusbildungsPraktikumsstelle.dringlichkeit(), updatedPraktikumsstelle.getDringlichkeit());

    }

}
