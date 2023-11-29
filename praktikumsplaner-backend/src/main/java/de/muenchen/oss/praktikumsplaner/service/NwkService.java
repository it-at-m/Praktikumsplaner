package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NwkMapper;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NwkService {

    private final NwkMapper nwkMapper;
    private final NwkRepository nwkRepository;
    private final ExcelService excelService;

    public NwkDto saveNWK(final CreateNwkDto createNwkDTO) {
        return nwkMapper.toDTO(nwkRepository.save(nwkMapper.toEntity(createNwkDTO, true)));
    }

    public void importNWK(final String base64String) throws IOException {
        excelService.excelToNwkDTOList(base64String).forEach(this::saveNWK);
    }

    public List<NwkDto> findAllActiveNWKs() {
        return nwkRepository.findNWKsByActiveIsTrueOrderByNachname().stream().map(nwkMapper::toDTO).collect(Collectors.toList());
    }
}
