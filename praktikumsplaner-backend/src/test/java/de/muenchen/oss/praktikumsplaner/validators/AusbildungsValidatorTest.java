package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AusbildungsValidatorTest {

    private AusbildungsValidator validator;

    @AusbildungsAnnotation(studiengang = "ausbildung")
    public record TestClass(Studiengang ausbildung) {
    }

    @BeforeEach
    public void setUp() {
        validator = new AusbildungsValidator();
        AusbildungsAnnotation annotation = TestClass.class.getAnnotation(AusbildungsAnnotation.class);
        validator.initialize(annotation);
    }

    @Test
    public void testIsValidWithValidObjects() {
        for (Studiengang ausbildung: EnumSet.allOf(Studiengang.class)) {
            final TestClass testObject = new TestClass(ausbildung);
            validator.isValid(testObject, null);
        }
    }

    @Test
    public void testIsValidWithNullObject() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    public void testIsValidCatchException() {
        Object testObject = new Object();
        assertFalse(validator.isValid(testObject, null));
    }
}
