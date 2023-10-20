package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.mappers.MeldezeitraumMapper;
import de.muenchen.oss.praktikumsplaner.dtos.CreateMeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.repository.MeldezeitraumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeldezeitraumServiceTest {
    @Spy
    private MeldezeitraumMapper mapper = Mappers.getMapper(MeldezeitraumMapper.class);
    @Mock
    private MeldezeitraumRepository repository;
    @InjectMocks
    private MeldezeitraumService service;

    @Test
    public void testCreateMeldezeitraum() {
        LocalDate start = LocalDate.of(2020, 10, 10);
        LocalDate end = LocalDate.of(2020, 11, 11);
        String name = "Der Name";

        CreateMeldezeitraumDTO createMeldezeitraumDTO = CreateMeldezeitraumDTO.builder()
                .startZeitpunkt(start)
                .endZeitpunkt(end)
                .zeitraumName(name)
                .build();

        Meldezeitraum meldezeitraum = new Meldezeitraum();
        meldezeitraum.setId(UUID.randomUUID());
        meldezeitraum.setStartZeitpunkt(start);
        meldezeitraum.setEndZeitpunkt(end);
        meldezeitraum.setZeitraumName(name);

        when(repository.save(any(Meldezeitraum.class))).thenReturn(meldezeitraum);

        MeldezeitraumDTO dto = service.createMeldezeitraum(createMeldezeitraumDTO);

        assertEquals(dto.id(), meldezeitraum.getId());
        assertEquals(dto.startZeitpunkt(), createMeldezeitraumDTO.startZeitpunkt());
        assertEquals(dto.endZeitpunkt(), createMeldezeitraumDTO.endZeitpunkt());
        assertEquals(dto.zeitraumName(), createMeldezeitraumDTO.zeitraumName());

    }
}
