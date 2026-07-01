package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleViewDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdatePraktikumsstelleDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface PraktikumsstellenMapper {

    String ASSIGNED_NWK = "assignedNwk";

    @Mapping(target = ASSIGNED_NWK, source = "praktikumsstelle.assignedNwk")
    @Mapping(target = "richtungLongName", expression = "java(praktikumsstelle.getRichtung().getLongName())")
    @Mapping(target = "art", source = "richtung.art")
    PraktikumsstelleViewDto toDto(Praktikumsstelle praktikumsstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDto.id", target = "meldezeitraumID")
    @Mapping(target = "projektarbeit", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    Praktikumsstelle toEntity(CreatePraktikumsstelleDto createPraktikumsstelleDto, MeldezeitraumDto meldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projektarbeit", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    Praktikumsstelle toEntity(CreatePraktikumsstelleDto createPraktikumsstelleDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "projektarbeit", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    Praktikumsstelle toEntity(UUID id, UpdatePraktikumsstelleDto praktikumsstelleDto);

    @Mapping(target = "dienststelle", source = "praktikumsstelleDto.dienststelle")
    @Mapping(target = "oertlicheAusbilder", source = "praktikumsstelleDto.oertlicheAusbilder")
    @Mapping(target = "email", source = "praktikumsstelleDto.email")
    @Mapping(target = "taetigkeiten", source = "praktikumsstelleDto.taetigkeiten")
    @Mapping(target = "wuensche", source = "praktikumsstelleDto.wuensche")
    @Mapping(target = "richtung", source = "praktikumsstelleDto.richtung")
    @Mapping(target = "programmierkenntnisse", source = "praktikumsstelleDto.programmierkenntnisse")
    @Mapping(target = "studiensemester", source = "praktikumsstelleDto.studiensemester")
    @Mapping(target = "projektarbeit", ignore = true)
    @Mapping(target = "ausbildungsjahr", source = "praktikumsstelleDto.ausbildungsjahr")
    @Mapping(target = "minderjaehrigMoeglich", source = "praktikumsstelleDto.minderjaehrigMoeglich")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    void updatePraktikumsstelle(@MappingTarget Praktikumsstelle praktikumsstelle, UpdatePraktikumsstelleDto praktikumsstelleDto);

}
