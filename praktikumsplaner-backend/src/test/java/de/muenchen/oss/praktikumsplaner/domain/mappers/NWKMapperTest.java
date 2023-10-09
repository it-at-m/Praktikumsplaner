package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NWKMapperTest {
    private final NWKMapper mapper = Mappers.getMapper(NWKMapper.class);

    @Test
    public void testNWKToDto() {
        final String nachname = "mustermann";
        final String vorname = "max";
        final String studiengang = "BSC";
        final String jahrgang = "22/26";
        final String vorlesungstage = "Mo + Di";

        NWK nwk = new NWK(nachname, vorname, studiengang, jahrgang, vorlesungstage);
        NwkDTO nwkDTO = mapper.toDTO(nwk);

        assertEquals(nwk.getNachname(), nwkDTO.getNachname());
        assertEquals(nwk.getVorname(), nwkDTO.getVorname());
        assertEquals(nwk.getStudiengang(), nwkDTO.getStudiengang());
        assertEquals(nwk.getJahrgang(), nwkDTO.getJahrgang());
        assertEquals(nwk.getId(), nwkDTO.getId());

    }

    @Test
    public void testNwkDtoToEntity() {
        final String nachname = "mustermann";
        final String vorname = "max";
        final String studiengang = "BSC";
        final String jahrgang = "22/26";
        final String vorlesungstage = "Mo + Di";
        final UUID id = UUID.randomUUID();

        NwkDTO nwkDTO = new NwkDTO(id, nachname, vorname, studiengang, jahrgang, vorlesungstage);

        NWK nwk = mapper.toEntity(nwkDTO);

        assertEquals(nwkDTO.getNachname(), nwk.getNachname());
        assertEquals(nwkDTO.getVorname(), nwk.getVorname());
        assertEquals(nwkDTO.getStudiengang(), nwk.getStudiengang());
        assertEquals(nwkDTO.getJahrgang(), nwk.getJahrgang());
        assertEquals(nwkDTO.getId(), nwk.getId());
    }

    @Test
    public void testNull() {
        assertNull(mapper.toDTO(null));
        assertNull(mapper.toEntity(null));
    }
}
