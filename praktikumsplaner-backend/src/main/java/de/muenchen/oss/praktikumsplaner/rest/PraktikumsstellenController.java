package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.security.Authorities.HAS_ANY_ROLE_AUSBILDUNGSLEITUNG_AUSBILDER;
import static de.muenchen.oss.praktikumsplaner.security.Authorities.HAS_ROLE_AUSBILDUNGSLEITUNG;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleViewDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.service.PraktikumsstellenService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/praktikumsstellen")
public class PraktikumsstellenController {

    public static final String PRAKTIKUMSSTELLEN_ID = "praktikumsstellenId";
    public static final String MELDEZEITRAUM_CURRENT = "current";
    public static final String MELDEZEITRAUM_MOST_RECENT = "most_recent";
    private final PraktikumsstellenService praktikumsstellenService;

    @PreAuthorize(HAS_ANY_ROLE_AUSBILDUNGSLEITUNG_AUSBILDER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PraktikumsstelleDto createPraktikumsstelle(final @Valid @RequestBody
    CreatePraktikumsstelleDto createDto) {
        return praktikumsstellenService.normalizeAndSave(createDto);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PostMapping("/ausbildungsleitung")
    @ResponseStatus(HttpStatus.CREATED)
    public PraktikumsstelleDto createPraktikumsstelleWithMeldezeitraum(final @Valid @RequestBody
    CreatePraktikumsstelleDto createDto) {
        return praktikumsstellenService.saveWithMeldezeitraum(createDto);
    }

    @PreAuthorize(HAS_ANY_ROLE_AUSBILDUNGSLEITUNG_AUSBILDER)
    @GetMapping
    public List<PraktikumsstelleViewDto> getAllPraktiumsstellenInSpecificMeldezeitraum(
            @RequestParam(name = "meldezeitraum", required = false) final String meldezeitraum) {
        if (MELDEZEITRAUM_CURRENT.equals(meldezeitraum)) {
            return praktikumsstellenService.getAllInCurrentMeldezeitraum();
        } else if (MELDEZEITRAUM_MOST_RECENT.equals(meldezeitraum)) {
            return praktikumsstellenService.getRecentPraktikumsstellen();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wert '" + meldezeitraum + "' für Parameter 'meldezeitraum' ist nicht unterstützt.");
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PatchMapping("/{praktikumsstellenId}")
    public PraktikumsstelleDto assignNwk(@PathVariable(name = PRAKTIKUMSSTELLEN_ID) final UUID praktikumsstellenId,
            @RequestParam(name = "nwkId", required = false) final UUID nwkId) {
        if (nwkId == null) {
            return praktikumsstellenService.unassignNwk(praktikumsstellenId);
        }
        return praktikumsstellenService.assignNwk(praktikumsstellenId, nwkId);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PutMapping("/{praktikumsstellenId}")
    public void updatePraktikumsstelle(@PathVariable(name = PRAKTIKUMSSTELLEN_ID) final UUID praktikumsstellenId,
            @RequestBody final UpdatePraktikumsstelleDto praktikumsstelleDto) {
        praktikumsstellenService.updatePraktikumsstelle(praktikumsstellenId, praktikumsstelleDto);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @DeleteMapping("/{praktikumsstellenId}")
    public void deletePratkikumsstelle(@PathVariable(name = PRAKTIKUMSSTELLEN_ID) final UUID praktikumsstellenId) {
        praktikumsstellenService.deletePraktikumsstelle(praktikumsstellenId);
    }
}
