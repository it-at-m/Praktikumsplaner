package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PraktikumsstellenMapper {
    PraktikumsstelleDTO toDTO(Praktikumsstelle praktikumsstelle);

    @Mapping(target = "id", ignore = true)
    Praktikumsstelle toEntity(CreatePraktikumsstelleDTO createPraktikumsstelleDTO);
}
