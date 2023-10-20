package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstelleRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstelleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstelleRepository studiumsPraktikumsstelleRepository;
    private final AusbildungsPraktikumsstelleRepository ausbildungsPraktikumsstelleRepository;

    public StudiumsPraktikumsstelleDTO saveStudiumsPraktikumsstelle(CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO) {
        return praktikumsstellenMapper.toDTO(studiumsPraktikumsstelleRepository.save(praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDTO)));
    }

    public AusbildungsPraktikumsstelleDTO saveAusbildungsPraktikumsstelle(CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO) {
        return praktikumsstellenMapper
                .toDTO(ausbildungsPraktikumsstelleRepository.save(praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDTO)));
    }
}
