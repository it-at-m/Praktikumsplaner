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
            // plus and minus make reports the same day as start or end possible
            if (LocalDate.now().isAfter(meldezeitraum.getStartZeitpunkt().minusDays(1)) &&
                    LocalDate.now().isBefore(meldezeitraum.getEndZeitpunkt().plusDays(1))) {
                currentMeldezeitraum = meldezeitraum;
            }
        }
        if (currentMeldezeitraum == null) throw new ValidationException("Kein aktiver Meldezeitraum");
        return meldezeitraumMapper.toDto(currentMeldezeitraum);
    }
}
