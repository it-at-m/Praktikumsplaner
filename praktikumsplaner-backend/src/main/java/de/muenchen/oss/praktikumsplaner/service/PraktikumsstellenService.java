package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleViewDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.NwkRepository;
import de.muenchen.oss.praktikumsplaner.repository.PraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.security.AuthUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    public static final String PRAKTIKUMSSTELLE_NOT_FOUND = "Praktikumsstelle nicht gefunden.";
    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final PraktikumsstellenRepository praktikumsstellenRepository;
    private final MeldezeitraumService meldezeitraumService;
    private final NwkRepository nwkRepository;

    public PraktikumsstelleDto normalizeAndSave(final CreatePraktikumsstelleDto createDto) {
        final Praktikumsstelle entity = praktikumsstellenMapper.toEntity(createDto, meldezeitraumService.getCurrentMeldezeitraum());
        praktikumsstellenRepository.save(entity);
        return praktikumsstellenMapper.toDto(entity);
    }

    public PraktikumsstelleDto saveWithMeldezeitraum(final CreatePraktikumsstelleDto createDto) {
        final Praktikumsstelle entity = praktikumsstellenMapper.toEntity(createDto);
        praktikumsstellenRepository.save(entity);
        return praktikumsstellenMapper.toDto(entity);
    }

    public PraktikumsstelleDto assignNwk(final UUID praktikumsstellenID, final UUID nwkID) {
        final Nwk assignedNwk = nwkRepository.findById(nwkID).orElseThrow();

        final Praktikumsstelle praktikumsstelle = praktikumsstellenRepository.findById(praktikumsstellenID)
                .orElseThrow(() -> new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND));
        if (praktikumsstelle.getAssignedNwk() == null) {
            praktikumsstelle.setAssignedNwk(assignedNwk);
            praktikumsstellenRepository.save(praktikumsstelle);
            return praktikumsstellenMapper.toDto(praktikumsstelle);
        }
        throw new ResourceConflictException("Praktikumsstelle hat bereits eine zugewiesenen Nachwuchskraft.");
    }

    public PraktikumsstelleDto unassignNwk(final UUID praktikumsstellenId) {
        final Praktikumsstelle praktikumsstelle = praktikumsstellenRepository.findById(praktikumsstellenId)
                .orElseThrow(() -> new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND));
        praktikumsstelle.setAssignedNwk(null);
        praktikumsstellenRepository.save(praktikumsstelle);
        return praktikumsstellenMapper.toDto(praktikumsstelle);
    }

    public List<PraktikumsstelleViewDto> getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum() {
        final UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return praktikumsstellenRepository
                .findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(lastMeldezeitraumID)
                .stream().map(praktikumsstellenMapper::toDto).toList();
    }

    // removed type-specific helpers

    public List<PraktikumsstelleViewDto> getAllInCurrentMeldezeitraum() {
        final UUID currentMeldezeitraumID = meldezeitraumService.getCurrentMeldezeitraum().id();
        return filterPraktikumsstellenForCurrentRole(getPraktikumsstellen(currentMeldezeitraumID));
    }

    public List<PraktikumsstelleViewDto> getRecentPraktikumsstellen() {
        final UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return filterPraktikumsstellenForCurrentRole(getPraktikumsstellen(lastMeldezeitraumID));
    }

    public void deletePraktikumsstelle(final UUID praktikumsstellenId) {
        if (praktikumsstellenRepository.existsById(praktikumsstellenId)) {
            praktikumsstellenRepository.deleteById(praktikumsstellenId);
        } else {
            throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
        }
    }

    public void updatePraktikumsstelle(final UUID praktikumsstellenId, final UpdatePraktikumsstelleDto praktikumsstelleDto) {
        final Praktikumsstelle updated = praktikumsstellenMapper.toEntity(praktikumsstellenId, praktikumsstelleDto);
        final Praktikumsstelle current = praktikumsstellenRepository.findById(praktikumsstellenId)
                .orElseThrow(() -> new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND));

        if (current.getAssignedNwk() != null) {
            updatePraktikumsstelleWithAssignedNwk(current, praktikumsstelleDto);
        } else {
            praktikumsstellenRepository.save(updated);
        }
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void updatePraktikumsstelleWithAssignedNwk(final Praktikumsstelle current, final UpdatePraktikumsstelleDto dto) {
        // Check if any field which is not supposed to change changed
        if (current.getDringlichkeit() != dto.dringlichkeit()
                || !Objects.equals(current.getNamentlicheAnforderung(), dto.namentlicheAnforderung())
                || current.isPlanstelleVorhanden() != dto.planstelleVorhanden()
                || !current.getMeldezeitraumID().toString().equals(dto.meldezeitraumID().toString())
                || !Objects.equals(current.getProgrammierkenntnisse(), dto.programmierkenntnisse())
                || !Objects.equals(current.getWuensche(), dto.wuensche())) {
            throw new ResourceConflictException("Unerlaubter Versuch der Änderung von Daten");
        }

        // Per-art immutables
        switch (current.getArt()) {
        case AUSBILDUNG -> {
            if (current.isProjektarbeit()
                    || !Objects.equals(current.getAusbildungsjahr(), dto.ausbildungsjahr())
                    || !Objects.equals(current.getMinderjaehrigMoeglich(), dto.minderjaehrigMoeglich())) {
                throw new ResourceConflictException("Unerlaubter Versuch der Änderung von Daten");
            }
        }
        case STUDIUM -> {
            /* no extra immutables besides above and studiensemester validated elsewhere if needed */ }
        }

        praktikumsstellenMapper.updatePraktikumsstelle(current, dto);
        praktikumsstellenRepository.save(current);
    }

    private List<PraktikumsstelleViewDto> getPraktikumsstellen(final UUID meldezeitraumID) {
        final List<PraktikumsstelleViewDto> list = praktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumID)
                .stream()
                .map(praktikumsstellenMapper::toDto)
                .toList();
        return list.stream().sorted(Comparator.comparing(PraktikumsstelleViewDto::dienststelle)).toList();
    }

    private List<PraktikumsstelleViewDto> filterPraktikumsstellenForCurrentRole(
            final List<PraktikumsstelleViewDto> praktikumsstellen) {

        if (AuthUtils.isAusbildungsleitung()) {
            return praktikumsstellen;
        }

        if (AuthUtils.isAusbilder()) {
            final String usermail = AuthUtils.getMailFromUser();
            final String userDepartment = AuthUtils.getDepartmentFromUser();

            return praktikumsstellen.stream()
                    .filter(dto -> usermail.equals(dto.email()) || dto.dienststelle().startsWith(userDepartment))
                    .toList();
        }

        throw new AuthorizationDeniedException("Zugriffsrolle fehlt");
    }

    // removed type-specific finders
}
