package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.MeldezeitraumMapper;
import de.muenchen.oss.praktikumsplaner.repository.MeldezeitraumRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;
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

        ZeitraumDto zeitraum = ZeitraumDto.builder().startZeitpunkt(start).endZeitpunkt(end).build();

        CreateMeldezeitraumDto createMeldezeitraumDto = CreateMeldezeitraumDto.builder()
                .zeitraum(zeitraum)
                .zeitraumName(name)
                .build();

        Meldezeitraum meldezeitraum = new Meldezeitraum();
        meldezeitraum.setId(UUID.randomUUID());
        meldezeitraum.setStartZeitpunkt(start);
        meldezeitraum.setEndZeitpunkt(end);
        meldezeitraum.setZeitraumName(name);

        when(repository.save(any(Meldezeitraum.class))).thenReturn(meldezeitraum);

        MeldezeitraumDto dto = service.createMeldezeitraum(createMeldezeitraumDto);

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

        ZeitraumDto zeitraum = ZeitraumDto.builder().startZeitpunkt(start).endZeitpunkt(end).build();

        MeldezeitraumDto meldezeitraumDto = MeldezeitraumDto.builder()
                .zeitraum(zeitraum)
                .zeitraumName(name)
                .build();

        when(repository.findMeldezeitraumByDateInRange(LocalDate.now())).thenReturn(meldezeitraum);
        when(mapper.toDto(any(Meldezeitraum.class))).thenReturn(meldezeitraumDto);

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

        ZeitraumDto zeitraum = ZeitraumDto.builder().startZeitpunkt(overlapStart).endZeitpunkt(start).build();

        CreateMeldezeitraumDto createMeldezeitraumDto = CreateMeldezeitraumDto.builder()
                .zeitraum(zeitraum)
                .zeitraumName(name)
                .build();

        when(repository.isOverlappingMeldezeitraum(createMeldezeitraumDto.zeitraum().startZeitpunkt(),
                createMeldezeitraumDto.zeitraum().endZeitpunkt())).thenReturn(true);

        assertThrows(ValidationException.class, () -> service.checkOverlappingMeldezeitraum(createMeldezeitraumDto));
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

        ZeitraumDto zeitraum = ZeitraumDto.builder().startZeitpunkt(newStart).endZeitpunkt(newEnd).build();

        CreateMeldezeitraumDto createMeldezeitraumDto = CreateMeldezeitraumDto.builder()
                .zeitraum(zeitraum)
                .zeitraumName(name)
                .build();

        when(repository.isOverlappingMeldezeitraum(createMeldezeitraumDto.zeitraum().startZeitpunkt(),
                createMeldezeitraumDto.zeitraum().endZeitpunkt())).thenReturn(false);

        assertDoesNotThrow(() -> service.checkOverlappingMeldezeitraum(createMeldezeitraumDto));
    }

    @Test
    public void testGetMostRecentMeldezeitraum() {
        String name = "Name";

        Meldezeitraum mostRecent = new Meldezeitraum();
        mostRecent.setId(UUID.randomUUID());
        mostRecent.setStartZeitpunkt(LocalDate.now().minusDays(10));
        mostRecent.setEndZeitpunkt(LocalDate.now().minusDays(6));
        mostRecent.setZeitraumName(name);

        Meldezeitraum oldest = new Meldezeitraum();
        oldest.setId(UUID.randomUUID());
        oldest.setStartZeitpunkt(LocalDate.now().minusDays(15));
        oldest.setEndZeitpunkt(LocalDate.now().minusDays(11));
        oldest.setZeitraumName(name);

        List<Meldezeitraum> meldezeitraume = List.of(mostRecent, oldest, mostRecent);

        when(repository.findByEndZeitpunktBeforeOrderByEndZeitpunktDesc(LocalDate.now())).thenReturn(meldezeitraume);
        assertEquals(service.getMostRecentPassedMeldezeitraum(), mapper.toDto(mostRecent));
    }

    @Test
    public void testGetMostRecentMeldezeitraumNoMatchingMeldezeitraum() {

        List<Meldezeitraum> meldezeitraume = List.of();

        when(repository.findByEndZeitpunktBeforeOrderByEndZeitpunktDesc(LocalDate.now())).thenReturn(meldezeitraume);
        assertThrows(EntityNotFoundException.class, () -> service.getMostRecentPassedMeldezeitraum());
    }

    @Test
    public void testGetUpcomingMeldezeitraeume() {
        String name = "Name";

        Meldezeitraum first = new Meldezeitraum();
        first.setId(UUID.randomUUID());
        first.setStartZeitpunkt(LocalDate.now().plusDays(10));
        first.setEndZeitpunkt(LocalDate.now().plusDays(6));
        first.setZeitraumName(name);

        Meldezeitraum second = new Meldezeitraum();
        second.setId(UUID.randomUUID());
        second.setStartZeitpunkt(LocalDate.now().plusDays(15));
        second.setEndZeitpunkt(LocalDate.now().plusDays(11));
        second.setZeitraumName(name);

        List<Meldezeitraum> meldezeitraume = List.of(first, second);

        when(repository.findByStartZeitpunktAfterOrderByStartZeitpunktAsc(LocalDate.now())).thenReturn(meldezeitraume);
        assertEquals(service.getUpcomingMeldezeitraeume(), meldezeitraume.stream().map(mapper::toDto).toList());
    }

    @Test
    public void testGetPassedMeldezeitraeume() {
        String name = "Name";

        Meldezeitraum first = new Meldezeitraum();
        first.setId(UUID.randomUUID());
        first.setStartZeitpunkt(LocalDate.now().minusDays(10));
        first.setEndZeitpunkt(LocalDate.now().minusDays(6));
        first.setZeitraumName(name);

        Meldezeitraum second = new Meldezeitraum();
        second.setId(UUID.randomUUID());
        second.setStartZeitpunkt(LocalDate.now().minusDays(15));
        second.setEndZeitpunkt(LocalDate.now().minusDays(11));
        second.setZeitraumName(name);

        List<Meldezeitraum> meldezeitraume = List.of(first, second);

        when(repository.findByEndZeitpunktBeforeOrderByEndZeitpunktDesc(LocalDate.now())).thenReturn(meldezeitraume);
        assertEquals(service.getPassedMeldezeitraeume(), meldezeitraume.stream().map(mapper::toDto).toList());
    }

    @Test
    public void testGetAllMeldezeitraeume() {
        String name = "Name";

        Meldezeitraum mostRecent = new Meldezeitraum();
        mostRecent.setId(UUID.randomUUID());
        mostRecent.setStartZeitpunkt(LocalDate.now().minusDays(10));
        mostRecent.setEndZeitpunkt(LocalDate.now().minusDays(6));
        mostRecent.setZeitraumName(name);

        Meldezeitraum oldest = new Meldezeitraum();
        oldest.setId(UUID.randomUUID());
        oldest.setStartZeitpunkt(LocalDate.now().minusDays(15));
        oldest.setEndZeitpunkt(LocalDate.now().minusDays(11));
        oldest.setZeitraumName(name);

        List<Meldezeitraum> meldezeitraume = List.of(mostRecent, oldest, mostRecent);

        when(repository.findAll()).thenReturn(meldezeitraume);
        assertEquals(service.getAllMeldezeitraeume(), meldezeitraume.stream().map(mapper::toDto).toList());
    }
}
