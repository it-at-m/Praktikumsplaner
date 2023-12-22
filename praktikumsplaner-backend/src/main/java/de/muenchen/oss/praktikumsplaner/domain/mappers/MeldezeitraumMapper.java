package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MeldezeitraumMapper {
    @Mapping(source = "startZeitpunkt", target = "zeitraum.startZeitpunkt")
    @Mapping(source = "endZeitpunkt", target = "zeitraum.endZeitpunkt")
    MeldezeitraumDto toDto(final Meldezeitraum meldezeitraum);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "zeitraum.startZeitpunkt", target = "startZeitpunkt")
    @Mapping(source = "zeitraum.endZeitpunkt", target = "endZeitpunkt")
    Meldezeitraum toEntity(final CreateMeldezeitraumDto meldezeitraumCreateDto);
}
