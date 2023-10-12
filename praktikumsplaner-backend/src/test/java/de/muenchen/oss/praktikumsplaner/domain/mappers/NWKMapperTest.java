package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
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
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "22/26";
        final String vorlesungstage = "Mo + Di";

        NWK nwk = new NWK(nachname, vorname, studiengang, jahrgang, vorlesungstage);
        NwkDTO nwkDTO = mapper.toDTO(nwk);

        assertEquals(nwk.getNachname(), nwkDTO.nachname());
        assertEquals(nwk.getVorname(), nwkDTO.vorname());
        assertEquals(nwk.getStudiengang(), nwkDTO.studiengang());
        assertEquals(nwk.getJahrgang(), nwkDTO.jahrgang());
        assertEquals(nwk.getId(), nwkDTO.id());

    }

    @Test
    public void testNwkDtoToEntity() {
        final String nachname = "mustermann";
        final String vorname = "max";
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "22/26";
        final String vorlesungstage = "Mo + Di";
        final UUID id = UUID.randomUUID();

        NwkDTO nwkDTO = NwkDTO.builder().id(id).vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang).vorlesungstage(vorlesungstage)
                .build();

        NWK nwk = mapper.toEntity(nwkDTO);

        assertEquals(nwkDTO.nachname(), nwk.getNachname());
        assertEquals(nwkDTO.vorname(), nwk.getVorname());
        assertEquals(nwkDTO.studiengang(), nwk.getStudiengang());
        assertEquals(nwkDTO.jahrgang(), nwk.getJahrgang());
        assertEquals(nwkDTO.id(), nwk.getId());
    }

    @Test
    public void testNull() {
        assertNull(mapper.toDTO(null));
        assertNull(mapper.toEntity((NwkDTO) null));
        assertNull(mapper.toEntity((CreateNwkDTO) null));
    }
}
