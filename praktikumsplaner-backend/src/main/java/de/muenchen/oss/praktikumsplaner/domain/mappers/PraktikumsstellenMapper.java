package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.*;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdateStudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    AusbildungsPraktikumsstelle toEntity(final UUID id,
            final UpdateAusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto ausbildungsPraktikumsstelleDto);

    @Mapping(target = "id", source = "id")
    StudiumsPraktikumsstelle toEntity(final UUID id, final UpdateStudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto studiumsPraktikumsstelleDto);

    @Mapping(target = "dienststelle", source = "praktikumsstelleDto.dienststelle")
    @Mapping(target = "oertlicheAusbilder", source = "praktikumsstelleDto.oertlicheAusbilder")
    @Mapping(target = "email", source = "praktikumsstelleDto.email")
    @Mapping(target = "taetigkeiten", source = "praktikumsstelleDto.taetigkeiten")

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projektarbeit", ignore = true)
    @Mapping(target = "ausbildungsjahr", ignore = true)
    @Mapping(target = "ausbildungsrichtung", ignore = true)
    AusbildungsPraktikumsstelle updateAusbildungsPraktikumsstelle(@MappingTarget final AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle,
            final UpdateAusbildungsPraktikumsstelleWithMeldezeitraumAndAssignedNWKDto praktikumsstelleDto);

    @Mapping(target = "dienststelle", source = "praktikumsstelleDto.dienststelle")
    @Mapping(target = "oertlicheAusbilder", source = "praktikumsstelleDto.oertlicheAusbilder")
    @Mapping(target = "email", source = "praktikumsstelleDto.email")
    @Mapping(target = "taetigkeiten", source = "praktikumsstelleDto.taetigkeiten")

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studiensemester", ignore = true)
    @Mapping(target = "studiengang", ignore = true)
    StudiumsPraktikumsstelle updateStudiumsPraktikumsstelle(@MappingTarget final StudiumsPraktikumsstelle studiumsPraktikumsstelle,
            final UpdateStudiumsPraktikumsstelleWithMeldezeitraumAndAssignedNwkDto praktikumsstelleDto);

}
