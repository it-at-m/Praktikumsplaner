package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbilderDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.exception.ResourceConflictException;
import de.muenchen.oss.praktikumsplaner.repository.PraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.security.AuthUtils;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class PraktikumsstellenService {

    public static final String PRAKTIKUMSSTELLE_NOT_FOUND = "Praktikumsstelle nicht gefunden.";

    private final PraktikumsstellenMapper praktikumsstellenMapper;
    private final PraktikumsstellenRepository praktikumsstellenRepository;
    private final MeldezeitraumService meldezeitraumService;
    private final NwkService nwkService;

    public PraktikumsstelleDto normalizeAndSavePraktikumsstelle(final CreatePraktikumsstelleDto createPraktikumsstelleDto) {
        final Praktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createPraktikumsstelleDto,
                meldezeitraumService.getCurrentMeldezeitraum());
        praktikumsstellenRepository.save(entityPraktikumsstelle);
        return praktikumsstellenMapper.toDto(entityPraktikumsstelle);
    }

    public PraktikumsstelleDto savePraktikumsstelleWithMeldezeitraum(
            final CreatePraktikumsstelleWithMeldezeitraumDto createPraktikumsstelleWithMeldezeitraumDto) {
        final Praktikumsstelle entityPraktikumsstelle = praktikumsstellenMapper.toEntity(createPraktikumsstelleWithMeldezeitraumDto);
        praktikumsstellenRepository.save(entityPraktikumsstelle);
        return praktikumsstellenMapper.toDto(entityPraktikumsstelle);
    }

    public PraktikumsstelleDto assignNwk(final UUID praktikumsstellenID, final UUID nwkID) {
        final Nwk assignedNwk = nwkService.getNwk(nwkID);
        final Praktikumsstelle praktikumsstelle = findByIdOrThrow(praktikumsstellenID);

        if (praktikumsstelle.getAssignedNwk() != null) {
            throw new ResourceConflictException("Praktikumsstelle hat bereits eine zugewiesenen Nachwuchskraft.");
        }

        praktikumsstelle.setAssignedNwk(assignedNwk);
        praktikumsstellenRepository.save(praktikumsstelle);
        return praktikumsstellenMapper.toDto(praktikumsstelle);
    }

    public PraktikumsstelleDto unassignNwk(final UUID praktikumsstellenId) {
        final Praktikumsstelle praktikumsstelle = findByIdOrThrow(praktikumsstellenId);
        praktikumsstelle.setAssignedNwk(null);
        praktikumsstellenRepository.save(praktikumsstelle);
        return praktikumsstellenMapper.toDto(praktikumsstelle);
    }

    public List<PraktikumsstelleDto> getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum() {
        final UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return praktikumsstellenRepository.findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(lastMeldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDto)
                .toList();
    }

    public List<PraktikumsstelleDto> getAllInCurrentMeldezeitraum() {
        final UUID currentMeldezeitraumID = meldezeitraumService.getCurrentMeldezeitraum().id();
        return filterPraktikumsstellenForCurrentRole(getPraktikumsstellen(currentMeldezeitraumID));
    }

    public List<PraktikumsstelleDto> getRecentPraktikumsstellen() {
        final UUID lastMeldezeitraumID = meldezeitraumService.getMostRecentPassedMeldezeitraum().id();
        return filterPraktikumsstellenForCurrentRole(getPraktikumsstellen(lastMeldezeitraumID));
    }

    public void deletePraktikumsstelle(final UUID praktikumsstellenId) {
        if (!praktikumsstellenRepository.existsById(praktikumsstellenId)) {
            throw new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND);
        }
        praktikumsstellenRepository.deleteById(praktikumsstellenId);
    }

    public void updatePraktikumsstelle(final UUID praktikumsstellenId, final UpdatePraktikumsstelleDto praktikumsstelleDto) {
        final Praktikumsstelle praktikumsstelleToUpdate = findByIdOrThrow(praktikumsstellenId);

        if (praktikumsstelleToUpdate.getAssignedNwk() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eine zugewiesene Praktikumsstelle darf nicht bearbeitet werden.");
        }

        praktikumsstellenRepository.save(praktikumsstellenMapper.toEntity(praktikumsstellenId, praktikumsstelleDto));
    }

    public List<PraktikumsstelleDto> getPraktikumsstellen(final UUID meldezeitraumID) {
        meldezeitraumService.checkExists(meldezeitraumID);
        final List<PraktikumsstelleDto> praktikumsstellen = praktikumsstellenRepository.findAllByMeldezeitraumID(meldezeitraumID).stream()
                .map(praktikumsstellenMapper::toDto)
                .sorted(Comparator.comparing(PraktikumsstelleDto::dienststelle))
                .toList();
        return filterPraktikumsstellenForCurrentRole(praktikumsstellen);
    }

    protected List<PraktikumsstelleDto> filterPraktikumsstellenForCurrentRole(final List<PraktikumsstelleDto> praktikumsstellen) {
        if (AuthUtils.isAusbildungsleitung()) {
            return praktikumsstellen;
        }

        if (AuthUtils.isAusbilder()) {
            final String usermail = AuthUtils.getMailFromUser();
            final String userDepartment = AuthUtils.getDepartmentFromUser();

            return praktikumsstellen.stream()
                    .filter(dto -> dto.dienststelle().startsWith(userDepartment) ||
                            dto.ausbilder().stream().map(AusbilderDto::email).anyMatch(email -> email.equals(usermail)))
                    .toList();
        }

        throw new AuthorizationDeniedException("Zugriffsrolle fehlt");
    }

    private Praktikumsstelle findByIdOrThrow(final UUID praktikumsstellenId) {
        return praktikumsstellenRepository.findById(praktikumsstellenId)
                .orElseThrow(() -> new ResourceNotFoundException(PRAKTIKUMSSTELLE_NOT_FOUND));
    }
}
