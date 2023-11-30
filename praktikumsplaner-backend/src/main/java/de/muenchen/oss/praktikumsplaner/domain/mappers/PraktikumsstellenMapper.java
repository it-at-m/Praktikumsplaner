package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PraktikumsstellenMapper {
    @Mapping(target = "assignedNWK", source = "studiumsPraktikumsstelle.assignedNWK")
    StudiumsPraktikumsstelleDTO toDTO(StudiumsPraktikumsstelle studiumsPraktikumsstelle);

    @Mapping(target = "assignedNWK", source = "ausbildungsPraktikumsstelle.assignedNWK")
    AusbildungsPraktikumsstelleDTO toDTO(AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDTO.id", target = "meldezeitraumID")
    @Mapping(target = "assignedNWK", ignore = true)
    StudiumsPraktikumsstelle toEntity(CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO, MeldezeitraumDTO meldezeitraumDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDTO.id", target = "meldezeitraumID")
    @Mapping(target = "assignedNWK", ignore = true)
    AusbildungsPraktikumsstelle toEntity(CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO, MeldezeitraumDTO meldezeitraumDTO);
}
