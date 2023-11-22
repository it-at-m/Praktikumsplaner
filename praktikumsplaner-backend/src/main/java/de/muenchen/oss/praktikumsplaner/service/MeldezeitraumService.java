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
        return meldezeitraumMapper.toDto(meldezeitraumRepository.save(meldezeitraumMapper.toEntity(meldezeitraumCreateDto)));
    }

    public MeldezeitraumDTO getCurrentMeldezeitraum() {
        Meldezeitraum currentMeldezeitraum = null;
        for (Meldezeitraum meldezeitraum : meldezeitraumRepository.findAll()) {
            if ((LocalDate.now().isEqual(meldezeitraum.getStartZeitpunkt()) ||
                    LocalDate.now().isAfter(meldezeitraum.getStartZeitpunkt())) &&
                    (LocalDate.now().isEqual(meldezeitraum.getEndZeitpunkt()) ||
                            LocalDate.now().isBefore(meldezeitraum.getEndZeitpunkt()))) {
                currentMeldezeitraum = meldezeitraum;
            }
        }
        if (currentMeldezeitraum == null) throw new ValidationException("Kein aktiver Meldezeitraum");
        return meldezeitraumMapper.toDto(currentMeldezeitraum);
    }
}
