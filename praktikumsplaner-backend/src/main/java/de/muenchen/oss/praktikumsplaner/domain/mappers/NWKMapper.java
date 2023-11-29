package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNWKDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NWKDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NWKMapper {
    NWKDTO toDTO(final NWK nwk);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "isActive", target = "active")
    NWK toEntity(final CreateNWKDTO createNwkDTO, final boolean isActive);
}
