package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumCreateDTO;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumDTO;
import de.muenchen.oss.praktikumsplaner.service.MeldezeitraumService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public MeldezeitraumDTO createMeldezeitraum(final @Valid @RequestBody MeldezeitraumCreateDTO meldezeitraumDto) {
        final MeldezeitraumDTO meldezeitraum = meldezeitraumService.createMeldezeitraum(meldezeitraumDto);
        return meldezeitraum;
    }
}
