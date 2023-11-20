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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.List;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public TreeMap<String, List<BasePraktikumsstelle>> getAllPraktiumsstellen() {
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

        TreeMap<String, List<BasePraktikumsstelle>> groupedPraktikumsstellen = groupDienststellen(sortedIterable);

        return groupedPraktikumsstellen;
    }

    private TreeMap<String, List<BasePraktikumsstelle>> groupDienststellen(Iterable<BasePraktikumsstelle> allPraktikumsstellen) {
        TreeMap<String, List<BasePraktikumsstelle>> abteilungsMap = new TreeMap<>();

        for (BasePraktikumsstelle praktikumsstelle : allPraktikumsstellen) {
            String dienststelle = praktikumsstelle.dienststelle;
            String hauptabteilung = getHauptabteilung(dienststelle);

            abteilungsMap.computeIfAbsent(hauptabteilung, k -> new ArrayList<>()).add(praktikumsstelle);
        }

        return abteilungsMap;
    }

    private String getHauptabteilung(String dienststelle) {
        if (dienststelle.contains("IBS")) {
            return dienststelle.substring(0, 4);
        } else if (dienststelle.contains("KM")) {
            return dienststelle.substring(0, 3);
        } else {
            return "unidentifiable";
        }
    }
}
