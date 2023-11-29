package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MeldezeitraumMapper {
    MeldezeitraumDTO toDTO(Meldezeitraum meldezeitraum);

    @Mapping(target = "id", ignore = true)
    Meldezeitraum toEntity(CreateMeldezeitraumDTO meldezeitraumCreateDTO);
}
