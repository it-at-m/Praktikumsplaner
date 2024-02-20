package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PraktikumsstellenServiceTest {
    @Spy
    private PraktikumsstellenMapper mapper = Mappers.getMapper(PraktikumsstellenMapper.class);
    @Mock
    private AusbildungsPraktikumsstellenRepository ausbildungsRepository;
    @Mock
    private StudiumsPraktikumsstellenRepository studiumsRepository;
    @InjectMocks
    private PraktikumsstellenService service;
    @Mock
    private MeldezeitraumService meldezeitraumService;
    @Mock
    private NwkRepository nwkRepository;
    private final ServiceTestHelper helper = new ServiceTestHelper();

    @Test
    public void testCreateStudiumsPraktikumsstelle() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");

        StudiumsPraktikumsstelle studiumsPraktikumsstelle = new StudiumsPraktikumsstelle();
        studiumsPraktikumsstelle.setId(UUID.randomUUID());
        studiumsPraktikumsstelle.setDienststelle("Testdienststelle");
        studiumsPraktikumsstelle.setOertlicheAusbilder("TestoertlicheAusbilder");
        studiumsPraktikumsstelle.setEmail("test@test.de");
        studiumsPraktikumsstelle.setTaetigkeiten("Testtätigkeiten");
        studiumsPraktikumsstelle.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        studiumsPraktikumsstelle.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        studiumsPraktikumsstelle.setReferat(Referat.ITM);
        studiumsPraktikumsstelle.setProgrammierkenntnisse("true");
        studiumsPraktikumsstelle.setStudiensemester(Set.of(Studiensemester.SEMESTER1));
        studiumsPraktikumsstelle.setStudiengang(Studiengang.BSC);
        studiumsPraktikumsstelle.setMeldezeitraumID(meldezeitraumDto.id());

        CreateStudiumsPraktikumsstelleDto createDto = CreateStudiumsPraktikumsstelleDto.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse("true")
                .studiensemester(Set.of(Studiensemester.SEMESTER1)).studiengang(Studiengang.BSC).build();

        StudiumsPraktikumsstelleDto dto = StudiumsPraktikumsstelleDto.builder().id(studiumsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse("true")
                .studiensemester(Set.of(Studiensemester.SEMESTER1)).studiengang(Studiengang.BSC).build();

        when(studiumsRepository.save(studiumsPraktikumsstelle)).thenReturn(studiumsPraktikumsstelle);
        when(mapper.toDto(studiumsPraktikumsstelle)).thenReturn(dto);
        when(mapper.toEntity(createDto, meldezeitraumDto)).thenReturn(studiumsPraktikumsstelle);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);

        StudiumsPraktikumsstelleDto result = service.saveStudiumsPraktikumsstelle(createDto);

        assertEquals(dto, result);
    }

    @Test
    public void testCreateAusbildungsPraktikumsstelle() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");

        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = new AusbildungsPraktikumsstelle();
        ausbildungsPraktikumsstelle.setId(UUID.randomUUID());
        ausbildungsPraktikumsstelle.setDienststelle("Testdienststelle");
        ausbildungsPraktikumsstelle.setOertlicheAusbilder("TestoertlicheAusbilder");
        ausbildungsPraktikumsstelle.setEmail("test@test.de");
        ausbildungsPraktikumsstelle.setTaetigkeiten("Testtätigkeiten");
        ausbildungsPraktikumsstelle.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        ausbildungsPraktikumsstelle.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        ausbildungsPraktikumsstelle.setReferat(Referat.ITM);
        ausbildungsPraktikumsstelle.setProjektarbeit(true);
        ausbildungsPraktikumsstelle.setAusbildungsjahr(Set.of(Ausbildungsjahr.JAHR1));
        ausbildungsPraktikumsstelle.setAusbildungsrichtung(Ausbildungsrichtung.FISI);
        ausbildungsPraktikumsstelle.setMeldezeitraumID(meldezeitraumDto.id());

        CreateAusbildungsPraktikumsstelleDto createDto = CreateAusbildungsPraktikumsstelleDto.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR1)).ausbildungsrichtung(Ausbildungsrichtung.FISI).build();

        AusbildungsPraktikumsstelleDto dto = AusbildungsPraktikumsstelleDto.builder().id(ausbildungsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR1)).ausbildungsrichtung(Ausbildungsrichtung.FISI).build();

        when(ausbildungsRepository.save(ausbildungsPraktikumsstelle)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toEntity(createDto, meldezeitraumDto)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toDto(ausbildungsPraktikumsstelle)).thenReturn(dto);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);

        AusbildungsPraktikumsstelleDto result = service.saveAusbildungsPraktikumsstelle(createDto);

        assertEquals(dto, result);
    }

    @Test
    public void testGetAllPraktiumsstellenInMostRecentPassedMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle1 = helper.createAusbildungsPraktikumsstelleEntity("KM81", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, false, meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle2 = helper.createAusbildungsPraktikumsstelleEntity("KM22", "Erika Mustermann",
                "erika@mustermann.de",
                "Einarbeitung für Übernahme", Dringlichkeit.DRINGEND, Referat.RIT,
                Set.of(Ausbildungsjahr.JAHR3), Ausbildungsrichtung.FISI, true, meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        List<AusbildungsPraktikumsstelle> ausbildungsList = Arrays.asList(ausbildungsPraktikumsstelle1, ausbildungsPraktikumsstelle2);

        StudiumsPraktikumsstelle studiumsPraktikumsstelle1 = helper.createStudiumsPraktikumsstelleEntity("KM83", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BSC, "true", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        StudiumsPraktikumsstelle studiumsPraktikumsstelle2 = helper.createStudiumsPraktikumsstelleEntity("InnoLab", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BWI, "false", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        StudiumsPraktikumsstelle studiumsPraktikumsstelle3 = helper.createStudiumsPraktikumsstelleEntity("GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, Referat.RIT,
                Set.of(Studiensemester.SEMESTER3), Studiengang.BWI, "true", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        List<StudiumsPraktikumsstelle> studiumsList = Arrays.asList(studiumsPraktikumsstelle1, studiumsPraktikumsstelle2, studiumsPraktikumsstelle3);

        when(meldezeitraumService.getMostRecentPassedMeldezeitraum()).thenReturn(meldezeitraumDto);

        when(ausbildungsRepository.findAllByMeldezeitraumID(meldezeitraumDto.id())).thenReturn(ausbildungsList);
        when(studiumsRepository.findAllByMeldezeitraumID(meldezeitraumDto.id())).thenReturn(studiumsList);

        when(mapper.toDto(any(AusbildungsPraktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((AusbildungsPraktikumsstelle) invocation.getArguments()[0]));
        when(mapper.toDto(any(StudiumsPraktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((StudiumsPraktikumsstelle) invocation.getArguments()[0]));

        TreeMap<String, List<PraktikumsstelleDto>> result = service.getRecentPraktikumsstellenGroupedByDienststelle();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("GL1", result.firstKey());
        assertTrue(result.containsKey("KM2"));
        assertTrue(result.containsKey("InnoLab"));
        assertEquals("KM8", result.lastKey());

        assertEquals(2, result.get("KM8").size());
        assertEquals(1, result.get("KM2").size());
        assertEquals(1, result.get("InnoLab").size());
        assertEquals(1, result.get("GL1").size());
    }

    @Test
    public void testGetAllPraktiumsstellenInCurrentMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().plusDays(1), "letzte woche bis morgen");
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle1 = helper.createAusbildungsPraktikumsstelleEntity("KM81", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, false, meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle2 = helper.createAusbildungsPraktikumsstelleEntity("KM22", "Erika Mustermann",
                "erika@mustermann.de",
                "Einarbeitung für Übernahme", Dringlichkeit.DRINGEND, Referat.RIT,
                Set.of(Ausbildungsjahr.JAHR3), Ausbildungsrichtung.FISI, true, meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        List<AusbildungsPraktikumsstelle> ausbildungsList = Arrays.asList(ausbildungsPraktikumsstelle1, ausbildungsPraktikumsstelle2);

        StudiumsPraktikumsstelle studiumsPraktikumsstelle1 = helper.createStudiumsPraktikumsstelleEntity("KM83", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BSC, "true", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        StudiumsPraktikumsstelle studiumsPraktikumsstelle2 = helper.createStudiumsPraktikumsstelleEntity("InnoLab", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BWI, "false", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        StudiumsPraktikumsstelle studiumsPraktikumsstelle3 = helper.createStudiumsPraktikumsstelleEntity("GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, Referat.RIT,
                Set.of(Studiensemester.SEMESTER3), Studiengang.BWI, "true", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        List<StudiumsPraktikumsstelle> studiumsList = Arrays.asList(studiumsPraktikumsstelle1, studiumsPraktikumsstelle2, studiumsPraktikumsstelle3);

        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);

        when(ausbildungsRepository.findAllByMeldezeitraumID(meldezeitraumDto.id())).thenReturn(ausbildungsList);
        when(studiumsRepository.findAllByMeldezeitraumID(meldezeitraumDto.id())).thenReturn(studiumsList);

        when(mapper.toDto(any(AusbildungsPraktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((AusbildungsPraktikumsstelle) invocation.getArguments()[0]));
        when(mapper.toDto(any(StudiumsPraktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((StudiumsPraktikumsstelle) invocation.getArguments()[0]));

        TreeMap<String, List<PraktikumsstelleDto>> result = service.getAllInCurrentMeldezeitraumGroupedByDienststelle();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("GL1", result.firstKey());
        assertTrue(result.containsKey("KM2"));
        assertTrue(result.containsKey("InnoLab"));
        assertEquals("KM8", result.lastKey());

        assertEquals(2, result.get("KM8").size());
        assertEquals(1, result.get("KM2").size());
        assertEquals(1, result.get("InnoLab").size());
        assertEquals(1, result.get("GL1").size());
    }

    @Test
    public void testAssignNwkToAusbildung() {
        Nwk assigningNwk = new Nwk();
        assigningNwk.setId(UUID.randomUUID());
        AusbildungsPraktikumsstelle stelle = helper.createAusbildungsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, false, UUID.randomUUID(), null);
        AusbildungsPraktikumsstelle withAssigned = helper.createAusbildungsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, false, UUID.randomUUID(), null);
        withAssigned.setAssignedNwk(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(true);
        when(ausbildungsRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(assigningNwk));
        when(ausbildungsRepository.save(any(AusbildungsPraktikumsstelle.class))).thenReturn(withAssigned);

        assertEquals(service.assignNwk(stelle.getId(), assigningNwk.getId()), mapper.toDto(withAssigned));
    }

    @Test
    public void testAssignNwkToStudium() {
        Nwk assigningNwk = new Nwk();
        assigningNwk.setId(UUID.randomUUID());
        StudiumsPraktikumsstelle stelle = helper.createStudiumsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Studiensemester.SEMESTER1), Studiengang.BWI, "false", UUID.randomUUID(), null);
        StudiumsPraktikumsstelle withAssigned = helper.createStudiumsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, Set.of(Studiensemester.SEMESTER1),
                Studiengang.BWI, "false", UUID.randomUUID(), null);
        withAssigned.setAssignedNwk(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(false);
        when(studiumsRepository.existsById(stelle.getId())).thenReturn(true);
        when(studiumsRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(assigningNwk));
        when(studiumsRepository.save(any(StudiumsPraktikumsstelle.class))).thenReturn(withAssigned);

        assertEquals(service.assignNwk(stelle.getId(), assigningNwk.getId()), mapper.toDto(withAssigned));
    }

    @Test
    public void testAssignNwkToOccupiedStudium() {
        Nwk nwk = new Nwk();
        nwk.setId(UUID.randomUUID());
        StudiumsPraktikumsstelle stelle = helper.createStudiumsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Studiensemester.SEMESTER1), Studiengang.BWI, "false", UUID.randomUUID(), null);
        stelle.setAssignedNwk(nwk);

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(false);
        when(studiumsRepository.existsById(stelle.getId())).thenReturn(true);
        when(studiumsRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(nwk));

        assertThrows(ResourceConflictException.class, () -> service.assignNwk(stelle.getId(), nwk.getId()));
    }

    @Test
    public void testAssignNwkToNotExistingPraktikumsstelle() {
        when(ausbildungsRepository.existsById(any(UUID.class))).thenReturn(false);
        when(studiumsRepository.existsById(any(UUID.class))).thenReturn(false);
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Nwk()));
        assertThrows(ResourceNotFoundException.class, () -> service.assignNwk(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    public void testUnassignNwkToAusbildung() {
        Nwk assigningNwk = new Nwk();
        assigningNwk.setId(UUID.randomUUID());
        AusbildungsPraktikumsstelle stelle = helper.createAusbildungsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, false, UUID.randomUUID(), null);
        AusbildungsPraktikumsstelle withAssigned = helper.createAusbildungsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, false, UUID.randomUUID(), null);
        withAssigned.setAssignedNwk(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(true);
        when(ausbildungsRepository.findById(stelle.getId())).thenReturn(Optional.of(withAssigned));
        when(ausbildungsRepository.save(any(AusbildungsPraktikumsstelle.class))).thenReturn(stelle);

        assertEquals(service.unassignNwk(stelle.getId()), mapper.toDto(withAssigned));
    }

    @Test
    public void testUnassignNwkFromStudium() {
        Nwk assigningNwk = new Nwk();
        assigningNwk.setId(UUID.randomUUID());
        StudiumsPraktikumsstelle stelle = helper.createStudiumsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Studiensemester.SEMESTER1), Studiengang.BWI, "false", UUID.randomUUID(), null);
        StudiumsPraktikumsstelle withAssigned = helper.createStudiumsPraktikumsstelleEntity("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Studiensemester.SEMESTER1), Studiengang.BWI, "false", UUID.randomUUID(), null);
        withAssigned.setAssignedNwk(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(false);
        when(studiumsRepository.existsById(stelle.getId())).thenReturn(true);
        when(studiumsRepository.findById(stelle.getId())).thenReturn(Optional.of(withAssigned));
        when(studiumsRepository.save(any(StudiumsPraktikumsstelle.class))).thenReturn(stelle);

        assertEquals(service.unassignNwk(stelle.getId()), mapper.toDto(stelle));
    }

    @Test
    public void testUnassignNwkNotExistingPraktikumsstelle() {
        when(ausbildungsRepository.existsById(any(UUID.class))).thenReturn(false);
        when(studiumsRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.unassignNwk(UUID.randomUUID()));
    }

    @Test
    public void testSaveStudiumsPraktikumsstelleWithMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now(), LocalDate.now().plusDays(1), "Test");

        StudiumsPraktikumsstelle studiumsPraktikumsstelle = helper.createStudiumsPraktikumsstelleEntity("KM83", "Ausbilder", "tester@test.de",
                "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, Set.of(Studiensemester.SEMESTER1), Studiengang.BSC,
                "true", meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));

        CreateStudiumsPraktikumsstelleWithMeldezeitraumDto createStudiumsPraktikumsstelleDto = CreateStudiumsPraktikumsstelleWithMeldezeitraumDto.builder()
                .dienststelle(studiumsPraktikumsstelle.getDienststelle()).oertlicheAusbilder(studiumsPraktikumsstelle.getOertlicheAusbilder())
                .email(studiumsPraktikumsstelle.getEmail()).taetigkeiten(studiumsPraktikumsstelle.getTaetigkeiten())
                .dringlichkeit(studiumsPraktikumsstelle.getDringlichkeit()).namentlicheAnforderung(studiumsPraktikumsstelle.getNamentlicheAnforderung())
                .referat(studiumsPraktikumsstelle.getReferat()).programmierkenntnisse(studiumsPraktikumsstelle.getProgrammierkenntnisse())
                .studiensemester(studiumsPraktikumsstelle.getStudiensemester()).studiengang(studiumsPraktikumsstelle.getStudiengang()).build();

        StudiumsPraktikumsstelleDto studiumsPraktikumsstelleDto = helper.createPraktikumsstelleDto(studiumsPraktikumsstelle);

        when(studiumsRepository.save(studiumsPraktikumsstelle)).thenReturn(studiumsPraktikumsstelle);
        when(mapper.toDto(studiumsPraktikumsstelle)).thenReturn(studiumsPraktikumsstelleDto);
        when(mapper.toEntity(createStudiumsPraktikumsstelleDto)).thenReturn(studiumsPraktikumsstelle);

        StudiumsPraktikumsstelleDto result = service.saveStudiumsPraktikumsstelleWithMeldezeitraum(createStudiumsPraktikumsstelleDto);

        assertEquals(studiumsPraktikumsstelleDto, result);
    }

    @Test
    public void testSaveAusbildungsPraktikumsstelleWithMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now(), LocalDate.now().plusDays(1), "Test");

        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = helper.createAusbildungsPraktikumsstelleEntity("KM83", "Ausbilder", "tester@test.de",
                "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI,
                true, meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));

        CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto createAusbildungsPraktikumsstelleDto = CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto
                .builder().dienststelle(ausbildungsPraktikumsstelle.getDienststelle()).oertlicheAusbilder(ausbildungsPraktikumsstelle.getOertlicheAusbilder())
                .email(ausbildungsPraktikumsstelle.getEmail()).taetigkeiten(ausbildungsPraktikumsstelle.getTaetigkeiten())
                .dringlichkeit(ausbildungsPraktikumsstelle.getDringlichkeit()).namentlicheAnforderung(ausbildungsPraktikumsstelle.getNamentlicheAnforderung())
                .referat(ausbildungsPraktikumsstelle.getReferat()).programmierkenntnisse(ausbildungsPraktikumsstelle.getProgrammierkenntnisse())
                .ausbildungsjahr(ausbildungsPraktikumsstelle.getAusbildungsjahr()).ausbildungsrichtung(ausbildungsPraktikumsstelle.getAusbildungsrichtung())
                .build();

        AusbildungsPraktikumsstelleDto ausbildungsPraktikumsstelleDto = helper.createPraktikumsstelleDto(ausbildungsPraktikumsstelle);

        when(ausbildungsRepository.save(ausbildungsPraktikumsstelle)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toDto(ausbildungsPraktikumsstelle)).thenReturn(ausbildungsPraktikumsstelleDto);
        when(mapper.toEntity(createAusbildungsPraktikumsstelleDto)).thenReturn(ausbildungsPraktikumsstelle);

        AusbildungsPraktikumsstelleDto result = service.saveAusbildungsPraktikumsstelleWithMeldezeitraum(createAusbildungsPraktikumsstelleDto);

        assertEquals(ausbildungsPraktikumsstelleDto, result);
    }

    @Test
    public void testGetAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle1 = helper.createAusbildungsPraktikumsstelleEntity("KM81", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.ZWINGEND, Referat.ITM,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, false, meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle2 = helper.createAusbildungsPraktikumsstelleEntity("KM22", "Erika Mustermann",
                "erika@mustermann.de",
                "Einarbeitung für Übernahme", Dringlichkeit.DRINGEND, Referat.RIT,
                Set.of(Ausbildungsjahr.JAHR3), Ausbildungsrichtung.FISI, true, meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        List<AusbildungsPraktikumsstelle> ausbildungsList = Arrays.asList(ausbildungsPraktikumsstelle1, ausbildungsPraktikumsstelle2);

        StudiumsPraktikumsstelle studiumsPraktikumsstelle1 = helper.createStudiumsPraktikumsstelleEntity("KM83", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BSC, "true", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        StudiumsPraktikumsstelle studiumsPraktikumsstelle2 = helper.createStudiumsPraktikumsstelleEntity("InnoLab", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BWI, "false", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        StudiumsPraktikumsstelle studiumsPraktikumsstelle3 = helper.createStudiumsPraktikumsstelleEntity("GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, Referat.RIT,
                Set.of(Studiensemester.SEMESTER3), Studiengang.BWI, "true", meldezeitraumDto.id(),
                helper.createNwkEntity("TestNwk", "TestNwk", null, null, null, null, false));
        List<StudiumsPraktikumsstelle> studiumsList = Arrays.asList(studiumsPraktikumsstelle1, studiumsPraktikumsstelle2, studiumsPraktikumsstelle3);

        List<PraktikumsstelleDto> dtos = new ArrayList<>();
        dtos.addAll(ausbildungsList.stream().map(helper::createPraktikumsstelleDto).toList());
        dtos.addAll(studiumsList.stream().map(helper::createPraktikumsstelleDto).toList());

        when(meldezeitraumService.getMostRecentPassedMeldezeitraum()).thenReturn(meldezeitraumDto);

        when(ausbildungsRepository.findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(meldezeitraumDto.id())).thenReturn(ausbildungsList);
        when(studiumsRepository.findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(meldezeitraumDto.id())).thenReturn(studiumsList);

        when(mapper.toDto(any(AusbildungsPraktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((AusbildungsPraktikumsstelle) invocation.getArguments()[0]));
        when(mapper.toDto(any(StudiumsPraktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((StudiumsPraktikumsstelle) invocation.getArguments()[0]));

        List<PraktikumsstelleDto> result = service.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum();

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(dtos, result);
    }

    @Test
    public void testShouldDeleteAllPraktikumsstellenByMeldezeitraumId() {
        UUID meldezeitraumId = UUID.randomUUID();

        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = new AusbildungsPraktikumsstelle();
        ausbildungsPraktikumsstelle.setMeldezeitraumID(meldezeitraumId);

        StudiumsPraktikumsstelle studiumsPraktikumsstelle = new StudiumsPraktikumsstelle();
        studiumsPraktikumsstelle.setMeldezeitraumID(meldezeitraumId);

        List<AusbildungsPraktikumsstelle> ausbildungsPraktikumsstellen = List.of(ausbildungsPraktikumsstelle);
        List<StudiumsPraktikumsstelle> studiumsPraktikumsstellen = List.of(studiumsPraktikumsstelle);

        when(ausbildungsRepository.findAllByMeldezeitraumID(meldezeitraumId)).thenReturn(ausbildungsPraktikumsstellen);
        when(studiumsRepository.findAllByMeldezeitraumID(meldezeitraumId)).thenReturn(studiumsPraktikumsstellen);

        service.deleteAllPraktikumsstellenByMeldezeitraumId(meldezeitraumId);

        verify(ausbildungsRepository, times(1)).deleteAll(ausbildungsPraktikumsstellen);
        verify(studiumsRepository, times(1)).deleteAll(studiumsPraktikumsstellen);
    }

    @Test
    public void testShouldNotDeletePraktikumsstellenWhenNoneExistForMeldezeitraumId() {
        UUID meldezeitraumId = UUID.randomUUID();

        when(ausbildungsRepository.findAllByMeldezeitraumID(meldezeitraumId)).thenReturn(new ArrayList<>());
        when(studiumsRepository.findAllByMeldezeitraumID(meldezeitraumId)).thenReturn(new ArrayList<>());

        service.deleteAllPraktikumsstellenByMeldezeitraumId(meldezeitraumId);

        verify(ausbildungsRepository, times(1)).deleteAll(anyList());
        verify(studiumsRepository, times(1)).deleteAll(anyList());
    }
}
