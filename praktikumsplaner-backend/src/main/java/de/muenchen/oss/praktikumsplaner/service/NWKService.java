package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.repository.NWKRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NWKService {

    private final NWKMapper nwkMapper;
    private final NWKRepository nwkRepository;

    public NwkDTO saveNWK(CreateNwkDTO createNwkDTO) {
        return nwkMapper.toDTO(nwkRepository.save(nwkMapper.toEntity(createNwkDTO)));
    }

    public List<NwkDTO> saveNWK(List<CreateNwkDTO> createNwkDTOS) {
        return createNwkDTOS.stream().map(this::saveNWK).toList();
    }
}
