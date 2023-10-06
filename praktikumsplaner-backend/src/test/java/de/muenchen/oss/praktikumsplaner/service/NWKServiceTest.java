package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.rest.NWKRepository;
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

        NWK nwk = new NWK(nachname, vorname, studiengang, jahrgang);
        NwkDTO nwkDTO = new NwkDTO(nwk.getId(), nwk.getNachname(), nwk.getVorname(), nwk.getStudiengang(), nwk.getJahrgang());

        Mockito.when(mapper.toEntity(nwkDTO)).thenReturn(nwk);
        Mockito.when(mapper.toDTO(nwk)).thenReturn(nwkDTO);
        Mockito.when(repository.save(Mockito.eq(nwk))).thenReturn(nwk);

        NwkDTO result = service.saveNWK(nwkDTO);

        assertNotNull(result);
        assertEquals(result.getNachname(), nwkDTO.getNachname());

    }

    @Test
    public void testCreateNWKFromList() {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final String studiengang = "BSC";
        final String jahrgang = "21/24";

        NWK nwk = new NWK(nachname, vorname, studiengang, jahrgang);
        NwkDTO nwkDTO = new NwkDTO(nwk.getId(), nwk.getNachname(), nwk.getVorname(), nwk.getStudiengang(), nwk.getJahrgang());
        List<NwkDTO> nwkDTOS = new ArrayList<>();
        nwkDTOS.add(nwkDTO);

        Mockito.when(mapper.toEntity(nwkDTO)).thenReturn(nwk);
        Mockito.when(mapper.toDTO(nwk)).thenReturn(nwkDTO);
        Mockito.when(repository.save(Mockito.eq(nwk))).thenReturn(nwk);

        List<NwkDTO> result = service.saveNWK(nwkDTOS);

        assertNotNull(result);
        assertEquals(result.get(0).getNachname(), nwkDTOS.get(0).getNachname());
    }
}
