package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.mappers.MeldezeitraumMapper;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumCreateDTO;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.repositories.MeldezeitraumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MeldezeitraumService {
    private final MeldezeitraumMapper meldezeitraumMapper;
    private final MeldezeitraumRepository meldezeitraumRepository;

    public MeldezeitraumDTO createMeldezeitraum(final MeldezeitraumCreateDTO meldezeitraumCreateDto) {
        final Meldezeitraum meldezeitraum = meldezeitraumMapper.toEntity(meldezeitraumCreateDto);
        return meldezeitraumMapper.toDto(meldezeitraumRepository.save(meldezeitraum));
    }
}
