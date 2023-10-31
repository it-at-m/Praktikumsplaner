package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PraktikumsstellenMapper {
    StudiumsPraktikumsstelleDTO toDTO(StudiumsPraktikumsstelle studiumsPraktikumsstelle);

    AusbildungsPraktikumsstelleDTO toDTO(AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle);

    @Mapping(target = "id", ignore = true)
    StudiumsPraktikumsstelle toEntity(CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO);

    @Mapping(target = "id", ignore = true)
    AusbildungsPraktikumsstelle toEntity(CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO);
}