package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.mappers.MeldezeitraumMapper;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.repository.MeldezeitraumRepository;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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

        assertNotNull(dto);
    }

    @Test
    public void testGetCurrentMeldezeitraumSuccess() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now().plusDays(1);
        String name = "gestern bis morgen";

        Meldezeitraum meldezeitraum = new Meldezeitraum();
        meldezeitraum.setId(UUID.randomUUID());
        meldezeitraum.setStartZeitpunkt(start);
        meldezeitraum.setEndZeitpunkt(end);
        meldezeitraum.setZeitraumName(name);

        MeldezeitraumDTO meldezeitraumDTO = MeldezeitraumDTO.builder()
                .startZeitpunkt(start)
                .endZeitpunkt(end)
                .zeitraumName(name)
                .build();

        when(repository.findMeldezeitraumByDateInRange(LocalDate.now())).thenReturn(meldezeitraum);
        when(mapper.toDto(any(Meldezeitraum.class))).thenReturn(meldezeitraumDTO);

        assertEquals(service.getCurrentMeldezeitraum(), mapper.toDto(meldezeitraum));
    }

    @Test
    public void testGetCurrentMeldezeitraumFailure() {
        when(repository.findMeldezeitraumByDateInRange(LocalDate.now())).thenReturn(null);

        assertThrows(ValidationException.class, () -> service.getCurrentMeldezeitraum());
    }

    @Test
    public void testcheckOverlappingMeldezeitraumExists() {
        LocalDate start = LocalDate.of(2, 2, 2);
        LocalDate end = LocalDate.of(3, 3, 3);
        LocalDate overlapStart = LocalDate.of(1, 1, 1);
        String name = "Dawn of Time";

        Meldezeitraum meldezeitraum = new Meldezeitraum();
        meldezeitraum.setId(UUID.randomUUID());
        meldezeitraum.setStartZeitpunkt(start);
        meldezeitraum.setEndZeitpunkt(end);
        meldezeitraum.setZeitraumName(name);

        CreateMeldezeitraumDTO createMeldezeitraumDTO = CreateMeldezeitraumDTO.builder()
                .startZeitpunkt(overlapStart)
                .endZeitpunkt(start)
                .zeitraumName(name)
                .build();

        when(repository.isOverlappingMeldezeitraum(createMeldezeitraumDTO.startZeitpunkt(),
                createMeldezeitraumDTO.endZeitpunkt())).thenReturn(true);

        assertThrows(ValidationException.class, () -> service.checkOverlappingMeldezeitraum(createMeldezeitraumDTO));
    }

    @Test
    public void testcheckNoOverlappingMeldezeitraum() {
        LocalDate start = LocalDate.of(2, 2, 2);
        LocalDate end = LocalDate.of(3, 3, 3);
        LocalDate newStart = LocalDate.of(5, 5, 5);
        LocalDate newEnd = LocalDate.of(6, 6, 6);
        String name = "2-6";

        Meldezeitraum meldezeitraum = new Meldezeitraum();
        meldezeitraum.setId(UUID.randomUUID());
        meldezeitraum.setStartZeitpunkt(start);
        meldezeitraum.setEndZeitpunkt(end);
        meldezeitraum.setZeitraumName(name);

        CreateMeldezeitraumDTO createMeldezeitraumDTO = CreateMeldezeitraumDTO.builder()
                .startZeitpunkt(newStart)
                .endZeitpunkt(newEnd)
                .zeitraumName(name)
                .build();

        when(repository.isOverlappingMeldezeitraum(createMeldezeitraumDTO.startZeitpunkt(),
                createMeldezeitraumDTO.endZeitpunkt())).thenReturn(false);

        assertDoesNotThrow(() -> service.checkOverlappingMeldezeitraum(createMeldezeitraumDTO));
    }
}
