package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NWKService {

    private final NWKMapper nwkMapper;
    private final NWKRepository nwkRepository;
    private final ExcelService excelService;

    public NwkDTO saveNWK(CreateNwkDTO createNwkDTO) {
        return nwkMapper.toDTO(nwkRepository.save(nwkMapper.toEntity(createNwkDTO, true)));
    }

    public void importNWK(String base64String) throws IOException {
        excelService.excelToNwkDTOList(base64String).forEach(this::saveNWK);
    }

    public List<NwkDTO> findAllActiveNWKs() {
        return nwkRepository.findNWKsByActiveIsTrueOrderByNachname().stream().map(nwkMapper::toDTO).collect(Collectors.toList());
    }

    public List<NwkDTO> findAllUnassignedNWKs() {
        return nwkRepository.findAllUnassigned().stream().map(nwkMapper::toDTO).collect(Collectors.toList());
    }

    public List<NwkDTO> findAllNWKs() {
        return nwkRepository.findAll().stream().map(nwkMapper::toDTO).collect(Collectors.toList());
    }
}
