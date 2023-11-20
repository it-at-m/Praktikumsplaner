package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.domain.mappers.NWKMapper;
import de.muenchen.oss.praktikumsplaner.service.NWKService;
import java.io.IOException;
import java.util.ArrayList;
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
    private final NWKMapper nwkMapper;

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNWKExcel(@RequestBody String base64String) throws IOException {
        nwkService.importNWK(base64String);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<NwkDTO> getNWKs(@RequestParam(name = "status", required = false) String status) {
        if ("aktiv".equals(status)) {
            List<NwkDTO> nwkDTOList = new ArrayList<>();
            nwkService.findAllActiveNWKs().forEach(nwk -> nwkDTOList.add(nwkMapper.toDTO(nwk)));

            return nwkDTOList;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status-Parameter ist erforderlich.");
        }
    }
}
