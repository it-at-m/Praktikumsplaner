package de.muenchen.oss.praktikumsplaner.domain.mappers;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface PraktikumsstellenMapper {

    String ASSIGNED_NWK = "assignedNwk";

    @Mapping(target = ASSIGNED_NWK, source = "studiumsPraktikumsstelle.assignedNwk")
    StudiumsPraktikumsstelleDto toDto(StudiumsPraktikumsstelle studiumsPraktikumsstelle);

    @Mapping(target = ASSIGNED_NWK, source = "ausbildungsPraktikumsstelle.assignedNwk")
    AusbildungsPraktikumsstelleDto toDto(AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDto.id", target = "meldezeitraumID")
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    StudiumsPraktikumsstelle toEntity(CreateStudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto, MeldezeitraumDto meldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "meldezeitraumDto.id", target = "meldezeitraumID")
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    AusbildungsPraktikumsstelle toEntity(CreateAusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto,
            MeldezeitraumDto meldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    StudiumsPraktikumsstelle toEntity(CreateStudiumsPraktikumsstelleWithMeldezeitraumDto createStudiumsPraktikumsstelleWithMeldezeitraumDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    AusbildungsPraktikumsstelle toEntity(CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto createAusbildungsPraktikumsstelleWithMeldezeitraumDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    AusbildungsPraktikumsstelle toEntity(UUID id,
            UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto ausbildungsPraktikumsstelleDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    StudiumsPraktikumsstelle toEntity(UUID id, UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto studiumsPraktikumsstelleDto);

    @Mapping(target = "dienststelle", source = "praktikumsstelleDto.dienststelle")
    @Mapping(target = "oertlicheAusbilder", source = "praktikumsstelleDto.oertlicheAusbilder")
    @Mapping(target = "email", source = "praktikumsstelleDto.email")
    @Mapping(target = "taetigkeiten", source = "praktikumsstelleDto.taetigkeiten")
    @Mapping(target = "wuensche", source = "praktikumsstelleDto.wuensche")
    @Mapping(target = "minderjaehrigMoeglich", source = "praktikumsstelleDto.minderjaehrigMoeglich")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projektarbeit", ignore = true)
    @Mapping(target = "ausbildungsjahr", ignore = true)
    @Mapping(target = "ausbildungsrichtung", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    void updateAusbildungsPraktikumsstelle(@MappingTarget AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle,
            UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto);

    @Mapping(target = "dienststelle", source = "praktikumsstelleDto.dienststelle")
    @Mapping(target = "oertlicheAusbilder", source = "praktikumsstelleDto.oertlicheAusbilder")
    @Mapping(target = "email", source = "praktikumsstelleDto.email")
    @Mapping(target = "taetigkeiten", source = "praktikumsstelleDto.taetigkeiten")
    @Mapping(target = "wuensche", source = "praktikumsstelleDto.wuensche")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studiensemester", ignore = true)
    @Mapping(target = "studiengang", ignore = true)
    @Mapping(target = ASSIGNED_NWK, ignore = true)
    void updateStudiumsPraktikumsstelle(@MappingTarget StudiumsPraktikumsstelle studiumsPraktikumsstelle,
            UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto);

}
