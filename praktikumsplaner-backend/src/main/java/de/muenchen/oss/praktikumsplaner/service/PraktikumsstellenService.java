package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.BasePraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;

    public StudiumsPraktikumsstelleDTO saveStudiumsPraktikumsstelle(final CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO) {
        return praktikumsstellenMapper.toDTO(studiumsPraktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDTO)));
    }

    public AusbildungsPraktikumsstelleDTO saveAusbildungsPraktikumsstelle(final CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO) {
        return praktikumsstellenMapper
                .toDTO(ausbildungsPraktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDTO)));
    }

    public Iterable<BasePraktikumsstelle> getAllPraktiumsstellen() {
        Iterable<AusbildungsPraktikumsstelle> ausbildungsIterable = ausbildungsPraktikumsstellenRepository.findAll();
        Iterable<StudiumsPraktikumsstelle> studiumsIterable = studiumsPraktikumsstellenRepository.findAll();

        List<BasePraktikumsstelle> ausbildungsList = new ArrayList<>();
        ausbildungsIterable.forEach(ausbildungsList::add);

        List<BasePraktikumsstelle> studiumsList = new ArrayList<>();
        studiumsIterable.forEach(studiumsList::add);

        List<BasePraktikumsstelle> combinedList = new ArrayList<>();
        combinedList.addAll(ausbildungsList);
        combinedList.addAll(studiumsList);

        combinedList.sort(Comparator.comparing(BasePraktikumsstelle::getDienststelle));

        Iterable<BasePraktikumsstelle> sortedIterable = combinedList;

        return sortedIterable;
    }
}
