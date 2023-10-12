package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class NWKServiceTest {
    @Mock
    private NWKMapper mapper;
    @Mock
    private NWKRepository repository;
    @InjectMocks
    private NWKService service;

    @Test
    public void testCreateNWKSuccess() {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final String studiengang = "BSC";
        final String jahrgang = "21/24";
        final String vorlesungstage = "Mo + Di";

        NWK nwk = new NWK(nachname, vorname, studiengang, jahrgang, vorlesungstage);
        NwkDTO nwkDTO = NwkDTO.builder().id(nwk.getId()).vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();
        CreateNwkDTO createNwkDTO = CreateNwkDTO.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        Mockito.when(mapper.toEntity(createNwkDTO)).thenReturn(nwk);
        Mockito.when(mapper.toDTO(nwk)).thenReturn(nwkDTO);
        Mockito.when(repository.save(Mockito.eq(nwk))).thenReturn(nwk);

        NwkDTO result = service.saveNWK(createNwkDTO);

        assertNotNull(result);
        assertEquals(result.nachname(), createNwkDTO.nachname());
    }

    @Test
    public void testCreateNWKFromList() {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final String studiengang = "BSC";
        final String jahrgang = "21/24";
        final String vorlesungstage = "Mo + Di";

        NWK nwk = new NWK(nachname, vorname, studiengang, jahrgang, vorlesungstage);
        NwkDTO nwkDTO = NwkDTO.builder().id(nwk.getId()).vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();
        CreateNwkDTO createNwkDTO = CreateNwkDTO.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();
        List<CreateNwkDTO> createNwkDTOS = new ArrayList<>();
        createNwkDTOS.add(createNwkDTO);

        Mockito.when(mapper.toEntity(createNwkDTO)).thenReturn(nwk);
        Mockito.when(mapper.toDTO(nwk)).thenReturn(nwkDTO);
        Mockito.when(repository.save(Mockito.eq(nwk))).thenReturn(nwk);

        List<NwkDTO> result = service.saveNWK(createNwkDTOS);

        assertNotNull(result);
        assertEquals(result.get(0).nachname(), createNwkDTOS.get(0).nachname());
    }
}
