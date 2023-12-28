package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.StudiumsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudiumsValidatorTest {

    private StudiumsValidator validator;

    @StudiumsAnnotation(studiengang = "studiengang")
    public record TestClass(Studiengang studiengang) {
    }

    @BeforeEach
    public void setUp() {
        validator = new StudiumsValidator();
        StudiumsAnnotation annotation = TestClass.class.getAnnotation(StudiumsAnnotation.class);
        validator.initialize(annotation);
    }

    @Test
    public void testIsValidWithValidObjects() {
        for (Studiengang studiengang : EnumSet.allOf(Studiengang.class)) {
            final TestClass testObject = new TestClass(studiengang);
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
