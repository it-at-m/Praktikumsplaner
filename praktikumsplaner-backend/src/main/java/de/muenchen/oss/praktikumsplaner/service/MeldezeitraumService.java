package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.MeldezeitraumMapper;
import de.muenchen.oss.praktikumsplaner.repository.MeldezeitraumRepository;
import jakarta.validation.ValidationException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MeldezeitraumService {
    private final MeldezeitraumMapper meldezeitraumMapper;
    private final MeldezeitraumRepository meldezeitraumRepository;

    public MeldezeitraumDTO createMeldezeitraum(final CreateMeldezeitraumDTO meldezeitraumCreateDto) {
        checkOverlappingMeldezeitraum(meldezeitraumCreateDto);
        return meldezeitraumMapper.toDto(meldezeitraumRepository.save(meldezeitraumMapper.toEntity(meldezeitraumCreateDto)));
    }

    public MeldezeitraumDTO getCurrentMeldezeitraum() throws ValidationException {
        Meldezeitraum currentMeldezeitraum = meldezeitraumRepository
                .findMeldezeitraumByDateInRange(LocalDate.now());
        if (currentMeldezeitraum == null) throw new ValidationException("Kein aktiver Meldezeitraum");
        return meldezeitraumMapper.toDto(currentMeldezeitraum);
    }

    public void checkOverlappingMeldezeitraum(final CreateMeldezeitraumDTO meldezeitraumCreateDto) {
        if (meldezeitraumRepository.isOverlappingMeldezeitraum(meldezeitraumCreateDto.startZeitpunkt(),
                meldezeitraumCreateDto.endZeitpunkt())) {
            throw new ValidationException("Überlappt mit einem existierendem Meldezeitraum");
        }
    }
}
