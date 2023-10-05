package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.rest.NWKRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NWKService {

    private final NWKMapper nwkMapper;
    private final NWKRepository nwkRepository;

    @Valid
    public void saveNWK(@Valid NwkDTO nwkDTO) {
        nwkRepository.save(nwkMapper.toEntity(nwkDTO));
    }
}
