package de.muenchen.oss.praktikumsplaner.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.oss.praktikumsplaner.annotations.StartDateBeforeEndDate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StartDateBeforeEndDateValidatorTest {

    @StartDateBeforeEndDate(startDate = "startDate", endDate = "endDate")
    private record TestClass(LocalDate startDate, LocalDate endDate){}

    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testIsValidWithValidObjects() {
        final LocalDate startDate = LocalDate.of(2020, 10, 10);
        final LocalDate endDate = LocalDate.of(2021, 10, 10);

        final TestClass testObject = new TestClass(
                startDate,
                endDate);

        assertTrue(validator.validate(testObject).isEmpty());
    }

    @Test
    void testIsValidWithInvalidObjectThrowingExceptionInValidator() {
        final LocalDate startDate = LocalDate.of(2020, 10, 10);
        final LocalDate endDate = null;

        final TestClass meldezeitraumDTO = new TestClass(
                startDate,
                endDate);

        assertThrows(Exception.class, () -> validator.validate(meldezeitraumDTO));
    }

    @Test
    void testIsValidWithInvalidObjects() {
        final LocalDate startDate = LocalDate.of(2020, 10, 10);
        final LocalDate endDate = LocalDate.of(2019, 10, 10);

        final TestClass meldezeitraumDTO = new TestClass(
                startDate,
                endDate);

        assertFalse(validator.validate(meldezeitraumDTO).isEmpty());
    }
}
