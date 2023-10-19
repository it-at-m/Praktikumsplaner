package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumCreateDTO;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MeldezeitraumMapper {
    MeldezeitraumDTO toDto(Meldezeitraum meldezeitraum);

    @Mapping(target = "id", ignore = true)
    Meldezeitraum toEntity(MeldezeitraumCreateDTO meldezeitraumCreateDTO);
}
