package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateMeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.MeldezeitraumDto;
import de.muenchen.oss.praktikumsplaner.service.MeldezeitraumService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<MeldezeitraumDto> getMeldezeitraeume(@RequestParam(name = "period", required = false) String period) {
        if (period != null && period.equalsIgnoreCase("current")) {
            try {
                MeldezeitraumDto meldezeitraumDto = meldezeitraumService.getCurrentMeldezeitraum();
                return List.of(meldezeitraumDto);
            } catch (ValidationException ve) {
                return new ArrayList<>();
            }
        } else if (period != null && period.equalsIgnoreCase("past")) {
            return meldezeitraumService.getPassedMeldezeitraeume();
        } else if (period != null && period.equalsIgnoreCase("future")) {
            return meldezeitraumService.getUpcomingMeldezeitraeume();
        } else if (period != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wert '" + period + "' für Parameter 'period' ist nicht unterstützt.");
        }
        return meldezeitraumService.getAllMeldezeitraeume();
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_' + T(de.muenchen.oss.praktikumsplaner.security.AuthoritiesEnum).AUSBILDUNGSLEITUNG.name())")
    public void deleteMeldezeitraum(@PathVariable(name = "id") UUID id) {
        meldezeitraumService.deleteMeldezeitraumById(id);
    }
}
