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
    StudiumsPraktikumsstelleDTO toDTO(final StudiumsPraktikumsstelle studiumsPraktikumsstelle);

    AusbildungsPraktikumsstelleDTO toDTO(final AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDTO.id", target = "meldezeitraumID")
    StudiumsPraktikumsstelle toEntity(final CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO, final MeldezeitraumDTO meldezeitraumDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDTO.id", target = "meldezeitraumID")
    AusbildungsPraktikumsstelle toEntity(final CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO, final MeldezeitraumDTO meldezeitraumDTO);
}
