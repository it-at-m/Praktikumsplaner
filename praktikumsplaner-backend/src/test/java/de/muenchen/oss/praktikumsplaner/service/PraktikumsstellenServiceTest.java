package de.muenchen.oss.praktikumsplaner.service;

import static de.muenchen.oss.praktikumsplaner.TestUtils.getJwtAuthenticationToken;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import de.muenchen.oss.praktikumsplaner.repository.PraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.security.Authorities;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class PraktikumsstellenServiceTest {

    @Spy
    private PraktikumsstellenMapper mapper = Mappers.getMapper(PraktikumsstellenMapper.class);

    @Mock
    private PraktikumsstellenRepository praktikumsstellenRepository;

    @Mock
    private MeldezeitraumService meldezeitraumService;

    @Mock
    private NwkRepository nwkRepository;

    @InjectMocks
    private PraktikumsstellenService service;

    private final ServiceTestHelper helper = new ServiceTestHelper();

    @BeforeEach
    void setUp() {
        var authentication = getJwtAuthenticationToken(Authorities.AuthoritiesEnum.AUSBILDUNGSLEITUNG);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testCreateStudiumsPraktikumsstelle() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");

        Praktikumsstelle praktikumsstelle = helper.createPraktikumsstelleEntity(
                "TEST-001",
                "TestoertlicheAusbilder",
                "test@test.de",
                "Testtaetigkeiten",
                "Wuensche",
                Dringlichkeit.NACHRANGIG,
                Bildungsrichtung.BSC,
                null,
                Set.of(Studiensemester.SEMESTER1),
                "true",
                false,
                false,
                meldezeitraumDto.id(),
                null);

        CreatePraktikumsstelleDto createDto = CreatePraktikumsstelleDto.builder()
                .dienststelle("TEST-001")
                .oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de")
                .taetigkeiten("Testtaetigkeiten")
                .wuensche("Wuensche")
                .dringlichkeit(Dringlichkeit.NACHRANGIG)
                .namentlicheAnforderung("TestnamentlicheAnforderung")
                .programmierkenntnisse("true")
                .studiensemester(Set.of(Studiensemester.SEMESTER1))
                .richtung(Bildungsrichtung.BSC)
                .build();

        PraktikumsstelleDto dto = helper.createPraktikumsstelleDto(praktikumsstelle);

        when(praktikumsstellenRepository.save(praktikumsstelle)).thenReturn(praktikumsstelle);
        when(mapper.toDto(praktikumsstelle)).thenReturn(dto);
        when(mapper.toEntity(createDto, meldezeitraumDto)).thenReturn(praktikumsstelle);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);

        PraktikumsstelleDto result = service.normalizeAndSavePraktikumsstelle(createDto);

        assertEquals(dto, result);
    }

    @Test
    void testCreateAusbildungsPraktikumsstelle() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");

        Praktikumsstelle praktikumsstelle = helper.createPraktikumsstelleEntity(
                "TEST-001",
                "TestoertlicheAusbilder",
                "test@test.de",
                "Testtaetigkeiten",
                "Wuensche",
                Dringlichkeit.NACHRANGIG,
                Bildungsrichtung.FISI,
                Set.of(Ausbildungsjahr.JAHR1),
                null,
                null,
                true,
                true,
                meldezeitraumDto.id(),
                null);

        CreatePraktikumsstelleDto createDto = CreatePraktikumsstelleDto.builder()
                .dienststelle("TEST-001")
                .oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de")
                .taetigkeiten("Testtaetigkeiten")
                .wuensche("Wuensche")
                .dringlichkeit(Dringlichkeit.NACHRANGIG)
                .namentlicheAnforderung("TestnamentlicheAnforderung")
                .projektarbeit(true)
                .minderjaehrigMoeglich(true)
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR1))
                .richtung(Bildungsrichtung.FISI)
                .build();

        PraktikumsstelleDto dto = helper.createPraktikumsstelleDto(praktikumsstelle);

        when(praktikumsstellenRepository.save(praktikumsstelle)).thenReturn(praktikumsstelle);
        when(mapper.toEntity(createDto, meldezeitraumDto)).thenReturn(praktikumsstelle);
        when(mapper.toDto(praktikumsstelle)).thenReturn(dto);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);

        PraktikumsstelleDto result = service.normalizeAndSavePraktikumsstelle(createDto);

        assertEquals(dto, result);
    }

    @Test
    void testGetAllPraktiumsstellenInMostRecentPassedMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        List<Praktikumsstelle> stellen = List.of(
                helper.createPraktikumsstelleEntity("ITM-SLP31", "Max Musterfrau", "max@musterfrau.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.ZWINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR2), null, null, false, true,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-DKL22", "Erika Mustermann", "erika@mustermann.de", "Einarbeitung für Übernahme", null,
                        Dringlichkeit.DRINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR3), null, null, true, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-SLP33", "Test Tester", "test@tester.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.NACHRANGIG, Bildungsrichtung.BSC, null, Set.of(Studiensemester.SEMESTER5), "true", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BSC, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-DKL-IL", "Test Testerin", "test@testerin.de", "Design eines Praktikumsplaners", null,
                        Dringlichkeit.NACHRANGIG, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER5), "false", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BWI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-GL13", "John Smith", "John@smith.com", "Planung von Events", null,
                        Dringlichkeit.ZWINGEND, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER3), "true", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BWI, "22/23", null, false)));

        when(meldezeitraumService.getMostRecentPassedMeldezeitraum()).thenReturn(meldezeitraumDto);
        when(praktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumDto.id())).thenReturn(stellen);
        when(mapper.toDto(any(Praktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((Praktikumsstelle) invocation.getArguments()[0]));

        List<PraktikumsstelleDto> result = service.getRecentPraktikumsstellen();

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    void testGetAllPraktiumsstellenInMostRecentPassedMeldezeitraumForAusbilder() {
        var authentication = getJwtAuthenticationToken(Authorities.AuthoritiesEnum.AUSBILDER);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        List<Praktikumsstelle> stellen = List.of(
                helper.createPraktikumsstelleEntity("ITM-SLP31", "Max Musterfrau", "max@musterfrau.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.ZWINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR2), null, null, false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-DKL22", "Erika Mustermann", "test@test.de", "Einarbeitung für Übernahme", null,
                        Dringlichkeit.DRINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR3), null, null, true, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-SLP33", "Test Tester", "test@test.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.NACHRANGIG, Bildungsrichtung.BSC, null, Set.of(Studiensemester.SEMESTER5), "true", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BSC, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-DKL-IL", "Test Testerin", "test@tester.de", "Design eines Praktikumsplaners", null,
                        Dringlichkeit.NACHRANGIG, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER5), "false", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BWI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-GL13", "John Smith", "John@smith.com", "Planung von Events", null,
                        Dringlichkeit.ZWINGEND, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER3), "true", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BWI, "22/23", null, false)));

        when(meldezeitraumService.getMostRecentPassedMeldezeitraum()).thenReturn(meldezeitraumDto);
        when(praktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumDto.id())).thenReturn(stellen);
        when(mapper.toDto(any(Praktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((Praktikumsstelle) invocation.getArguments()[0]));

        List<PraktikumsstelleDto> result = service.getRecentPraktikumsstellen();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testGetAllPraktiumsstellenInCurrentMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().plusDays(1), "letzte woche bis morgen");
        List<Praktikumsstelle> stellen = List.of(
                helper.createPraktikumsstelleEntity("ITM-SLP31", "Max Musterfrau", "max@musterfrau.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.ZWINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR2), null, null, false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-DKL22", "Erika Mustermann", "erika@mustermann.de", "Einarbeitung für Übernahme", null,
                        Dringlichkeit.DRINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR3), null, null, true, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-SLP33", "Test Tester", "test@tester.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.NACHRANGIG, Bildungsrichtung.BSC, null, Set.of(Studiensemester.SEMESTER5), "true", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BSC, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-DKL-IL", "Test Testerin", "test@testerin.de", "Design eines Praktikumsplaners", null,
                        Dringlichkeit.NACHRANGIG, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER5), "false", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BWI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-GL13", "John Smith", "John@smith.com", "Planung von Events", null,
                        Dringlichkeit.ZWINGEND, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER3), "true", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BWI, "22/23", null, false)));

        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);
        when(praktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumDto.id())).thenReturn(stellen);
        when(mapper.toDto(any(Praktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((Praktikumsstelle) invocation.getArguments()[0]));

        List<PraktikumsstelleDto> result = service.getAllInCurrentMeldezeitraum();

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    void testAssignNwk() {
        Nwk assigningNwk = new Nwk();
        assigningNwk.setId(UUID.randomUUID());
        Praktikumsstelle stelle = helper.createPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "ausbilder@email.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR2), null, null, false, false, UUID.randomUUID(), null);
        Praktikumsstelle withAssigned = helper.createPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "ausbilder@email.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR2), null, null, false, false, stelle.getMeldezeitraumID(), null);
        withAssigned.setAssignedNwk(assigningNwk);
        withAssigned.setId(stelle.getId());

        when(praktikumsstellenRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(assigningNwk));
        when(praktikumsstellenRepository.save(any(Praktikumsstelle.class))).thenReturn(withAssigned);

        assertEquals(mapper.toDto(withAssigned), service.assignNwk(stelle.getId(), assigningNwk.getId()));
    }

    @Test
    void testAssignNwkToOccupiedStelle() {
        Nwk nwk = new Nwk();
        nwk.setId(UUID.randomUUID());
        Praktikumsstelle stelle = helper.createPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "ausbilder@email.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER1), "false", false, false, UUID.randomUUID(), nwk);

        when(praktikumsstellenRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(nwk));

        assertThrows(ResourceConflictException.class, () -> service.assignNwk(stelle.getId(), nwk.getId()));
    }

    @Test
    void testAssignNwkToNotExistingPraktikumsstelle() {
        when(praktikumsstellenRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Nwk()));
        assertThrows(ResourceNotFoundException.class, () -> service.assignNwk(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void testUnassignNwk() {
        Nwk assignedNwk = new Nwk();
        assignedNwk.setId(UUID.randomUUID());
        Praktikumsstelle stelle = helper.createPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "ausbilder@email.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Bildungsrichtung.BWI, null, Set.of(Studiensemester.SEMESTER1), "false", false, false, UUID.randomUUID(), assignedNwk);

        when(praktikumsstellenRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(praktikumsstellenRepository.save(any(Praktikumsstelle.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        PraktikumsstelleDto result = service.unassignNwk(stelle.getId());

        assertEquals(stelle.getId(), result.id());
        Assertions.assertNull(result.assignedNwk());
    }

    @Test
    void testUnassignNwkNotExistingPraktikumsstelle() {
        when(praktikumsstellenRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.unassignNwk(UUID.randomUUID()));
    }

    @Test
    void testSavePraktikumsstelleWithMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now(), LocalDate.now().plusDays(1), "Test");

        Praktikumsstelle praktikumsstelle = helper.createPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "tester@test.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Bildungsrichtung.BSC, null, Set.of(Studiensemester.SEMESTER1), "true", false, false,
                meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BSC, "23/27", null, false));

        CreatePraktikumsstelleWithMeldezeitraumDto createDto = CreatePraktikumsstelleWithMeldezeitraumDto.builder()
                .dienststelle(praktikumsstelle.getDienststelle())
                .oertlicheAusbilder(praktikumsstelle.getOertlicheAusbilder())
                .email(praktikumsstelle.getEmail())
                .taetigkeiten(praktikumsstelle.getTaetigkeiten())
                .dringlichkeit(praktikumsstelle.getDringlichkeit())
                .namentlicheAnforderung(praktikumsstelle.getNamentlicheAnforderung())
                .programmierkenntnisse(praktikumsstelle.getProgrammierkenntnisse())
                .studiensemester(praktikumsstelle.getStudiensemester())
                .richtung(praktikumsstelle.getRichtung())
                .meldezeitraumID(praktikumsstelle.getMeldezeitraumID())
                .build();

        PraktikumsstelleDto praktikumsstelleDto = helper.createPraktikumsstelleDto(praktikumsstelle);

        when(praktikumsstellenRepository.save(praktikumsstelle)).thenReturn(praktikumsstelle);
        when(mapper.toDto(praktikumsstelle)).thenReturn(praktikumsstelleDto);
        when(mapper.toEntity(createDto)).thenReturn(praktikumsstelle);

        PraktikumsstelleDto result = service.savePraktikumsstelleWithMeldezeitraum(createDto);

        assertEquals(praktikumsstelleDto, result);
    }

    @Test
    void testGetAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        List<Praktikumsstelle> stellen = List.of(
                helper.createPraktikumsstelleEntity("ITM-SLP31", "Max Musterfrau", "max@musterfrau.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.ZWINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR2), null, null, false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-DKL22", "Erika Mustermann", "erika@mustermann.de", "Einarbeitung für Übernahme", null,
                        Dringlichkeit.DRINGEND, Bildungsrichtung.FISI, Set.of(Ausbildungsjahr.JAHR3), null, null, true, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.FISI, "22/23", null, false)),
                helper.createPraktikumsstelleEntity("ITM-SLP33", "Test Tester", "test@tester.de", "Entwicklung eines Praktikumsplaners", null,
                        Dringlichkeit.NACHRANGIG, Bildungsrichtung.BSC, null, Set.of(Studiensemester.SEMESTER5), "true", false, false,
                        meldezeitraumDto.id(), helper.createNwkEntity("TestNwk", "TestNwk", Bildungsrichtung.BSC, "22/23", null, false)));

        when(meldezeitraumService.getMostRecentPassedMeldezeitraum()).thenReturn(meldezeitraumDto);
        when(praktikumsstellenRepository.findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(meldezeitraumDto.id())).thenReturn(stellen);
        when(mapper.toDto(any(Praktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((Praktikumsstelle) invocation.getArguments()[0]));

        List<PraktikumsstelleDto> result = service.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testDeletePraktikumsstelle() {
        UUID id = UUID.randomUUID();
        when(praktikumsstellenRepository.existsById(id)).thenReturn(true);

        service.deletePraktikumsstelle(id);

        verify(praktikumsstellenRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePraktikumsstelleWithoutExisting() {
        UUID id = UUID.randomUUID();
        when(praktikumsstellenRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.deletePraktikumsstelle(id));
    }

    @Test
    void testUpdatePraktikumstelle() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        UpdatePraktikumsstelleDto praktikumsstelle = new UpdatePraktikumsstelleDto(
                "ITM-GL13", "John Smith", false, "John@smith.com", "Planung von Events", Dringlichkeit.ZWINGEND, "", false, "false", null,
                false, null, Set.of(Studiensemester.SEMESTER3), Bildungsrichtung.BWI, meldezeitraumDto.id(), false);

        UUID uuid = UUID.randomUUID();
        Praktikumsstelle praktikumsstelleToCheck = mapper.toEntity(uuid, praktikumsstelle);

        when(praktikumsstellenRepository.findById(uuid)).thenReturn(Optional.of(praktikumsstelleToCheck));

        service.updatePraktikumsstelle(uuid, praktikumsstelle);

        verify(praktikumsstellenRepository, times(1)).save(mapper.toEntity(uuid, praktikumsstelle));
    }

    @Test
    void testUpdatePraktikumstelleWithoutExisting() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        UpdatePraktikumsstelleDto praktikumsstelle = new UpdatePraktikumsstelleDto(
                "ITM-GL13", "John Smith", false, "John@smith.com", "Planung von Events", Dringlichkeit.ZWINGEND, "", false, "false", null,
                false, null, Set.of(Studiensemester.SEMESTER3), Bildungsrichtung.BWI, meldezeitraumDto.id(), false);

        UUID uuid = UUID.randomUUID();
        when(praktikumsstellenRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.updatePraktikumsstelle(uuid, praktikumsstelle));
    }

    @Test
    void testUpdatePraktikumsstelleWithAssignedNwkThenSuccess() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        Nwk nwk = helper.createNwkEntity("Max", "Mustermensch", Bildungsrichtung.BSC, "23/27", null, true);
        Praktikumsstelle praktikumsstelle = helper.createPraktikumsstelleEntity("TEST", "Ausbilder", "ausbilder@email.ausbilder", "Taetigkeiten", null,
                Dringlichkeit.ZWINGEND, Bildungsrichtung.BSC, null, Set.of(Studiensemester.SEMESTER1), null, false, false, meldezeitraumDto.id(), nwk);
        UpdatePraktikumsstelleDto updateDto = new UpdatePraktikumsstelleDto(
                "TESTTEST", "Ausbilder", false, "ausbilder@email.ausbilder", "Taetigkeiten", Dringlichkeit.ZWINGEND, null, false, null, null,
                false, null, Set.of(Studiensemester.SEMESTER1), Bildungsrichtung.BSC, meldezeitraumDto.id(), false);

        when(praktikumsstellenRepository.findById(praktikumsstelle.getId())).thenReturn(Optional.of(praktikumsstelle));

        assertDoesNotThrow(() -> service.updatePraktikumsstelle(praktikumsstelle.getId(), updateDto));
        verify(praktikumsstellenRepository, times(1)).save(praktikumsstelle);
    }

    @Test
    void testUpdatePraktikumsstelleWithAssignedNwkThenError() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        Nwk nwk = helper.createNwkEntity("Max", "Mustermensch", Bildungsrichtung.BSC, "23/27", null, true);
        Praktikumsstelle praktikumsstelle = helper.createPraktikumsstelleEntity("TEST", "Ausbilder", "ausbilder@email.ausbilder", "Taetigkeiten", null,
                Dringlichkeit.ZWINGEND, Bildungsrichtung.BSC, null, Set.of(Studiensemester.SEMESTER1), null, false, false, meldezeitraumDto.id(), nwk);
        UpdatePraktikumsstelleDto updateDto = new UpdatePraktikumsstelleDto(
                "TESTTEST", "Ausbilder", false, "ausbilder@email.ausbilder", "Taetigkeiten", Dringlichkeit.DRINGEND, null, false, null, null,
                false, null, Set.of(Studiensemester.SEMESTER1), Bildungsrichtung.BSC, meldezeitraumDto.id(), false);

        when(praktikumsstellenRepository.findById(praktikumsstelle.getId())).thenReturn(Optional.of(praktikumsstelle));

        Assertions.assertThrows(ResourceConflictException.class, () -> service.updatePraktikumsstelle(praktikumsstelle.getId(), updateDto));
    }
}
