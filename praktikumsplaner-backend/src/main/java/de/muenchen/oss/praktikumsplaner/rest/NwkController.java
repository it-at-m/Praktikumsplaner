package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.security.Authorities.HAS_ROLE_AUSBILDUNGSLEITUNG;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.service.NwkService;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

    private static final String ACTIVE_STATUS = "aktiv";

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
    public List<NwkDto> getNwks(@RequestParam(name = "status", required = false) final String status,
            @RequestParam(name = "unassigned", required = false) final boolean unassigned) {
        if (status != null) {
            if (ACTIVE_STATUS.equals(status)) {
                return nwkService.findAllActiveNwks();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status-Parameter nicht unterstützt.");
        }
        if (unassigned) {
            return nwkService.findAllUnassignedNwksInCurrentMeldezeitraum();
        }
        return nwkService.findAllNwks();
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
}
