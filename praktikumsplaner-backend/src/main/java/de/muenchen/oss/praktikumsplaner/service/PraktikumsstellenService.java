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
import de.muenchen.oss.praktikumsplaner.domain.mappers.NwkMapper;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final NwkMapper nwkMapper;
    private final StudiumsPraktikumsstellenRepository studiumsPraktikumsstellenRepository;
    private final AusbildungsPraktikumsstellenRepository ausbildungsPraktikumsstellenRepository;
    private final MeldezeitraumService meldezeitraumService;
    private final NwkRepository nwkRepository;

    public StudiumsPraktikumsstelleDto saveStudiumsPraktikumsstelle(final CreateStudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto) {
        StudiumsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleDto,
                meldezeitraumService.getCurrentMeldezeitraum());
        entityPraktikumsstelle.setDienststelle(normalizeDienststelle(entityPraktikumsstelle.getDienststelle()));
        return praktikumsstellenMapper.toDto(studiumsPraktikumsstellenRepository.save(entityPraktikumsstelle));
    }

    public StudiumsPraktikumsstelleDto saveStudiumsPraktikumsstelleWithMeldezeitraum(
            final CreateStudiumsPraktikumsstelleWithMeldezeitraumDto createStudiumsPraktikumsstelleWithMeldezeitraumDto) {
        StudiumsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createStudiumsPraktikumsstelleWithMeldezeitraumDto);
        entityPraktikumsstelle.setDienststelle(normalizeDienststelle(entityPraktikumsstelle.getDienststelle()));
        return praktikumsstellenMapper.toDto(studiumsPraktikumsstellenRepository.save(entityPraktikumsstelle));
    }

    public AusbildungsPraktikumsstelleDto saveAusbildungsPraktikumsstelle(final CreateAusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto) {
        AusbildungsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleDto,
                meldezeitraumService.getCurrentMeldezeitraum());
        entityPraktikumsstelle.setDienststelle(normalizeDienststelle(entityPraktikumsstelle.getDienststelle()));
        return praktikumsstellenMapper
                .toDto(ausbildungsPraktikumsstellenRepository.save(entityPraktikumsstelle));
    }

    public AusbildungsPraktikumsstelleDto saveAusbildungsPraktikumsstelleWithMeldezeitraum(
            final CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto createAusbildungsPraktikumsstelleWithMeldezeitraumDto) {
        AusbildungsPraktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createAusbildungsPraktikumsstelleWithMeldezeitraumDto);
        entityPraktikumsstelle.setDienststelle(normalizeDienststelle(entityPraktikumsstelle.getDienststelle()));
        return praktikumsstellenMapper
                .toDto(ausbildungsPraktikumsstellenRepository.save(entityPraktikumsstelle));
    }

    public PraktikumsstelleDto assignNwk(UUID praktikumsstellenID, UUID nwkID) {
        Nwk assignedNwk = nwkRepository.findById(nwkID).orElseThrow();

        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenID)) {
            AusbildungsPraktikumsstelle praktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenID).orElseThrow();
            if (praktikumsstelle.getAssignedNwk() == null) {
                praktikumsstelle.setAssignedNwk(assignedNwk);
                ausbildungsPraktikumsstellenRepository.save(praktikumsstelle);
                return praktikumsstellenMapper.toDto(praktikumsstelle);
            } else
                throw new ResourceConflictException("Praktikumsstelle hat bereits eine zugewiesenen Nachwuchskraft.");
        } else if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenID)) {
            StudiumsPraktikumsstelle praktikumsstelle = studiumsPraktikumsstellenRepository.findById(praktikumsstellenID).orElseThrow();
            if (praktikumsstelle.getAssignedNwk() == null) {
                praktikumsstelle.setAssignedNwk(assignedNwk);
                studiumsPraktikumsstellenRepository.save(praktikumsstelle);
                return praktikumsstellenMapper.toDto(praktikumsstelle);
            } else
                throw new ResourceConflictException("Praktikumsstelle hat bereits eine zugewiesenen Nachwuchskraft.");
        } else throw new ResourceNotFoundException("Praktikumsstelle nicht gefunden.");
    }

    public PraktikumsstelleDto unassignNwk(UUID praktikumsstellenId) {
        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            AusbildungsPraktikumsstelle praktikumsstelle = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenId).orElseThrow();
            praktikumsstelle.setAssignedNwk(null);
            ausbildungsPraktikumsstellenRepository.save(praktikumsstelle);
            return praktikumsstellenMapper.toDto(praktikumsstelle);
        } else if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            StudiumsPraktikumsstelle praktikumsstelle = studiumsPraktikumsstellenRepository.findById(praktikumsstellenId).orElseThrow();
            praktikumsstelle.setAssignedNwk(null);
            studiumsPraktikumsstellenRepository.save(praktikumsstelle);
            return praktikumsstellenMapper.toDto(praktikumsstelle);
        } else throw new ResourceNotFoundException("Praktikumsstelle nicht gefunden.");
    }

    public List<PraktikumsstelleDto> getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum() {
        List<PraktikumsstelleDto> combinedList = new ArrayList<>();
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

    public TreeMap<String, List<PraktikumsstelleDto>> getAllInCurrentMeldezeitraumGroupedByDienststelle() {
        final UUID currentMeldezeitraumID = meldezeitraumService.getCurrentMeldezeitraum().id();
        return getPraktikumsstellenGroupedByDienststelle(currentMeldezeitraumID);
    }

    public TreeMap<String, List<PraktikumsstelleDto>> getRecentPraktikumsstellenGroupedByDienststelle() {
        UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return getPraktikumsstellenGroupedByDienststelle(lastMeldezeitraumID);
    }

    public void deleteStudiumsPraktikumsstelle(UUID praktikumsstellenId) {
        if (studiumsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            studiumsPraktikumsstellenRepository.deleteById(praktikumsstellenId);
        } else {
            throw new ResourceNotFoundException("Praktikumsstelle nicht gefunden.");
        }
    }

    public void deleteAusbildungsPraktikumsstelle(UUID praktikumsstellenId) {
        if (ausbildungsPraktikumsstellenRepository.existsById(praktikumsstellenId)) {
            ausbildungsPraktikumsstellenRepository.deleteById(praktikumsstellenId);
        } else {
            throw new ResourceNotFoundException("Praktikumsstelle nicht gefunden.");
        }
    }

    public void updateAusbildungsPraktikumsstelle(UUID praktikumsstellenId,
            UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {

        final AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = praktikumsstellenMapper.toEntity(praktikumsstellenId, praktikumsstelleDto);
        ausbildungsPraktikumsstelle.setDienststelle(normalizeDienststelle(ausbildungsPraktikumsstelle.getDienststelle()));

        final AusbildungsPraktikumsstelle praktikumsstelleToUpdate = findByIdOrThrowAusbildungspraktikumsstelle(praktikumsstellenId);

        if (praktikumsstelleToUpdate.getAssignedNwk() != null) {
            updateAusbildungsPraktikumsstelleWithAssignedNwk(praktikumsstellenId, praktikumsstelleDto);
        } else {
            ausbildungsPraktikumsstellenRepository.save(ausbildungsPraktikumsstelle);
        }
    }

    public void updateStudiumsPraktikumsstelle(UUID praktikumsstellenId, UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {

        final StudiumsPraktikumsstelle studiumsPraktikumsstelle = praktikumsstellenMapper.toEntity(praktikumsstellenId, praktikumsstelleDto);
        studiumsPraktikumsstelle.setDienststelle(normalizeDienststelle(studiumsPraktikumsstelle.getDienststelle()));

        final StudiumsPraktikumsstelle praktikumsstelleToUpdate = findByIdOrThrowStudiumpraktikumsstelle(praktikumsstellenId);

        if (praktikumsstelleToUpdate.getAssignedNwk() != null) {
            updateStudiumsPraktikumsstelleWithAssignedNwk(praktikumsstellenId, praktikumsstelleDto);
        } else {
            studiumsPraktikumsstellenRepository.save(studiumsPraktikumsstelle);
        }
    }

    private void updateAusbildungsPraktikumsstelleWithAssignedNwk(UUID id,
            UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {

        final AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = findByIdOrThrowAusbildungspraktikumsstelle(id);

        //Check if any field which is not supposed to change changed
        if (ausbildungsPraktikumsstelle.getReferat() != praktikumsstelleDto.referat()
                || ausbildungsPraktikumsstelle.getDringlichkeit() != praktikumsstelleDto.dringlichkeit()
                || !Objects.equals(ausbildungsPraktikumsstelle.getNamentlicheAnforderung(), praktikumsstelleDto.namentlicheAnforderung())
                || ausbildungsPraktikumsstelle.isPlanstelleVorhanden() != praktikumsstelleDto.planstelleVorhanden()
                || !ausbildungsPraktikumsstelle.getMeldezeitraumID().toString().equals(praktikumsstelleDto.meldezeitraumID().toString())
                || ausbildungsPraktikumsstelle.isProjektarbeit() != praktikumsstelleDto.projektarbeit()
                || !Objects.equals(ausbildungsPraktikumsstelle.getProgrammierkenntnisse(), praktikumsstelleDto.programmierkenntnisse())
                || !ausbildungsPraktikumsstelle.getAusbildungsjahr().equals(praktikumsstelleDto.ausbildungsjahr())
                || !ausbildungsPraktikumsstelle.getAusbildungsrichtung().equals(praktikumsstelleDto.ausbildungsrichtung())) {
            throw new ResourceConflictException("Unerlaubter Versuch der Änderung von Daten");
        }
        praktikumsstellenMapper.updateAusbildungsPraktikumsstelle(ausbildungsPraktikumsstelle, praktikumsstelleDto);
        ausbildungsPraktikumsstelle.setDienststelle(normalizeDienststelle(ausbildungsPraktikumsstelle.getDienststelle()));
        ausbildungsPraktikumsstellenRepository.save(ausbildungsPraktikumsstelle);
    }

    private void updateStudiumsPraktikumsstelleWithAssignedNwk(UUID id, UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {

        final StudiumsPraktikumsstelle studiumsPraktikumsstelle = findByIdOrThrowStudiumpraktikumsstelle(id);

        //Check if any field which is not supposed to change changed
        if (studiumsPraktikumsstelle.getReferat() != praktikumsstelleDto.referat()
                || studiumsPraktikumsstelle.getDringlichkeit() != praktikumsstelleDto.dringlichkeit()
                || !Objects.equals(studiumsPraktikumsstelle.getNamentlicheAnforderung(), praktikumsstelleDto.namentlicheAnforderung())
                || studiumsPraktikumsstelle.isPlanstelleVorhanden() != praktikumsstelleDto.planstelleVorhanden()
                || !studiumsPraktikumsstelle.getMeldezeitraumID().toString().equals(praktikumsstelleDto.meldezeitraumID().toString())
                || !Objects.equals(studiumsPraktikumsstelle.getProgrammierkenntnisse(), praktikumsstelleDto.programmierkenntnisse())
                || !studiumsPraktikumsstelle.getStudiensemester().equals(praktikumsstelleDto.studiensemester())
                || studiumsPraktikumsstelle.getStudiengang() != praktikumsstelleDto.studiengang()) {
            throw new ResourceConflictException("Unerlaubter Versuch der Änderung von Daten");
        }

        praktikumsstellenMapper.updateStudiumsPraktikumsstelle(studiumsPraktikumsstelle, praktikumsstelleDto);
        studiumsPraktikumsstelle.setDienststelle(normalizeDienststelle(studiumsPraktikumsstelle.getDienststelle()));

        studiumsPraktikumsstellenRepository.save(studiumsPraktikumsstelle);
    }

    private TreeMap<String, List<PraktikumsstelleDto>> getPraktikumsstellenGroupedByDienststelle(UUID meldezeitraumID) {
        List<AusbildungsPraktikumsstelleDto> ausbildungsListDto = ausbildungsPraktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDto).toList();

        List<StudiumsPraktikumsstelleDto> studiumsListDto = studiumsPraktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDto).toList();

        List<PraktikumsstelleDto> combinedList = new ArrayList<>();
        combinedList.addAll(ausbildungsListDto);
        combinedList.addAll(studiumsListDto);
        combinedList.sort(Comparator.comparing(PraktikumsstelleDto::dienststelle));

        return combinedList.stream()
                .collect(Collectors.groupingBy(
                        praktikumsstelle -> getHauptabteilung(praktikumsstelle.dienststelle()),
                        TreeMap::new,
                        Collectors.toList()));
    }

    private String getHauptabteilung(final String dienststelle) {
        Matcher matcher = Pattern.compile("\\d").matcher(dienststelle);
        if (matcher.find()) {
            return dienststelle.substring(0, matcher.start() + 1);
        }
        return dienststelle;
    }

    private String normalizeDienststelle(final String dienststelle) {
        return dienststelle.toUpperCase().trim().replaceAll("ITM|IT@M|RIT|-", "");
    }

    private AusbildungsPraktikumsstelle findByIdOrThrowAusbildungspraktikumsstelle(UUID praktikumsstellenId) {
        final Optional<AusbildungsPraktikumsstelle> ausbildungsPraktikumsstelleOptional = ausbildungsPraktikumsstellenRepository.findById(praktikumsstellenId);
        if (ausbildungsPraktikumsstelleOptional.isPresent()) {
            return ausbildungsPraktikumsstelleOptional.get();
        }
        throw new ResourceNotFoundException("Praktikumsstelle nicht gefunden.");
    }

    private StudiumsPraktikumsstelle findByIdOrThrowStudiumpraktikumsstelle(UUID praktikumsstellenId) {
        final Optional<StudiumsPraktikumsstelle> studiumsPraktikumsstelleOptional = studiumsPraktikumsstellenRepository.findById(praktikumsstellenId);
        if (studiumsPraktikumsstelleOptional.isPresent()) {
            return studiumsPraktikumsstelleOptional.get();
        }
        throw new ResourceNotFoundException("Praktikumsstelle nicht gefunden.");
    }
}
