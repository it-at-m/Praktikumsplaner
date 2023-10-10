package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NWKService {

    private final NWKMapper nwkMapper;
    private final NWKRepository nwkRepository;

    public NwkDTO saveNWK(NwkDTO nwkDTO) {
        return nwkMapper.toDTO(nwkRepository.save(nwkMapper.toEntity(nwkDTO)));
    }

    public List<NwkDTO> saveNWK(List<NwkDTO> nwkDTOList) {
        List<NwkDTO> nwkDTOS = new ArrayList<>();
        for (NwkDTO nwkDTO : nwkDTOList) {
            nwkDTOS.add(saveNWK(nwkDTO));
        }
        return nwkDTOS;
    }
}
