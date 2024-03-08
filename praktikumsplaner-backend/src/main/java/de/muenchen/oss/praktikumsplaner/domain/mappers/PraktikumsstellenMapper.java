package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PraktikumsstellenMapper {
    @Mapping(target = "assignedNwk", source = "studiumsPraktikumsstelle.assignedNwk")
    StudiumsPraktikumsstelleDto toDto(final StudiumsPraktikumsstelle studiumsPraktikumsstelle);

    @Mapping(target = "assignedNwk", source = "ausbildungsPraktikumsstelle.assignedNwk")
    AusbildungsPraktikumsstelleDto toDto(final AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDto.id", target = "meldezeitraumID")
    @Mapping(target = "assignedNwk", ignore = true)
    StudiumsPraktikumsstelle toEntity(final CreateStudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto, final MeldezeitraumDto meldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDto.id", target = "meldezeitraumID")
    @Mapping(target = "assignedNwk", ignore = true)
    AusbildungsPraktikumsstelle toEntity(final CreateAusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto,
            final MeldezeitraumDto meldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedNwk", ignore = true)
    StudiumsPraktikumsstelle toEntity(final CreateStudiumsPraktikumsstelleWithMeldezeitraumDto createStudiumsPraktikumsstelleWithMeldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedNwk", ignore = true)
    AusbildungsPraktikumsstelle toEntity(final CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto createAusbildungsPraktikumsstelleWithMeldezeitraumDto);

    @Mapping(target = "id", source = "id")
    AusbildungsPraktikumsstelle toEntity(final UUID id, final CreateAusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto ausbildungsPraktikumsstelleDto);

    @Mapping(target="id", source = "id")
    StudiumsPraktikumsstelle toEntity(final UUID id, final CreateStudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto studiumsPraktikumsstelleDto);
}
