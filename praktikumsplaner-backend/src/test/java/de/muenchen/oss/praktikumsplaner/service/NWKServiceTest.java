package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NWKServiceTest {
    @Spy
    private NWKMapper mapper = Mappers.getMapper(NWKMapper.class);
    @Mock
    private NWKRepository repository;
    @InjectMocks
    private NWKService service;
    @Mock
    private ExcelService excelService;

    @Test
    public void testCreateNWKSuccess() {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "21/24";
        final Set<DayOfWeek> vorlesungstage = new HashSet<>();
        vorlesungstage.add(DayOfWeek.MONDAY);
        vorlesungstage.add(DayOfWeek.TUESDAY);

        NWK nwk = new NWK(vorname, nachname, studiengang, jahrgang, vorlesungstage);
        NwkDTO nwkDTO = NwkDTO.builder().id(nwk.getId()).vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();
        CreateNwkDTO createNwkDTO = CreateNwkDTO.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        when(repository.save(any(NWK.class))).thenReturn(nwk);

        NwkDTO result = service.saveNWK(createNwkDTO);

        assertNotNull(result);
        assertEquals(result, nwkDTO);
    }

    @Test
    public void testimportNWK() throws IOException {
        final String base64 = "WAAAAAAGGHHH=";
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "21/24";
        final Set<DayOfWeek> vorlesungstage = new HashSet<>();
        vorlesungstage.add(DayOfWeek.MONDAY);
        vorlesungstage.add(DayOfWeek.TUESDAY);
        final int EXCEL_TO_NWK_DTO_LIST_EXECUTIONS = 1;

        CreateNwkDTO createNwkDTO = CreateNwkDTO.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        List<CreateNwkDTO> createNwkDTOS = new ArrayList<>();
        createNwkDTOS.add(createNwkDTO);
        createNwkDTOS.add(createNwkDTO);
        createNwkDTOS.add(createNwkDTO);

        when(excelService.excelToNwkDTOList(base64)).thenReturn(createNwkDTOS);
        service.importNWK(base64);
        verify(excelService, times(EXCEL_TO_NWK_DTO_LIST_EXECUTIONS)).excelToNwkDTOList(base64);
        verify(repository, times(createNwkDTOS.size())).save(any(NWK.class));
    }

    @Test
    public void testImportNWKFailed() throws IOException {
        val base64 = "WAAAAAAGGHHH=";
        when(excelService.excelToNwkDTOList(base64)).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> service.importNWK(base64));
        verifyNoInteractions(repository);
    }
}
