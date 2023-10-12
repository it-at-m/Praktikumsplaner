package de.muenchen.oss.praktikumsplaner.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import java.io.IOException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class NWKServiceTest {
    @Mock
    private NWKMapper mapper;
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

        when(mapper.toEntity(createNwkDTO)).thenReturn(nwk);
        when(mapper.toDTO(nwk)).thenReturn(nwkDTO);
        when(repository.save(eq(nwk))).thenReturn(nwk);

        NwkDTO result = service.saveNWK(createNwkDTO);

        assertNotNull(result);
        assertEquals(result.nachname(), createNwkDTO.nachname());
    }

    @Test
    public void testimportNWK() throws IOException {
        final String base64 = "WAAAAAAGGHHH=";

        service.importNWK(base64);
        Mockito.verify(excelService, Mockito.times(1)).excelToNwkDTOList(base64);
    }
}
