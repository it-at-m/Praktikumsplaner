package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NWKMapper {
    NWK toEntity(NwkDTO nwkDTO);

    NwkDTO toDTO(NWK nwk);
}
