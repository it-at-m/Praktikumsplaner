package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.service.NwkService;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/nachwuchskraft")
public class NwkController {
    private final NwkService nwkService;

    private static final String ACTIVE_STATUS = "aktiv";

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNwkExcel(@RequestBody String base64String) throws IOException {
        nwkService.importNwk(base64String);
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NwkDto> getNwks(@RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "unassigned", required = false) String unassigned) {
        if (status != null) {
            if (ACTIVE_STATUS.equals(status)) {
                return nwkService.findAllActiveNwks();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status-Parameter nicht unterstützt.");
        }
        if (unassigned != null) {
            if (Boolean.TRUE.toString().equals(unassigned)) {
                return nwkService.findAllUnassignedNwks();
            }
        }
        return nwkService.findAllNwks();
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public NwkDto modifyNwk(@RequestBody NwkDto nwkDto) {
        return nwkService.saveNwk(nwkDto);
    }
}
