package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NwkMapper;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.val;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NwkServiceTest {
    @Spy
    private NwkMapper mapper = Mappers.getMapper(NwkMapper.class);
    @Mock
    private NwkRepository repository;
    @Mock
    private MeldezeitraumService meldezeitraumService;
    @InjectMocks
    private NwkService service;
    @Mock
    private ExcelImportService excelImportService;

    private final ServiceTestHelper helper = new ServiceTestHelper();

    @Test
    public void testCreateNwkSuccess() {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "21/24";
        final Set<DayOfWeek> vorlesungstage = new HashSet<>();
        final boolean isActive = true;
        vorlesungstage.add(DayOfWeek.MONDAY);
        vorlesungstage.add(DayOfWeek.TUESDAY);

        Nwk nwk = new Nwk(vorname, nachname, studiengang, null, jahrgang, vorlesungstage, isActive);
        NwkDto nwkDto = NwkDto.builder().id(nwk.getId()).vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).active(isActive).build();
        CreateNwkDto createNwkDto = CreateNwkDto.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        when(repository.save(any(Nwk.class))).thenReturn(nwk);

        NwkDto result = service.saveNwk(createNwkDto);

        assertNotNull(result);
        assertEquals(result, nwkDto);
    }

    @Test
    public void testimportNwk() throws IOException {
        final String base64 = "WAAAAAAGGHHH=";
        final int EXCEL_TO_NWK_DTO_LIST_EXECUTIONS = 1;

        CreateNwkDto createNwkDto = CreateNwkDto.builder().vorname("Max").nachname("Mustermann").studiengang(Studiengang.BSC).ausbildungsrichtung(null)
                .jahrgang("21/24")
                .vorlesungstage(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)).build();

        List<CreateNwkDto> createNwkDtos = new ArrayList<>();
        createNwkDtos.add(createNwkDto);
        createNwkDtos.add(createNwkDto);
        createNwkDtos.add(createNwkDto);

        when(excelImportService.excelToNwkDtoList(base64)).thenReturn(createNwkDtos);
        service.importNwk(base64);
        verify(excelImportService, times(EXCEL_TO_NWK_DTO_LIST_EXECUTIONS)).excelToNwkDtoList(base64);
        verify(repository, times(createNwkDtos.size())).save(any(Nwk.class));
    }

    @Test
    public void testImportNwkFailed() throws IOException {
        val base64 = "WAAAAAAGGHHH=";
        when(excelImportService.excelToNwkDtoList(base64)).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> service.importNwk(base64));
        verifyNoInteractions(repository);
    }

    @Test
    public void testFindAllActiveNwks() {
        Nwk nwk1 = helper.createNwkEntity("Max", "Mustermann", Studiengang.BSC, null, "21/24", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), true);
        Nwk nwk2 = helper.createNwkEntity("Erika", "Musterfrau", Studiengang.BSC, null, "21/24", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), true);
        Nwk nwk3 = helper.createNwkEntity("Hans", "Peter", Studiengang.VI, null, "21/25", Set.of(DayOfWeek.MONDAY), true);
        Nwk nwk4 = helper.createNwkEntity("Anna", "Müller", null, Ausbildungsrichtung.FISI, "21/23", Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY), true);

        List<Nwk> nwks = List.of(nwk1, nwk2, nwk3, nwk4);

        when(repository.findNwksByActiveIsTrueOrderByNachname()).thenReturn(nwks);
        assertEquals(service.findAllActiveNwks(), nwks.stream().map(mapper::toDto).toList());
    }

    @Test
    public void testFindAllUnassigned() {
        Nwk nwk1 = helper.createNwkEntity("Max", "Mustermann", Studiengang.BSC, null, "21/24", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), true);
        Nwk nwk2 = helper.createNwkEntity("Erika", "Musterfrau", Studiengang.BSC, null, "21/24", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), true);
        Nwk nwk3 = helper.createNwkEntity("Hans", "Peter", Studiengang.VI, null, "21/25", Set.of(DayOfWeek.MONDAY), true);
        Nwk nwk4 = helper.createNwkEntity("Anna", "Müller", null, Ausbildungsrichtung.FISI, "21/23", Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY), true);

        List<Nwk> nwks = List.of(nwk1, nwk2, nwk3, nwk4);
        when(meldezeitraumService.getMostRecentPassedMeldezeitraum())
                .thenReturn(new MeldezeitraumDto(UUID.randomUUID(), "", new ZeitraumDto(LocalDate.now(), LocalDate.now())));
        when(repository.findAllUnassignedInSpecificMeldzeitraum(any(UUID.class))).thenReturn(nwks);
        assertEquals(service.findAllUnassignedNwksInCurrentMeldezeitraum(), nwks.stream().map(mapper::toDto).toList());
    }

    @Test
    public void testFindAllNwks() {
        Nwk nwk1 = helper.createNwkEntity("Max", "Mustermann", Studiengang.BSC, null, "21/24", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), true);
        Nwk nwk2 = helper.createNwkEntity("Erika", "Musterfrau", Studiengang.BSC, null, "21/24", Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), true);
        Nwk nwk3 = helper.createNwkEntity("Hans", "Peter", Studiengang.VI, null, "21/25", Set.of(DayOfWeek.MONDAY), true);
        Nwk nwk4 = helper.createNwkEntity("Anna", "Müller", null, Ausbildungsrichtung.FISI, "21/23", Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY), true);

        List<Nwk> nwks = List.of(nwk1, nwk2, nwk3, nwk4);

        when(repository.findAll()).thenReturn(nwks);
        assertEquals(service.findAllNwks(), nwks.stream().map(mapper::toDto).toList());
    }
}
