package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;
    private final MeldezeitraumService meldezeitraumService;

    public StudiumsPraktikumsstelleDTO saveStudiumsPraktikumsstelle(final CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO) {
        return praktikumsstellenMapper.toDTO(studiumsPraktikumsstellenRepository
                .save(praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDTO, meldezeitraumService.getCurrentMeldezeitraum())));
    }

    public AusbildungsPraktikumsstelleDTO saveAusbildungsPraktikumsstelle(final CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO) {
        return praktikumsstellenMapper
                .toDTO(ausbildungsPraktikumsstellenRepository
                        .save(praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDTO, meldezeitraumService.getCurrentMeldezeitraum())));
    }
}
