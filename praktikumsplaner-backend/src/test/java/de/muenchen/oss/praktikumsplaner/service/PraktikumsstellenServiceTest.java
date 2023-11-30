package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import static org.mockito.ArgumentMatchers.any;
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
    private NWKRepository nwkRepository;

    @Test
    public void testCreateStudiumsPraktikumsstelle() {
        MeldezeitraumDTO meldezeitraumDTO = createMeldezeitraumDTO(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");

        StudiumsPraktikumsstelle studiumsPraktikumsstelle = new StudiumsPraktikumsstelle();
        studiumsPraktikumsstelle.setId(UUID.randomUUID());
        studiumsPraktikumsstelle.setDienststelle("Testdienststelle");
        studiumsPraktikumsstelle.setOertlicheAusbilder("TestoertlicheAusbilder");
        studiumsPraktikumsstelle.setEmail("test@test.de");
        studiumsPraktikumsstelle.setTaetigkeiten("Testtätigkeiten");
        studiumsPraktikumsstelle.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        studiumsPraktikumsstelle.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        studiumsPraktikumsstelle.setReferat(Referat.ITM);
        studiumsPraktikumsstelle.setProgrammierkenntnisse(true);
        studiumsPraktikumsstelle.setStudiensemester(Studiensemester.SEMESTER1);
        studiumsPraktikumsstelle.setStudienart(Studiengang.BSC);
        studiumsPraktikumsstelle.setMeldezeitraumID(meldezeitraumDTO.id());

        CreateStudiumsPraktikumsstelleDTO createDTO = CreateStudiumsPraktikumsstelleDTO.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse(true)
                .studiensemester(Studiensemester.SEMESTER1).studienart(Studiengang.BSC).build();

        StudiumsPraktikumsstelleDTO dto = StudiumsPraktikumsstelleDTO.builder().id(studiumsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse(true)
                .studiensemester(Studiensemester.SEMESTER1).studienart(Studiengang.BSC).build();

        when(studiumsRepository.save(studiumsPraktikumsstelle)).thenReturn(studiumsPraktikumsstelle);
        when(mapper.toDTO(studiumsPraktikumsstelle)).thenReturn(dto);
        when(mapper.toEntity(createDTO, meldezeitraumDTO)).thenReturn(studiumsPraktikumsstelle);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDTO);

        StudiumsPraktikumsstelleDTO result = service.saveStudiumsPraktikumsstelle(createDTO);

        assertEquals(dto, result);
    }

    @Test
    public void testCreateAusbildungsPraktikumsstelle() {
        MeldezeitraumDTO meldezeitraumDTO = createMeldezeitraumDTO(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");

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
        ausbildungsPraktikumsstelle.setAusbildungsjahr(Ausbildungsjahr.JAHR1);
        ausbildungsPraktikumsstelle.setAusbildungsrichtung(Studiengang.FISI);
        ausbildungsPraktikumsstelle.setMeldezeitraumID(meldezeitraumDTO.id());

        CreateAusbildungsPraktikumsstelleDTO createDTO = CreateAusbildungsPraktikumsstelleDTO.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Ausbildungsjahr.JAHR1).ausbildungsrichtung(Studiengang.FISI).build();

        AusbildungsPraktikumsstelleDTO dto = AusbildungsPraktikumsstelleDTO.builder().id(ausbildungsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Ausbildungsjahr.JAHR1).ausbildungsrichtung(Studiengang.FISI).build();

        when(ausbildungsRepository.save(ausbildungsPraktikumsstelle)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toEntity(createDTO, meldezeitraumDTO)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toDTO(ausbildungsPraktikumsstelle)).thenReturn(dto);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDTO);

        AusbildungsPraktikumsstelleDTO result = service.saveAusbildungsPraktikumsstelle(createDTO);

        assertEquals(dto, result);
    }

    @Test
    public void testGetAllPraktikumsstellen() {
        MeldezeitraumDTO meldezeitraumDTO = createMeldezeitraumDTO(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle1 = createAusbildungsPraktikumsstelle("KM81", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.ZWINGEND, Referat.ITM,
                false, Ausbildungsjahr.JAHR2, Studiengang.FISI, meldezeitraumDTO.id());
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle2 = createAusbildungsPraktikumsstelle("KM22", "Erika Mustermann", "erika@mustermann.de",
                "Einarbeitung für Übernahme", Dringlichkeit.DRINGEND, Referat.RIT,
                true, Ausbildungsjahr.JAHR3, Studiengang.FISI, meldezeitraumDTO.id());
        List<AusbildungsPraktikumsstelle> ausbildungsList = Arrays.asList(ausbildungsPraktikumsstelle1, ausbildungsPraktikumsstelle2);

        StudiumsPraktikumsstelle studiumsPraktikumsstelle1 = createStudiumsPraktikumsstelle("KM83", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM, true,
                Studiensemester.SEMESTER5, Studiengang.BSC, meldezeitraumDTO.id());
        StudiumsPraktikumsstelle studiumsPraktikumsstelle2 = createStudiumsPraktikumsstelle("InnoLab", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM, false,
                Studiensemester.SEMESTER5, Studiengang.BWI, meldezeitraumDTO.id());
        StudiumsPraktikumsstelle studiumsPraktikumsstelle3 = createStudiumsPraktikumsstelle("GL13", "John Smith", "John@smith.com",
                "Planung von Events", Dringlichkeit.ZWINGEND, Referat.RIT, true,
                Studiensemester.SEMESTER3, Studiengang.BWI, meldezeitraumDTO.id());
        List<StudiumsPraktikumsstelle> studiumsList = Arrays.asList(studiumsPraktikumsstelle1, studiumsPraktikumsstelle2, studiumsPraktikumsstelle3);

        when(meldezeitraumService.getMostRecentPassedMeldezeitraum()).thenReturn(meldezeitraumDTO);

        when(ausbildungsRepository.findAllByMeldezeitraumID(meldezeitraumDTO.id())).thenReturn(ausbildungsList);
        when(studiumsRepository.findAllByMeldezeitraumID(meldezeitraumDTO.id())).thenReturn(studiumsList);

        when(mapper.toDTO(any(AusbildungsPraktikumsstelle.class)))
                .thenAnswer(invocation -> createPraktikumsstelleDTO((AusbildungsPraktikumsstelle) invocation.getArguments()[0]));
        when(mapper.toDTO(any(StudiumsPraktikumsstelle.class)))
                .thenAnswer(invocation -> createPraktikumsstelleDTO((StudiumsPraktikumsstelle) invocation.getArguments()[0]));

        TreeMap<String, List<PraktikumsstelleDTO>> result = service.getAllPraktiumsstellen();

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
        NWK assigningNwk = new NWK();
        assigningNwk.setId(UUID.randomUUID());
        AusbildungsPraktikumsstelle stelle = createAusbildungsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Ausbildungsjahr.JAHR2, Studiengang.FISI, UUID.randomUUID());
        AusbildungsPraktikumsstelle withAssigned = createAusbildungsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Ausbildungsjahr.JAHR2, Studiengang.FISI, UUID.randomUUID());
        withAssigned.setAssignedNWK(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(true);
        when(ausbildungsRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(assigningNwk));
        when(ausbildungsRepository.save(any(AusbildungsPraktikumsstelle.class))).thenReturn(withAssigned);

        assertEquals(service.assignNWK(stelle.getId(), assigningNwk.getId()), mapper.toDTO(withAssigned));
    }

    @Test
    public void testAssignNwkToStudium() {
        NWK assigningNwk = new NWK();
        assigningNwk.setId(UUID.randomUUID());
        StudiumsPraktikumsstelle stelle = createStudiumsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Studiensemester.SEMESTER1, Studiengang.BWI, UUID.randomUUID());
        StudiumsPraktikumsstelle withAssigned = createStudiumsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Studiensemester.SEMESTER1, Studiengang.BWI, UUID.randomUUID());
        withAssigned.setAssignedNWK(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(false);
        when(studiumsRepository.existsById(stelle.getId())).thenReturn(true);
        when(studiumsRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(assigningNwk));
        when(studiumsRepository.save(any(StudiumsPraktikumsstelle.class))).thenReturn(withAssigned);

        assertEquals(service.assignNWK(stelle.getId(), assigningNwk.getId()), mapper.toDTO(withAssigned));
    }

    @Test
    public void testAssignNwkToOccupiedAusbildung() {
        NWK nwk = new NWK();
        nwk.setId(UUID.randomUUID());
        AusbildungsPraktikumsstelle stelle = createAusbildungsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Ausbildungsjahr.JAHR2, Studiengang.BWI, UUID.randomUUID());
        stelle.setAssignedNWK(nwk);

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(true);
        when(ausbildungsRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(nwk));

        assertThrows(ResourceConflictException.class, () -> service.assignNWK(stelle.getId(), nwk.getId()));
    }

    @Test
    public void testAssignNwkToOccupiedStudium() {
        NWK nwk = new NWK();
        nwk.setId(UUID.randomUUID());
        StudiumsPraktikumsstelle stelle = createStudiumsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Studiensemester.SEMESTER1, Studiengang.BWI, UUID.randomUUID());
        stelle.setAssignedNWK(nwk);

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(false);
        when(studiumsRepository.existsById(stelle.getId())).thenReturn(true);
        when(studiumsRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(nwk));

        assertThrows(ResourceConflictException.class, () -> service.assignNWK(stelle.getId(), nwk.getId()));
    }

    @Test
    public void testAssignNwkToNotExistingPraktikumsstelle() {
        when(ausbildungsRepository.existsById(any(UUID.class))).thenReturn(false);
        when(studiumsRepository.existsById(any(UUID.class))).thenReturn(false);
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(new NWK()));
        assertThrows(ResourceNotFoundException.class, () -> service.assignNWK(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    public void testUnassignNwkToAusbildung() {
        NWK assigningNwk = new NWK();
        assigningNwk.setId(UUID.randomUUID());
        AusbildungsPraktikumsstelle stelle = createAusbildungsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Ausbildungsjahr.JAHR2, Studiengang.FISI, UUID.randomUUID());
        AusbildungsPraktikumsstelle withAssigned = createAusbildungsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Ausbildungsjahr.JAHR2, Studiengang.FISI, UUID.randomUUID());
        withAssigned.setAssignedNWK(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(true);
        when(ausbildungsRepository.findById(stelle.getId())).thenReturn(Optional.of(withAssigned));
        when(ausbildungsRepository.save(any(AusbildungsPraktikumsstelle.class))).thenReturn(stelle);

        assertEquals(service.unassignNWK(stelle.getId()), mapper.toDTO(withAssigned));
    }

    @Test
    public void testUnassignNwkFromStudium() {
        NWK assigningNwk = new NWK();
        assigningNwk.setId(UUID.randomUUID());
        StudiumsPraktikumsstelle stelle = createStudiumsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Studiensemester.SEMESTER1, Studiengang.BWI, UUID.randomUUID());
        StudiumsPraktikumsstelle withAssigned = createStudiumsPraktikumsstelle("KM83", "Ausbilder",
                "asubider@email.de", "Alles", Dringlichkeit.ZWINGEND, Referat.ITM, false,
                Studiensemester.SEMESTER1, Studiengang.BWI, UUID.randomUUID());
        withAssigned.setAssignedNWK(assigningNwk);
        withAssigned.setId(stelle.getId());
        withAssigned.setMeldezeitraumID(stelle.getMeldezeitraumID());

        when(ausbildungsRepository.existsById(stelle.getId())).thenReturn(false);
        when(studiumsRepository.existsById(stelle.getId())).thenReturn(true);
        when(studiumsRepository.findById(stelle.getId())).thenReturn(Optional.of(withAssigned));
        when(studiumsRepository.save(any(StudiumsPraktikumsstelle.class))).thenReturn(stelle);

        assertEquals(service.unassignNWK(stelle.getId()), mapper.toDTO(stelle));
    }

    @Test
    public void testUnassignNwkNotExistingPraktikumsstelle() {
        when(ausbildungsRepository.existsById(any(UUID.class))).thenReturn(false);
        when(studiumsRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.unassignNWK(UUID.randomUUID()));
    }

    private AusbildungsPraktikumsstelle createAusbildungsPraktikumsstelle(
            String dienststelle, String ausbilder, String email, String taetigkeiten, Dringlichkeit dringlichkeit,
            Referat referat, boolean projektarbeit, Ausbildungsjahr ausbildungsjahr, Studiengang studiengang, UUID meldezeitraumId) {
        AusbildungsPraktikumsstelle stelle = new AusbildungsPraktikumsstelle();
        stelle.setId(UUID.randomUUID());
        stelle.setDienststelle(dienststelle);
        stelle.setOertlicheAusbilder(ausbilder);
        stelle.setEmail(email);
        stelle.setTaetigkeiten(taetigkeiten);
        stelle.setDringlichkeit(dringlichkeit);
        stelle.setReferat(referat);
        stelle.setProjektarbeit(projektarbeit);
        stelle.setAusbildungsjahr(ausbildungsjahr);
        stelle.setAusbildungsrichtung(studiengang);
        stelle.setMeldezeitraumID(meldezeitraumId);
        return stelle;
    }

    private StudiumsPraktikumsstelle createStudiumsPraktikumsstelle(
            String dienststelle, String ausbilder, String email, String taetigkeiten, Dringlichkeit dringlichkeit,
            Referat referat, boolean programmierkenntnisse, Studiensemester semester, Studiengang studiengang, UUID meldezeitraumId) {
        StudiumsPraktikumsstelle stelle = new StudiumsPraktikumsstelle();
        stelle.setId(UUID.randomUUID());
        stelle.setDienststelle(dienststelle);
        stelle.setOertlicheAusbilder(ausbilder);
        stelle.setEmail(email);
        stelle.setTaetigkeiten(taetigkeiten);
        stelle.setDringlichkeit(dringlichkeit);
        stelle.setReferat(referat);
        stelle.setProgrammierkenntnisse(programmierkenntnisse);
        stelle.setStudiensemester(semester);
        stelle.setStudienart(studiengang);
        stelle.setMeldezeitraumID(meldezeitraumId);
        return stelle;
    }

    private AusbildungsPraktikumsstelleDTO createPraktikumsstelleDTO(AusbildungsPraktikumsstelle stelle) {
        return AusbildungsPraktikumsstelleDTO.builder()
                .dienststelle(stelle.getDienststelle()).oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail()).taetigkeiten(stelle.getTaetigkeiten())
                .dringlichkeit(stelle.getDringlichkeit()).namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .referat(stelle.getReferat()).projektarbeit(stelle.isProjektarbeit())
                .ausbildungsjahr(stelle.getAusbildungsjahr())
                .ausbildungsrichtung(stelle.getAusbildungsrichtung()).build();
    }

    private StudiumsPraktikumsstelleDTO createPraktikumsstelleDTO(StudiumsPraktikumsstelle stelle) {
        return StudiumsPraktikumsstelleDTO.builder()
                .dienststelle(stelle.getDienststelle()).oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail()).taetigkeiten(stelle.getTaetigkeiten())
                .dringlichkeit(stelle.getDringlichkeit()).namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .referat(stelle.getReferat()).programmierkenntnisse(stelle.isProgrammierkenntnisse())
                .studiensemester(stelle.getStudiensemester()).studienart(stelle.getStudienart()).build();
    }

    private MeldezeitraumDTO createMeldezeitraumDTO(LocalDate start, LocalDate end, String name) {
        return MeldezeitraumDTO.builder()
                .id(UUID.randomUUID())
                .startZeitpunkt(start)
                .endZeitpunkt(end)
                .zeitraumName(name)
                .build();
    }
}
