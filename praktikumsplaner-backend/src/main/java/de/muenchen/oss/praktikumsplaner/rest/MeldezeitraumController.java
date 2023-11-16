package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.service.MeldezeitraumService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/meldezeitraum")
@AllArgsConstructor
public class MeldezeitraumController {

    private final MeldezeitraumService meldezeitraumService;

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeldezeitraumDTO createMeldezeitraum(final @Valid @RequestBody
    CreateMeldezeitraumDTO meldezeitraumDto) {
        return meldezeitraumService.createMeldezeitraum(meldezeitraumDto);
    }
}
