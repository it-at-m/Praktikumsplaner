package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.service.PraktikumsstellenService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/praktikumsstellen")
public class PraktikumsstellenController {

    private final PraktikumsstellenService praktikumsstellenService;

    @PreAuthorize("!hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).NWK.name())")
    @PostMapping("/studium")
    @ResponseStatus(HttpStatus.CREATED)
    public StudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelle(final @Valid @RequestBody
    CreateStudiumsPraktikumsstelleDTO createStudiumsPraktikumsstelleDTO) {
        return praktikumsstellenService.saveStudiumsPraktikumsstelle(createStudiumsPraktikumsstelleDTO);
    }

    @PreAuthorize("!hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).NWK.name())")
    @PostMapping("/ausbildung")
    @ResponseStatus(HttpStatus.CREATED)
    public AusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelle(final @Valid @RequestBody
    CreateAusbildungsPraktikumsstelleDTO createAusbildungsPraktikumsstelleDTO) {
        return praktikumsstellenService.saveAusbildungsPraktikumsstelle(createAusbildungsPraktikumsstelleDTO);
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TreeMap<String, List<PraktikumsstelleDTO>> getAllPraktikumsstellen() {
        return praktikumsstellenService.getAllPraktiumsstellen();
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PatchMapping("/{praktikumsstellenId}")
    @ResponseStatus(HttpStatus.OK)
    public PraktikumsstelleDTO assignNWK(@PathVariable UUID praktikumsstellenId, @RequestParam(required = false) UUID nwkId) {
        if (nwkId == null) {
            return praktikumsstellenService.unassignNWK(praktikumsstellenId);
        }
        return praktikumsstellenService.assignNWK(praktikumsstellenId, nwkId);
    }
}
