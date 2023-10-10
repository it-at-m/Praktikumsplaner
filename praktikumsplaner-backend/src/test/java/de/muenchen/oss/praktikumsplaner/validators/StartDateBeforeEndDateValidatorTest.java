package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumCreateDTO;
import de.muenchen.oss.praktikumsplaner.dtos.MeldezeitraumDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StartDateBeforeEndDateValidatorTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testIsValidWithValidObjects() {
        final LocalDate startDate = LocalDate.of(2020,10,10);
        final LocalDate endDate = LocalDate.of(2021,10,10);

        final MeldezeitraumDTO meldezeitraumDTO = new MeldezeitraumDTO(
                UUID.randomUUID(),
                "Name",
                startDate,
                endDate
        );

        final MeldezeitraumCreateDTO meldezeitraumCreateDTO = new MeldezeitraumCreateDTO(
                "Name",
                startDate,
                endDate
        );

        final Meldezeitraum meldezeitraum = new Meldezeitraum(
                "Name",
                startDate,
                endDate
        );

        assertTrue(validator.validate(meldezeitraumDTO).isEmpty());
        assertTrue(validator.validate(meldezeitraumCreateDTO).isEmpty());
        assertTrue(validator.validate(meldezeitraum).isEmpty());
    }

    void testIsValidWithInvalidObjects(){
        final LocalDate startDate = LocalDate.of(2020,10,10);
        final LocalDate endDate = LocalDate.of(2019,10,10);

        final MeldezeitraumDTO meldezeitraumDTO = new MeldezeitraumDTO(
                UUID.randomUUID(),
                "Name",
                startDate,
                endDate
        );

        final MeldezeitraumCreateDTO meldezeitraumCreateDTO = new MeldezeitraumCreateDTO(
                "Name",
                startDate,
                endDate
        );

        final Meldezeitraum meldezeitraum = new Meldezeitraum(
                "Name",
                startDate,
                endDate
        );

        assertFalse(validator.validate(meldezeitraumDTO).isEmpty());
        assertFalse(validator.validate(meldezeitraumCreateDTO).isEmpty());
        assertFalse(validator.validate(meldezeitraum).isEmpty());
    }
}
