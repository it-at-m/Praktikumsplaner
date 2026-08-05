package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdatePraktikumsstelleDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface PraktikumsstellenMapper {

    String ASSIGNED_NWK = "assignedNwk";

    @Mapping(target = ASSIGNED_NWK, source = "praktikumsstelle.assignedNwk")
    PraktikumsstelleDto toDto(Praktikumsstelle praktikumsstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDto.id", target = "meldezeitraumID")
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    Praktikumsstelle toEntity(CreatePraktikumsstelleDto createPraktikumsstelleDto, MeldezeitraumDto meldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    Praktikumsstelle toEntity(CreatePraktikumsstelleWithMeldezeitraumDto createPraktikumsstelleWithMeldezeitraumDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    Praktikumsstelle toEntity(UUID id, UpdatePraktikumsstelleDto praktikumsstelleDto);

    @Mapping(target = "dienststelle", source = "praktikumsstelleDto.dienststelle")
    @Mapping(target = "oertlicheAusbilder", source = "praktikumsstelleDto.oertlicheAusbilder")
    @Mapping(target = "email", source = "praktikumsstelleDto.email")
    @Mapping(target = "taetigkeiten", source = "praktikumsstelleDto.taetigkeiten")
    @Mapping(target = "wuensche", source = "praktikumsstelleDto.wuensche")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    void updatePraktikumsstelle(@MappingTarget Praktikumsstelle praktikumsstelle, UpdatePraktikumsstelleDto praktikumsstelleDto);
}
