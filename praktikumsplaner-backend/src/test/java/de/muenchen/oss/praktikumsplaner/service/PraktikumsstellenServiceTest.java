package de.muenchen.oss.praktikumsplaner.service;

import static de.muenchen.oss.praktikumsplaner.TestUtils.getJwtAuthenticationToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleViewDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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
public class PraktikumsstellenServiceTest {
    @Spy
    private PraktikumsstellenMapper mapper = Mappers.getMapper(PraktikumsstellenMapper.class);
    @Mock
    private PraktikumsstellenRepository praktikumsstellenRepository;
    @InjectMocks
    private PraktikumsstellenService service;
    @Mock
    private MeldezeitraumService meldezeitraumService;
    @Mock
    private NwkRepository nwkRepository;

    private final ServiceTestHelper helper = new ServiceTestHelper();

    @BeforeEach
    public void setUp() {
        var authentication = getJwtAuthenticationToken(Authorities.AuthoritiesEnum.AUSBILDUNGSLEITUNG);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testCreatePraktikumsstelleStudium() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");
        Praktikumsstelle studium = new Praktikumsstelle();
        studium.setId(UUID.randomUUID());
        studium.setDienststelle("TEST-001");
        studium.setOertlicheAusbilder("TestoertlicheAusbilder");
        studium.setEmail("test@test.de");
        studium.setTaetigkeiten("Testtätigkeiten");
        studium.setWuensche("Wuensche");
        studium.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        studium.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        studium.setProgrammierkenntnisse("true");
        studium.setStudiensemester(Set.of(Studiensemester.SEMESTER1));
        studium.setRichtung(Bildungsrichtung.BSC);
        studium.setMeldezeitraumID(meldezeitraumDto.id());

        CreatePraktikumsstelleDto createDto = CreatePraktikumsstelleDto.builder()
                .dienststelle("TEST-001").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten").wuensche("Wuensche")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .programmierkenntnisse("true").studiensemester(Set.of(Studiensemester.SEMESTER1)).richtung(Bildungsrichtung.BSC).build();

        PraktikumsstelleViewDto dto = helper.createPraktikumsstelleDto(studium);
        when(praktikumsstellenRepository.save(studium)).thenReturn(studium);
        when(mapper.toDto(studium)).thenReturn(dto);
        when(mapper.toEntity(createDto, meldezeitraumDto)).thenReturn(studium);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);

