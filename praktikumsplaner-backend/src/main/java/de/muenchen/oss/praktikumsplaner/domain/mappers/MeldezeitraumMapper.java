package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MeldezeitraumMapper {
    MeldezeitraumDto toDto(final Meldezeitraum meldezeitraum);

    @Mapping(target = "id", ignore = true)
    Meldezeitraum toEntity(final CreateMeldezeitraumDto meldezeitraumCreateDto);
}
