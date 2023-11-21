package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.MeldezeitraumRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import jakarta.validation.ValidationException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;

    private final MeldezeitraumRepository meldezeitraumRepository;

    public StudiumsPraktikumsstelleDTO saveStudiumsPraktikumsstelle(CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO) {
        createStudiumsPraktikumsstelleDTO = createStudiumsPraktikumsstelleDTO.withId(getCurrentMeldezeitraum().getId());
        return praktikumsstellenMapper.toDTO(studiumsPraktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDTO)));
    }

    public AusbildungsPraktikumsstelleDTO saveAusbildungsPraktikumsstelle(CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO) {
        createAusbildungsPraktikumsstelleDTO = createAusbildungsPraktikumsstelleDTO.withId(getCurrentMeldezeitraum().getId());
        return praktikumsstellenMapper
                .toDTO(ausbildungsPraktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDTO)));
    }

    public Meldezeitraum getCurrentMeldezeitraum() {
        Meldezeitraum currentMeldezeitraum = null;
        for (Meldezeitraum meldezeitraum : meldezeitraumRepository.findAll()) {
            // plus and minus make reports the same day as start or end possible
            if (LocalDate.now().isAfter(meldezeitraum.getStartZeitpunkt().minusDays(1)) &&
                    LocalDate.now().isBefore(meldezeitraum.getEndZeitpunkt().plusDays(1))) {
                currentMeldezeitraum = meldezeitraum;
            }
        }
        if (currentMeldezeitraum == null) throw new ValidationException("Kein aktiver Meldezeitraum");
        return currentMeldezeitraum;
    }
}
