package de.muenchen.oss.praktikumsplaner.rest;

import static de.muenchen.oss.praktikumsplaner.security.Authorities.HAS_ROLE_AUSBILDUNGSLEITUNG;

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

    public static final String PERIOD_CURRENT = "current";
    public static final String PERIOD_PAST = "past";
    public static final String PERIOD_FUTURE = "future";
    private final MeldezeitraumService meldezeitraumService;

    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeldezeitraumDto createMeldezeitraum(final @Valid @RequestBody
    CreateMeldezeitraumDto meldezeitraumDto) {
        return meldezeitraumService.createMeldezeitraum(meldezeitraumDto);
    }

    @GetMapping
    public List<MeldezeitraumDto> getMeldezeitraeume(@RequestParam(name = "period", required = false) final String period) {
        if (PERIOD_CURRENT.equalsIgnoreCase(period)) {
            try {
                final MeldezeitraumDto meldezeitraumDto = meldezeitraumService.getCurrentMeldezeitraum();
                return List.of(meldezeitraumDto);
            } catch (ValidationException ve) {
                return new ArrayList<>();
            }
        } else if (PERIOD_PAST.equalsIgnoreCase(period)) {
            return meldezeitraumService.getPassedMeldezeitraeume();
        } else if (PERIOD_FUTURE.equalsIgnoreCase(period)) {
            return meldezeitraumService.getUpcomingMeldezeitraeume();
        } else if (period != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wert '" + period + "' für Parameter 'period' ist nicht unterstützt.");
        }
        return meldezeitraumService.getAllMeldezeitraeume();
    }

    @DeleteMapping(path = "/{id}")
    @Transactional
    @PreAuthorize(HAS_ROLE_AUSBILDUNGSLEITUNG)
    public void deleteMeldezeitraum(@PathVariable(name = "id") final UUID id) {
        meldezeitraumService.deleteMeldezeitraumById(id);
    }
}
