package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.security.Authorities.HAS_ROLE_AUSBILDUNGSLEITUNG;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.NwkState;
import de.muenchen.oss.praktikumsplaner.service.NwkService;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/nachwuchskraft")
public class NwkController {
    private final NwkService nwkService;

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNwkExcel(@RequestBody final String base64String) throws IOException {
        nwkService.importNwk(base64String);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNwk(@RequestBody final CreateNwkDto createNwkDto) {
        nwkService.saveNwk(createNwkDto);
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @GetMapping
    public List<NwkDto> getNwks(@RequestParam(required = false) final NwkState state) {
        if (state == null) {
            return nwkService.findAllActiveNwks();
        }
        return switch (state) {
        case INACTIVE -> nwkService.findAllInactiveNwks();
        case UNASSIGNED -> nwkService.findAllUnassignedNwksInCurrentMeldezeitraum();
        };
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PutMapping
    public void updateNwk(@RequestBody final NwkDto nwkDto) {
        if (nwkService.nwkExistsById(nwkDto.id())) {
            nwkService.saveNwk(nwkDto);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Nachwuchskraft mit der ID %s existiert nicht.".formatted(nwkDto.id()));
    }

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @DeleteMapping("/{nwkId}")
    public void deletePraktikumsstelle(@PathVariable final UUID nwkId) {
        nwkService.deleteNwk(nwkId);
    }
}
