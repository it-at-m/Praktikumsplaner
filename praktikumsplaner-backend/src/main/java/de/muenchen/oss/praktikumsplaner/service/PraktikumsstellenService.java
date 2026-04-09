package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.security.AuthUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@SuppressWarnings("PMD.CouplingBetweenObjects")
public class PraktikumsstellenService {

    public static final String PRAKTIKUMSSTELLE_NOT_FOUND = "Praktikumsstelle nicht gefunden.";
    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;
    private final MeldezeitraumService meldezeitraumService;
    private final NwkRepository nwkRepository;

    public StudiumsPraktikumsstelleDto normalizeAndSaveStudiumsPraktikumsstelle(final CreateStudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto) {
        final StudiumsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDto,
                meldezeitraumService.getCurrentMeldezeitraum());
        studiumsPraktikumsstellenRepository.save(entityPraktikumsstelle);
        return praktikumsstellenMapper.toDto(entityPraktikumsstelle);
    }

    public StudiumsPraktikumsstelleDto saveStudiumsPraktikumsstelleWithMeldezeitraum(
            final CreateStudiumsPraktikumsstelleWithMeldezeitraumDto createStudiumsPraktikumsstelleWithMeldezeitraumDto) {
        final StudiumsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleWithMeldezeitraumDto);
        studiumsPraktikumsstellenRepository.save(entityPraktikumsstelle);
        return praktikumsstellenMapper.toDto(entityPraktikumsstelle);
    }

    public AusbildungsPraktikumsstelleDto normalizeAndSaveAusbildungsPraktikumsstelle(
            final CreateAusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto) {
        final AusbildungsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDto,
                meldezeitraumService.getCurrentMeldezeitraum());
        ausbildungsPraktikumsstellenRepository.save(entityPraktikumsstelle);
        return praktikumsstellenMapper.toDto(entityPraktikumsstelle);
    }

    public AusbildungsPraktikumsstelleDto saveAusbildungsPraktikumsstelleWithMeldezeitraum(
            final CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto createAusbildungsPraktikumsstelleWithMeldezeitraumDto) {
        final AusbildungsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleWithMeldezeitraumDto);
        ausbildungsPraktikumsstellenRepository.save(entityPraktikumsstelle);
        return praktikumsstellenMapper.toDto(entityPraktikumsstelle);
    }

    public PraktikumsstelleDto assignNwk(final UUID praktikumsstellenID, final UUID nwkID) {
        final Nwk assignedNwk = nwkRepository.findById(nwkID).orElseThrow();

        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenID)) {
            final AusbildungsPraktikumsstelle praktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenID).orElseThrow();
            if (praktikumsstelle.getAssignedNwk() == null) {
                praktikumsstelle.setAssignedNwk(assignedNwk);
                ausbildungsPraktikumsstellenRepository.save(praktikumsstelle);
                return praktikumsstellenMapper.toDto(praktikumsstelle);
            }
            throw new ResourceConflictException("Praktikumsstelle hat bereits eine zugewiesenen Nachwuchskraft.");
        } else if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenID)) {
            final StudiumsPraktikumsstelle praktikumsstelle = studiumsPraktikumsstellenRepository.findById(praktikumsstellenID).orElseThrow();
            if (praktikumsstelle.getAssignedNwk() == null) {
                praktikumsstelle.setAssignedNwk(assignedNwk);
                studiumsPraktikumsstellenRepository.save(praktikumsstelle);
                return praktikumsstellenMapper.toDto(praktikumsstelle);
            }
            throw new ResourceConflictException("Praktikumsstelle hat bereits eine zugewiesenen Nachwuchskraft.");
        }
        throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
    }

    public PraktikumsstelleDto unassignNwk(final UUID praktikumsstellenId) {
        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            final AusbildungsPraktikumsstelle praktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenId).orElseThrow();
            praktikumsstelle.setAssignedNwk(null);
            ausbildungsPraktikumsstellenRepository.save(praktikumsstelle);
            return praktikumsstellenMapper.toDto(praktikumsstelle);
        } else if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            final StudiumsPraktikumsstelle praktikumsstelle = studiumsPraktikumsstellenRepository.findById(praktikumsstellenId).orElseThrow();
            praktikumsstelle.setAssignedNwk(null);
            studiumsPraktikumsstellenRepository.save(praktikumsstelle);
            return praktikumsstellenMapper.toDto(praktikumsstelle);
        }
        throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
    }

    public List<PraktikumsstelleDto> getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum() {
        final List<PraktikumsstelleDto> combinedList = new ArrayList<>();
        combinedList.addAll(getAllAssignedAusbildungspraktikumsstellenInMostRecentPassedMeldezeitraum());
        combinedList.addAll(getAllAssignedStudiumspraktikumsstellenInMostRecentPassedMeldezeitraum());

        return combinedList;
    }

    public List<AusbildungsPraktikumsstelleDto> getAllAssignedAusbildungspraktikumsstellenInMostRecentPassedMeldezeitraum() {
        final UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();

        return ausbildungsPraktikumsstellenRepository
                .findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(lastMeldezeitraumID).stream().map(praktikumsstellenMapper::toDto).toList();
    }

    public List<StudiumsPraktikumsstelleDto> getAllAssignedStudiumspraktikumsstellenInMostRecentPassedMeldezeitraum() {
        final UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();

        return studiumsPraktikumsstellenRepository
                .findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(lastMeldezeitraumID).stream().map(praktikumsstellenMapper::toDto).toList();
    }

    public Map<String, List<PraktikumsstelleDto>> getAllInCurrentMeldezeitraumGroupedByDienststelle() {
        final UUID currentMeldezeitraumID = meldezeitraumService.getCurrentMeldezeitraum().id();
        return filterPraktikumsstellenForCurrentRole(getPraktikumsstellenGroupedByDienststelle(currentMeldezeitraumID));
    }

    public Map<String, List<PraktikumsstelleDto>> getRecentPraktikumsstellenGroupedByDienststelle() {
        final UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return filterPraktikumsstellenForCurrentRole(getPraktikumsstellenGroupedByDienststelle(lastMeldezeitraumID));
    }

    public void deleteStudiumsPraktikumsstelle(final UUID praktikumsstellenId) {
        if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            studiumsPraktikumsstellenRepository.deleteById(praktikumsstellenId);
        } else {
            throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
        }
    }

    public void deleteAusbildungsPraktikumsstelle(final UUID praktikumsstellenId) {
        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            ausbildungsPraktikumsstellenRepository.deleteById(praktikumsstellenId);
        } else {
            throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
        }
    }

    public void updateAusbildungsPraktikumsstelle(final UUID praktikumsstellenId,
            final UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {
        final AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = praktikumsstellenMapper.toEntity(praktikumsstellenId, praktikumsstelleDto);

        final AusbildungsPraktikumsstelle praktikumsstelleToUpdate = findByIdOrThrowAusbildungspraktikumsstelle(praktikumsstellenId);

        if (praktikumsstelleToUpdate.getAssignedNwk() != null) {
            updateAusbildungsPraktikumsstelleWithAssignedNwk(praktikumsstellenId, praktikumsstelleDto);
        } else {
            ausbildungsPraktikumsstellenRepository.save(ausbildungsPraktikumsstelle);
        }
    }

    public void updateStudiumsPraktikumsstelle(final UUID praktikumsstellenId, final UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {
        final StudiumsPraktikumsstelle studiumsPraktikumsstelle = praktikumsstellenMapper.toEntity(praktikumsstellenId, praktikumsstelleDto);

        final StudiumsPraktikumsstelle praktikumsstelleToUpdate = findByIdOrThrowStudiumpraktikumsstelle(praktikumsstellenId);

        if (praktikumsstelleToUpdate.getAssignedNwk() != null) {
            updateStudiumsPraktikumsstelleWithAssignedNwk(praktikumsstellenId, praktikumsstelleDto);
        } else {
            studiumsPraktikumsstellenRepository.save(studiumsPraktikumsstelle);
        }
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void updateAusbildungsPraktikumsstelleWithAssignedNwk(final UUID id,
            final UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {
        final AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = findByIdOrThrowAusbildungspraktikumsstelle(id);

        //Check if any field which is not supposed to change changed
        if (ausbildungsPraktikumsstelle.getDringlichkeit() != praktikumsstelleDto.dringlichkeit()
                || !Objects.equals(ausbildungsPraktikumsstelle.getNamentlicheAnforderung(), praktikumsstelleDto.namentlicheAnforderung())
                || ausbildungsPraktikumsstelle.isPlanstelleVorhanden() != praktikumsstelleDto.planstelleVorhanden()
                || !ausbildungsPraktikumsstelle.getMeldezeitraumID().toString().equals(praktikumsstelleDto.meldezeitraumID().toString())
                || ausbildungsPraktikumsstelle.isProjektarbeit() != praktikumsstelleDto.projektarbeit()
                || !Objects.equals(ausbildungsPraktikumsstelle.getProgrammierkenntnisse(), praktikumsstelleDto.programmierkenntnisse())
                || !ausbildungsPraktikumsstelle.getAusbildungsjahr().equals(praktikumsstelleDto.ausbildungsjahr())
                || !ausbildungsPraktikumsstelle.getAusbildungsrichtung().equals(praktikumsstelleDto.ausbildungsrichtung())
                || !Objects.equals(ausbildungsPraktikumsstelle.getWuensche(), praktikumsstelleDto.wuensche())
                || ausbildungsPraktikumsstelle.isMinderjaehrigMoeglich() != praktikumsstelleDto.minderjaehrigMoeglich()) {
            throw new ResourceConflictException("Unerlaubter Versuch der Änderung von Daten");
        }
        praktikumsstellenMapper.updateAusbildungsPraktikumsstelle(ausbildungsPraktikumsstelle, praktikumsstelleDto);
        ausbildungsPraktikumsstellenRepository.save(ausbildungsPraktikumsstelle);
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void updateStudiumsPraktikumsstelleWithAssignedNwk(final UUID id, final UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {
        final StudiumsPraktikumsstelle studiumsPraktikumsstelle = findByIdOrThrowStudiumpraktikumsstelle(id);

        //Check if any field which is not supposed to change changed
        if (studiumsPraktikumsstelle.getDringlichkeit() != praktikumsstelleDto.dringlichkeit()
                || !Objects.equals(studiumsPraktikumsstelle.getNamentlicheAnforderung(), praktikumsstelleDto.namentlicheAnforderung())
                || studiumsPraktikumsstelle.isPlanstelleVorhanden() != praktikumsstelleDto.planstelleVorhanden()
                || !studiumsPraktikumsstelle.getMeldezeitraumID().toString().equals(praktikumsstelleDto.meldezeitraumID().toString())
                || !Objects.equals(studiumsPraktikumsstelle.getProgrammierkenntnisse(), praktikumsstelleDto.programmierkenntnisse())
                || !studiumsPraktikumsstelle.getStudiensemester().equals(praktikumsstelleDto.studiensemester())
                || studiumsPraktikumsstelle.getStudiengang() != praktikumsstelleDto.studiengang()
                || !Objects.equals(studiumsPraktikumsstelle.getWuensche(), praktikumsstelleDto.wuensche())) {
            throw new ResourceConflictException("Unerlaubter Versuch der Änderung von Daten");
        }

        praktikumsstellenMapper.updateStudiumsPraktikumsstelle(studiumsPraktikumsstelle, praktikumsstelleDto);
        studiumsPraktikumsstellenRepository.save(studiumsPraktikumsstelle);
    }

    private Map<String, List<PraktikumsstelleDto>> getPraktikumsstellenGroupedByDienststelle(final UUID meldezeitraumID) {
        final List<AusbildungsPraktikumsstelleDto> ausbildungsListDto = ausbildungsPraktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumID)
                .stream()
                .map(praktikumsstellenMapper::toDto).toList();

        final List<StudiumsPraktikumsstelleDto> studiumsListDto = studiumsPraktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDto).toList();

        final List<PraktikumsstelleDto> combinedList = new ArrayList<>();
        combinedList.addAll(ausbildungsListDto);
        combinedList.addAll(studiumsListDto);
        combinedList.sort(Comparator.comparing(PraktikumsstelleDto::dienststelle));

        return combinedList.stream()
                .collect(Collectors.groupingBy(
                        praktikumsstelle -> getHauptabteilung(praktikumsstelle.dienststelle()),
                        TreeMap::new,
                        Collectors.toList()));
    }

    private Map<String, List<PraktikumsstelleDto>> filterPraktikumsstellenForCurrentRole(
            final Map<String, List<PraktikumsstelleDto>> abteilungsStellenMap) {

        if (AuthUtils.isAusbildungsleitung()) {
            return abteilungsStellenMap;
        }

        if (AuthUtils.isAusbilder()) {
            final String usermail = AuthUtils.getMailFromUser();
            final String userDepartment = AuthUtils.getDepartmentFromUser();

            return abteilungsStellenMap.entrySet().stream()
                    .map(entry -> Map.entry(
                            entry.getKey(),
                            entry.getValue().stream()
                                    .filter(dto -> usermail.equals(dto.email()) ||
                                            dto.dienststelle().startsWith(userDepartment))
                                    .collect(Collectors.toList())))
                    .filter(entry -> !entry.getValue().isEmpty())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> b,
                            TreeMap::new));

        }

        throw new AuthorizationDeniedException("Zugriffsrolle fehlt");
    }

    private String getHauptabteilung(final String dienststelle) {
        final Matcher matcher = Pattern.compile("\\d").matcher(dienststelle);
        if (matcher.find()) {
            return dienststelle.substring(0, matcher.start() + 1);
        }
        return dienststelle;
    }

    private AusbildungsPraktikumsstelle findByIdOrThrowAusbildungspraktikumsstelle(final UUID praktikumsstellenId) {
        final Optional<AusbildungsPraktikumsstelle> ausbildungsPraktikumsstelleOptional = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenId);
        if (ausbildungsPraktikumsstelleOptional.isPresent()) {
            return ausbildungsPraktikumsstelleOptional.get();
        }
        throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
    }

    private StudiumsPraktikumsstelle findByIdOrThrowStudiumpraktikumsstelle(final UUID praktikumsstellenId) {
        final Optional<StudiumsPraktikumsstelle> studiumsPraktikumsstelleOptional = studiumsPraktikumsstellenRepository.findById(praktikumsstellenId);
        if (studiumsPraktikumsstelleOptional.isPresent()) {
            return studiumsPraktikumsstelleOptional.get();
        }
        throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
    }
}
