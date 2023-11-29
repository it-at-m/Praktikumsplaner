package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NwkMapper {
    NwkDto toDTO(final Nwk nwk);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "isActive", target = "active")
    Nwk toEntity(final CreateNwkDto createNwkDTO, final boolean isActive);
}
