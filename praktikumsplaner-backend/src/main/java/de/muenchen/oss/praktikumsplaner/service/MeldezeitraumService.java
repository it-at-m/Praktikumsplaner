package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.MeldezeitraumMapper;
import de.muenchen.oss.praktikumsplaner.repository.MeldezeitraumRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MeldezeitraumService {
    private final MeldezeitraumMapper meldezeitraumMapper;
    private final MeldezeitraumRepository meldezeitraumRepository;

    public MeldezeitraumDto createMeldezeitraum(final CreateMeldezeitraumDto meldezeitraumCreateDto) {
        checkOverlappingMeldezeitraum(meldezeitraumCreateDto);
        return meldezeitraumMapper.toDto(meldezeitraumRepository.save(meldezeitraumMapper.toEntity(meldezeitraumCreateDto)));
    }

    public MeldezeitraumDto getCurrentMeldezeitraum() throws ValidationException {
        Meldezeitraum currentMeldezeitraum = meldezeitraumRepository
                .findMeldezeitraumByDateInRange(LocalDate.now());
        if (currentMeldezeitraum == null) throw new ValidationException("Kein aktiver Meldezeitraum");
        return meldezeitraumMapper.toDto(currentMeldezeitraum);
    }

    public void checkOverlappingMeldezeitraum(final CreateMeldezeitraumDto meldezeitraumCreateDto) {
        if (meldezeitraumRepository.isOverlappingMeldezeitraum(meldezeitraumCreateDto.startZeitpunkt(),
                meldezeitraumCreateDto.endZeitpunkt())) {
            throw new ValidationException("Überlappt mit einem existierendem Meldezeitraum");
        }
    }

    public List<MeldezeitraumDto> getUpcomingMeldezeitraeume() {
        return meldezeitraumRepository.findByStartZeitpunktAfterOrderByStartZeitpunktAsc(LocalDate.now())
                .stream().map(meldezeitraumMapper::toDto).toList();
    }

    public List<MeldezeitraumDto> getPassedMeldezeitraeume() {
        return meldezeitraumRepository.findByEndZeitpunktBeforeOrderByEndZeitpunktDesc(LocalDate.now())
                .stream().map(meldezeitraumMapper::toDto).toList();
    }

    public MeldezeitraumDto getMostRecentPassedMeldezeitraum() {
        List<Meldezeitraum> passedZeitraueme = meldezeitraumRepository.findByEndZeitpunktBeforeOrderByEndZeitpunktDesc(LocalDate.now());
        if (passedZeitraueme.isEmpty()) {
            throw new EntityNotFoundException("Kein vergangener Meldezeitraum gefunden!");
        }
        return meldezeitraumMapper.toDto(passedZeitraueme.get(0));
    }
}