        var result = service.normalizeAndSave(createDto);
        assertEquals(dto, result);
    }

    @Test
    public void testCreatePraktikumsstelleAusbildung() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), "gestern bis morgen");
        Praktikumsstelle ausbildung = new Praktikumsstelle();
        ausbildung.setId(UUID.randomUUID());
        ausbildung.setDienststelle("TEST-001");
        ausbildung.setOertlicheAusbilder("TestoertlicheAusbilder");
        ausbildung.setEmail("test@test.de");
        ausbildung.setTaetigkeiten("Testtätigkeiten");
        ausbildung.setWuensche("Wuensche");
        ausbildung.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        ausbildung.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        ausbildung.setAusbildungsjahr(Set.of(Ausbildungsjahr.JAHR1));
        ausbildung.setRichtung(Bildungsrichtung.FISI);
        ausbildung.setMinderjaehrigMoeglich(true);
        ausbildung.setMeldezeitraumID(meldezeitraumDto.id());

        CreatePraktikumsstelleDto createDto = CreatePraktikumsstelleDto.builder()
                .dienststelle("TEST-001").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten").wuensche("Wuensche")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .minderjaehrigMoeglich(true).ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR1)).richtung(Bildungsrichtung.FISI).build();

        PraktikumsstelleViewDto dto = helper.createPraktikumsstelleDto(ausbildung);
        when(praktikumsstellenRepository.save(ausbildung)).thenReturn(ausbildung);
        when(mapper.toEntity(createDto, meldezeitraumDto)).thenReturn(ausbildung);
        when(mapper.toDto(ausbildung)).thenReturn(dto);
        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);

        var result = service.normalizeAndSave(createDto);
        assertEquals(dto, result);
    }

    @Test
    public void testGetRecentPraktikumsstellen() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().minusDays(1), "letzte woche");
        Praktikumsstelle a1 = helper.createAusbildungsPraktikumsstelleEntity("ITM-SLP31", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", null, Dringlichkeit.ZWINGEND, Set.of(Ausbildungsjahr.JAHR2), Bildungsrichtung.FISI, false, true,
                meldezeitraumDto.id(), null);
        Praktikumsstelle a2 = helper.createAusbildungsPraktikumsstelleEntity("ITM-DKL22", "Erika Mustermann", "erika@mustermann.de",
                "Einarbeitung für Übernahme", null, Dringlichkeit.DRINGEND, Set.of(Ausbildungsjahr.JAHR3), Bildungsrichtung.FISI, true, false,
                meldezeitraumDto.id(), null);
        Praktikumsstelle s1 = helper.createStudiumsPraktikumsstelleEntity("ITM-SLP33", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", null, Dringlichkeit.NACHRANGIG, Set.of(Studiensemester.SEMESTER5), Bildungsrichtung.BSC, "true",
                meldezeitraumDto.id(), null);
        Praktikumsstelle s2 = helper.createStudiumsPraktikumsstelleEntity("ITM-DKL-IL", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", null, Dringlichkeit.NACHRANGIG, Set.of(Studiensemester.SEMESTER5), Bildungsrichtung.BWI, "false",
                meldezeitraumDto.id(), null);
        Praktikumsstelle s3 = helper.createStudiumsPraktikumsstelleEntity("ITM-GL13", "John Smith", "John@smith.com",
                "Planung von Events", null, Dringlichkeit.ZWINGEND, Set.of(Studiensemester.SEMESTER3), Bildungsrichtung.BWI, "true", meldezeitraumDto.id(),
                null);

        when(meldezeitraumService.getMostRecentPassedMeldezeitraum()).thenReturn(meldezeitraumDto);
        when(praktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumDto.id()))
                .thenReturn(Arrays.asList(s1, s2, s3, a1, a2));
        when(mapper.toDto(any(Praktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((Praktikumsstelle) invocation.getArguments()[0]));

        List<PraktikumsstelleViewDto> result = service.getRecentPraktikumsstellen();
        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testGetAllInCurrentMeldezeitraum() {
        MeldezeitraumDto meldezeitraumDto = helper.createMeldezeitraumDto(LocalDate.now().minusDays(8), LocalDate.now().plusDays(1), "letzte woche bis morgen");
        Praktikumsstelle a1 = helper.createAusbildungsPraktikumsstelleEntity("ITM-SLP31", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", null, Dringlichkeit.ZWINGEND, Set.of(Ausbildungsjahr.JAHR2), Bildungsrichtung.FISI, false, false,
                meldezeitraumDto.id(), null);
        Praktikumsstelle a2 = helper.createAusbildungsPraktikumsstelleEntity("ITM-DKL22", "Erika Mustermann", "erika@mustermann.de",
                "Einarbeitung für Übernahme", null, Dringlichkeit.DRINGEND, Set.of(Ausbildungsjahr.JAHR3), Bildungsrichtung.FISI, true, false,
                meldezeitraumDto.id(), null);
        Praktikumsstelle s1 = helper.createStudiumsPraktikumsstelleEntity("ITM-SLP33", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", null, Dringlichkeit.NACHRANGIG, Set.of(Studiensemester.SEMESTER5), Bildungsrichtung.BSC, "true",
                meldezeitraumDto.id(), null);
        Praktikumsstelle s2 = helper.createStudiumsPraktikumsstelleEntity("ITM-DKL-IL", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", null, Dringlichkeit.NACHRANGIG, Set.of(Studiensemester.SEMESTER5), Bildungsrichtung.BWI, "false",
                meldezeitraumDto.id(), null);
        Praktikumsstelle s3 = helper.createStudiumsPraktikumsstelleEntity("ITM-GL13", "John Smith", "John@smith.com",
                "Planung von Events", null, Dringlichkeit.ZWINGEND, Set.of(Studiensemester.SEMESTER3), Bildungsrichtung.BWI, "true", meldezeitraumDto.id(),
                null);

        when(meldezeitraumService.getCurrentMeldezeitraum()).thenReturn(meldezeitraumDto);
        when(praktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumDto.id()))
                .thenReturn(Arrays.asList(s1, s2, s3, a1, a2));
        when(mapper.toDto(any(Praktikumsstelle.class)))
                .thenAnswer(invocation -> helper.createPraktikumsstelleDto((Praktikumsstelle) invocation.getArguments()[0]));

        List<PraktikumsstelleViewDto> result = service.getAllInCurrentMeldezeitraum();
        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testAssignNwkAndUnassignAndDelete() {
        Nwk assigningNwk = new Nwk();
        assigningNwk.setId(UUID.randomUUID());
        Praktikumsstelle stelle = helper.createAusbildungsPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "asubider@email.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Set.of(Ausbildungsjahr.JAHR2), Bildungsrichtung.FISI, false, false, UUID.randomUUID(), null);
        Praktikumsstelle withAssigned = helper.createAusbildungsPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "asubider@email.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Set.of(Ausbildungsjahr.JAHR2), Bildungsrichtung.FISI, false, false, stelle.getMeldezeitraumID(), assigningNwk);
        withAssigned.setId(stelle.getId());

        when(praktikumsstellenRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(assigningNwk));
        when(praktikumsstellenRepository.save(any(Praktikumsstelle.class))).thenReturn(withAssigned);

        // assignedNwk may be partially mapped; compare on assignedNwk ID only
        var assignedDto = service.assignNwk(stelle.getId(), assigningNwk.getId());
        assertNotNull(assignedDto.assignedNwk());
        assertEquals(assigningNwk.getId(), assignedDto.assignedNwk().id());

        // Unassign
        when(praktikumsstellenRepository.findById(stelle.getId())).thenReturn(Optional.of(withAssigned));
        when(praktikumsstellenRepository.save(any(Praktikumsstelle.class))).thenReturn(stelle);
        var unassignedDto = service.unassignNwk(stelle.getId());
        assertEquals(stelle.getId(), unassignedDto.id());
        assertNull(unassignedDto.assignedNwk());

        // Delete
        when(praktikumsstellenRepository.existsById(stelle.getId())).thenReturn(true);
        service.deletePraktikumsstelle(stelle.getId());
        verify(praktikumsstellenRepository, times(1)).deleteById(stelle.getId());
    }

    @Test
    public void testAssignNwkConflictsAndNotFound() {
        Nwk nwk = new Nwk();
        nwk.setId(UUID.randomUUID());
        Praktikumsstelle stelle = helper.createStudiumsPraktikumsstelleEntity("ITM-SLP33", "Ausbilder", "asubider@email.de", "Alles", null,
                Dringlichkeit.ZWINGEND, Set.of(Studiensemester.SEMESTER1), Bildungsrichtung.BWI, "false", UUID.randomUUID(), null);
        stelle.setAssignedNwk(nwk);

        when(praktikumsstellenRepository.findById(stelle.getId())).thenReturn(Optional.of(stelle));
        when(nwkRepository.findById(any(UUID.class))).thenReturn(Optional.of(nwk));
        assertThrows(ResourceConflictException.class, () -> service.assignNwk(stelle.getId(), nwk.getId()));

        when(praktikumsstellenRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.assignNwk(UUID.randomUUID(), UUID.randomUUID()));
    }
}
