package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.service.MeldezeitraumService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
@RequestMapping(path = "/meldezeitraum")
@AllArgsConstructor
public class MeldezeitraumController {

    private final MeldezeitraumService meldezeitraumService;

    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeldezeitraumDto createMeldezeitraum(final @Valid @RequestBody
    CreateMeldezeitraumDto meldezeitraumDto) {
        return meldezeitraumService.createMeldezeitraum(meldezeitraumDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MeldezeitraumDto> getMeldezeitraum(@RequestParam(required = false) String restriction) {
        if (restriction != null && restriction.equalsIgnoreCase("current")) {
            try {
                MeldezeitraumDto meldezeitraumDto = meldezeitraumService.getCurrentMeldezeitraum();
                return List.of(meldezeitraumDto);
            } catch (ValidationException ve) {
                return new ArrayList<>();
            }
        } else if (restriction != null && restriction.equalsIgnoreCase("history")) {
            return meldezeitraumService.getPassedMeldezeitraeume();
        } else if (restriction != null && restriction.equalsIgnoreCase("future")) {
            return meldezeitraumService.getUpcomingMeldezeitraeume();
        } else if (restriction != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value '" + restriction + "' for parameter restriction not supported.");
        }

        return new ArrayList<>();
    }
}
