package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NwkMapper {
    NwkDto toDto(final Nwk nwk);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "isActive", target = "active")
    @Mapping(source = "createNwkDto.ausbildungsrichtung", target = "ausbildungsrichtung")
    Nwk toEntity(final CreateNwkDto createNwkDto, final boolean isActive);
}
