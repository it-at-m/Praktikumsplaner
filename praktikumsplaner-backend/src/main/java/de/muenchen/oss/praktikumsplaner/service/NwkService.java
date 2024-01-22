package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NwkMapper;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NwkService {

    private final NwkMapper nwkMapper;
    private final NwkRepository nwkRepository;
    private final ExcelImportService excelImportService;
    private final MeldezeitraumService meldezeitraumService;

    private static final Logger logger = LoggerFactory.getLogger(NwkService.class);

    public NwkDto saveNwk(final CreateNwkDto createNwkDto) {
        logger.error("saveNwk: " + createNwkDto);
        Nwk nwk = nwkMapper.toEntity(createNwkDto, true);
        logger.error("saveNwkEnitity: " + nwk);
        return nwkMapper.toDto(nwkRepository.save(nwkMapper.toEntity(createNwkDto, true)));
    }

    public void importNwk(final String base64String) throws IOException {
        excelImportService.excelToNwkDtoList(base64String).forEach(this::saveNwk);
    }

    public List<NwkDto> findAllActiveNwks() {
        return nwkRepository.findNwksByActiveIsTrueOrderByNachname().stream().map(nwkMapper::toDto).toList();
    }

    public List<NwkDto> findAllUnassignedNwksInCurrentMeldezeitraum() {
        final UUID meldezeitraumId = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return nwkRepository.findAllUnassignedInSpecificMeldzeitraum(meldezeitraumId).stream().map(nwkMapper::toDto).toList();
    }

    public List<NwkDto> findAllNwks() {
        return nwkRepository.findAll().stream().map(nwkMapper::toDto).toList();
    }

    public void saveNwk(final NwkDto nwkDto) {
        nwkRepository.save(nwkMapper.toEntity(nwkDto));
    }

    public boolean nwkExistsById(final UUID id) {
        return nwkRepository.existsById(id);
    }
}
