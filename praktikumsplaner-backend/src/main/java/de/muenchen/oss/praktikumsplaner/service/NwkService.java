package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NwkMapper;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NwkService {

    private final NwkMapper nwkMapper;
    private final NwkRepository nwkRepository;
    private final ExcelService excelService;

    public NwkDto saveNwk(final CreateNwkDto createNwkDto) {
        return nwkMapper.toDto(nwkRepository.save(nwkMapper.toEntity(createNwkDto, true)));
    }

    public void importNwk(final String base64String) throws IOException {
        excelService.excelToNwkDtoList(base64String).forEach(this::saveNwk);
    }

    public List<NwkDto> findAllActiveNwks() {
        return nwkRepository.findNwksByActiveIsTrueOrderByNachname().stream().map(nwkMapper::toDto).toList();
    }

    public List<NwkDto> findAllUnassignedNwks() {
        return nwkRepository.findAllUnassigned().stream().map(nwkMapper::toDto).toList();
    }

    public List<NwkDto> findAllNwks() {
        return nwkRepository.findAll().stream().map(nwkMapper::toDto).toList();
    }
}
