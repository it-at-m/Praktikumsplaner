package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.service.NWKService;
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
public class NWKController {
    private final NWKService nwkService;

    private final String ACTIVE_STATUS = "aktiv";

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNWKExcel(@RequestBody String base64String) throws IOException {
        nwkService.importNWK(base64String);
    }

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NwkDTO> getNWKs(@RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "unassigned", required = false) String unassigned) {
        if (status != null) {
            if (ACTIVE_STATUS.equals(status)) {
                return nwkService.findAllActiveNWKs();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status-Parameter nicht unterstützt.");
        }
        if (unassigned != null) {
            if (Boolean.TRUE.toString().equals(unassigned)) {
                return nwkService.findAllUnassignedNWKs();
            }
        }
        return nwkService.findAllNWKs();
    }
}
