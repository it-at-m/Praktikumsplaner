package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.MeldezeitraumRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;

    private final MeldezeitraumRepository meldezeitraumRepository;

    public StudiumsPraktikumsstelleDTO saveStudiumsPraktikumsstelle(final CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO) {
        meldezeitraumRepository.findAll().forEach(meldezeitraum -> {
            if(!(LocalDate.now().isAfter(meldezeitraum.getStartZeitpunkt()) &&
                LocalDate.now().isBefore(meldezeitraum.getEndZeitpunkt()))) throw new DateTimeException("");
        });
        return praktikumsstellenMapper.toDTO(studiumsPraktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDTO)));
    }

    public AusbildungsPraktikumsstelleDTO saveAusbildungsPraktikumsstelle(final CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO) {
        return praktikumsstellenMapper
                .toDTO(ausbildungsPraktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDTO)));
    }
}
