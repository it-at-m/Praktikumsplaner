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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class NwkService {
    public static final String NWK_NOT_FOUND = "Nachwuchskraft nicht gefunden.";

    private final NwkMapper nwkMapper;
    private final NwkRepository nwkRepository;
    private final ExcelImportService excelImportService;
    private final MeldezeitraumService meldezeitraumService;

    public NwkDto saveNwk(final CreateNwkDto createNwkDto) {
        log.error("saveNwk: {}", createNwkDto);
        final Nwk nwk = nwkMapper.toEntity(createNwkDto, true);
        log.error("saveNwkEnitity: {}", nwk);
        return nwkMapper.toDto(nwkRepository.save(nwkMapper.toEntity(createNwkDto, true)));
    }

    public void importNwk(final String base64String) throws IOException {
        excelImportService.excelToNwkDtoList(base64String).forEach(this::saveNwk);
    }

    public List<NwkDto> findAllActiveNwks() {
        return nwkRepository.findAllByActiveIsTrueOrderByNachname().stream().map(nwkMapper::toDto).toList();
    }

    public List<NwkDto> findAllInactiveNwks() {
        return nwkRepository.findAllByActiveIsFalse().stream().map(nwkMapper::toDto).toList();
    }

    public List<NwkDto> findAllUnassignedNwksInCurrentMeldezeitraum() {
        final UUID meldezeitraumId = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return nwkRepository.findAllUnassignedInSpecificMeldezeitraum(meldezeitraumId).stream().map(nwkMapper::toDto).toList();
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

    public void deleteNwk(final UUID nwkId) {
        if (!nwkRepository.existsById(nwkId)) {
            throw new ResourceNotFoundException(NWK_NOT_FOUND);
        }
        nwkRepository.deleteById(nwkId);
    }
}
