package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleWithMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.service.PraktikumsstellenService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/praktikumsstellen")
public class PraktikumsstellenController {

    private final PraktikumsstellenService praktikumsstellenService;

    @PreAuthorize("!hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).NWK.name())")
    @PostMapping("/studium")
    @ResponseStatus(HttpStatus.CREATED)
    public StudiumsPraktikumsstelleDto createStudiumsPraktikumsstelle(final @Valid @RequestBody
    CreateStudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto) {
        return praktikumsstellenService.saveStudiumsPraktikumsstelle(createStudiumsPraktikumsstelleDto);
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping("/studium/ausbildungsleitung")
    @ResponseStatus(HttpStatus.CREATED)
    public StudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleWithMeldezeitraum(final @Valid @RequestBody
    CreateStudiumsPraktikumsstelleWithMeldezeitraumDto createStudiumsPraktikumsstelleWithMeldezeitraumDto) {
        return praktikumsstellenService.saveStudiumsPraktikumsstelleWithMeldezeitraum(createStudiumsPraktikumsstelleWithMeldezeitraumDto);
    }

    @PreAuthorize("!hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).NWK.name())")
    @PostMapping("/ausbildung")
    @ResponseStatus(HttpStatus.CREATED)
    public AusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelle(final @Valid @RequestBody
    CreateAusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto) {
        return praktikumsstellenService.saveAusbildungsPraktikumsstelle(createAusbildungsPraktikumsstelleDto);
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping("/ausbildung/ausbildungsleitung")
    @ResponseStatus(HttpStatus.CREATED)
    public AusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleWithMeldezeitraum(final @Valid @RequestBody
    CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto createAusbildungsPraktikumsstelleWithMeldezeitraumDto) {
        return praktikumsstellenService.saveAusbildungsPraktikumsstelleWithMeldezeitraum(createAusbildungsPraktikumsstelleWithMeldezeitraumDto);
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @GetMapping
    public TreeMap<String, List<PraktikumsstelleDto>> getAllPraktiumsstellenInSpecificMeldezeitraum(
            @RequestParam(name = "meldezeitraum", required = false) String meldezeitraum) {
        if (meldezeitraum.equals("current")) {
            return praktikumsstellenService.getAllInCurrentMeldezeitraumGroupedByDienststelle();
        } else if (meldezeitraum.equals("most_recent")) {
            return praktikumsstellenService.getRecentPraktikumsstellenGroupedByDienststelle();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wert '" + meldezeitraum + "' für Parameter 'meldezeitraum' ist nicht unterstützt.");
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PatchMapping("/{praktikumsstellenId}")
    public PraktikumsstelleDto assignNwk(@PathVariable(name = "praktikumsstellenId") UUID praktikumsstellenId,
            @RequestParam(name = "nwkId", required = false) UUID nwkId) {
        if (nwkId == null) {
            return praktikumsstellenService.unassignNwk(praktikumsstellenId);
        }
        return praktikumsstellenService.assignNwk(praktikumsstellenId, nwkId);
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @DeleteMapping("/{praktikumsstellenId}")
    public void deletePratkikumsstelle(@PathVariable(name = "praktikumsstellenId") UUID praktikumsstellenId) {
        praktikumsstellenService.deletePraktikumsstelle(praktikumsstellenId);
    }
}
