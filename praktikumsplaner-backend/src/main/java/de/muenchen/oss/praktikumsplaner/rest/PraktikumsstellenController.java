package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.service.PraktikumsstellenService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
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
    public static final String HAS_ROLE_NWK = "hasRole(T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).NWK.name())";
    public static final String HAS_ROLE_AUSBILDUNGSLEITUNG = "hasRole(T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())";
    private final PraktikumsstellenService praktikumsstellenService;

    @PreAuthorize("!" + HAS_ROLE_NWK)
    @PostMapping("/studium")
    @ResponseStatus(HttpStatus.CREATED)
    public StudiumsPraktikumsstelleDto createStudiumsPraktikumsstelle(final @Valid @RequestBody
    CreateStudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto) {
        return praktikumsstellenService.normalizeAndSaveStudiumsPraktikumsstelle(createStudiumsPraktikumsstelleDto);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PostMapping("/studium/ausbildungsleitung")
    @ResponseStatus(HttpStatus.CREATED)
    public StudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleWithMeldezeitraum(final @Valid @RequestBody
    CreateStudiumsPraktikumsstelleWithMeldezeitraumDto createStudiumsPraktikumsstelleWithMeldezeitraumDto) {
        return praktikumsstellenService.saveStudiumsPraktikumsstelleWithMeldezeitraum(createStudiumsPraktikumsstelleWithMeldezeitraumDto);
    }

    @PreAuthorize("!" + HAS_ROLE_NWK)
    @PostMapping("/ausbildung")
    @ResponseStatus(HttpStatus.CREATED)
    public AusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelle(final @Valid @RequestBody
    CreateAusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto) {
        return praktikumsstellenService.normalizeAndSaveAusbildungsPraktikumsstelle(createAusbildungsPraktikumsstelleDto);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PostMapping("/ausbildung/ausbildungsleitung")
    @ResponseStatus(HttpStatus.CREATED)
    public AusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleWithMeldezeitraum(final @Valid @RequestBody
    CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto createAusbildungsPraktikumsstelleWithMeldezeitraumDto) {
        return praktikumsstellenService.saveAusbildungsPraktikumsstelleWithMeldezeitraum(createAusbildungsPraktikumsstelleWithMeldezeitraumDto);
    }

    @PreAuthorize(
        "hasAnyRole(T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name(),T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDER.name())"
    )
    @GetMapping
    public Map<String, List<PraktikumsstelleDto>> getAllPraktiumsstellenInSpecificMeldezeitraum(
            @RequestParam(name = "meldezeitraum", required = false) final String meldezeitraum) {
        if (MELDEZEITRAUM_CURRENT.equals(meldezeitraum)) {
            return praktikumsstellenService.getAllInCurrentMeldezeitraumGroupedByDienststelle();
        } else if (MELDEZEITRAUM_MOST_RECENT.equals(meldezeitraum)) {
            return praktikumsstellenService.getRecentPraktikumsstellenGroupedByDienststelle();
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
    @PutMapping("/ausbildung/{praktikumsstellenId}")
    public void updateAusbildungPraktikumsstelle(@PathVariable(name = PRAKTIKUMSSTELLEN_ID) final UUID praktikumsstellenId,
            @RequestBody final UpdateAusbildungsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {
        praktikumsstellenService.updateAusbildungsPraktikumsstelle(praktikumsstellenId, praktikumsstelleDto);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PutMapping("/studium/{praktikumsstellenId}")
    public void updateStudiumPraktikumsstelle(@PathVariable(name = PRAKTIKUMSSTELLEN_ID) final UUID praktikumsstellenId,
            @RequestBody final UpdateStudiumsPraktikumsstelleWithMeldezeitraumDto praktikumsstelleDto) {
        praktikumsstellenService.updateStudiumsPraktikumsstelle(praktikumsstellenId, praktikumsstelleDto);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @DeleteMapping("/studium/{praktikumsstellenId}")
    public void deleteStudiumPratkikumsstelle(@PathVariable(name = PRAKTIKUMSSTELLEN_ID) final UUID praktikumsstellenId) {
        praktikumsstellenService.deleteStudiumsPraktikumsstelle(praktikumsstellenId);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @DeleteMapping("/ausbildung/{praktikumsstellenId}")
    public void deleteAusbildungPratkikumsstelle(@PathVariable(name = PRAKTIKUMSSTELLEN_ID) final UUID praktikumsstellenId) {
        praktikumsstellenService.deleteAusbildungsPraktikumsstelle(praktikumsstellenId);
    }
}
