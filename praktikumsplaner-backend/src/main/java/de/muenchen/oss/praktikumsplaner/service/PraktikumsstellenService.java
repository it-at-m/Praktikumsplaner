package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.PraktikumsstellenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final PraktikumsstellenRepository praktikumsstellenRepository;

    public PraktikumsstelleDTO savePraktikumsstelle(CreatePraktikumsstelleDTO createPraktikumsstelleDTO) {
        return praktikumsstellenMapper.toDTO(praktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(createPraktikumsstelleDTO)));
    }
}
