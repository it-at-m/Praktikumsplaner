package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapperImpl;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        final String vorlesungstage = "Mo + Di";

        NWK nwk = new NWK(nachname, vorname, studiengang, jahrgang, vorlesungstage);
        NwkDTO nwkDTO = NwkDTO.builder().id(nwk.getId()).vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();
        CreateNwkDTO createNwkDTO = CreateNwkDTO.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        when(repository.save(eq(nwk))).thenReturn(nwk);

        NwkDTO result = service.saveNWK(createNwkDTO);

        assertNotNull(result);
        assertSame(result, nwkDTO);
    }

    @Test
    public void testimportNWK() throws IOException {
        final String base64 = "WAAAAAAGGHHH=";
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "21/24";
        final String vorlesungstage = "Mo + Di";
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
}
