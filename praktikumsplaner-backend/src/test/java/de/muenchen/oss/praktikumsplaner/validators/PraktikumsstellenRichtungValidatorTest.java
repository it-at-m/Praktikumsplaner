package de.muenchen.oss.praktikumsplaner.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreatePraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PraktikumsstellenRichtungValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void createValidator() {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testAusbildungWithAusbildungsjahrOnlyIsValid() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.FISI)
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR2))
                .studiensemester(null)
                .programmierkenntnisse(false)
                .build();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void testAusbildungRequiresAusbildungsjahr() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.FISI)
                .ausbildungsjahr(null)
                .studiensemester(null)
                .programmierkenntnisse(false)
                .build();

        final Set<ConstraintViolation<CreatePraktikumsstelleDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("ausbildungsjahr", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testAusbildungRejectsStudiensemester() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.FISI)
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR1))
                .studiensemester(Set.of(Studiensemester.SEMESTER1))
                .programmierkenntnisse(false)
                .build();

        final Set<ConstraintViolation<CreatePraktikumsstelleDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("studiensemester", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testStudiumWithStudiensemesterOnlyIsValid() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.BSC)
                .ausbildungsjahr(null)
                .studiensemester(Set.of(Studiensemester.SEMESTER4))
                .programmierkenntnisse(true)
                .build();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void testStudiumRequiresStudiensemester() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.BSC)
                .ausbildungsjahr(null)
                .studiensemester(null)
                .programmierkenntnisse(true)
                .build();

        final Set<ConstraintViolation<CreatePraktikumsstelleDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("studiensemester", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testStudiumRejectsAusbildungsjahr() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.BSC)
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR3))
                .studiensemester(Set.of(Studiensemester.SEMESTER2))
                .programmierkenntnisse(true)
                .build();

        final Set<ConstraintViolation<CreatePraktikumsstelleDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("ausbildungsjahr", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testStudiumRequiresProgrammierkenntnisse() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.BSC)
                .ausbildungsjahr(null)
                .studiensemester(Set.of(Studiensemester.SEMESTER2))
                .programmierkenntnisse(false)
                .build();

        final Set<ConstraintViolation<CreatePraktikumsstelleDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("programmierkenntnisse", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testAusbildungDoesNotRequireProgrammierkenntnisse() {
        final CreatePraktikumsstelleDto dto = createBaseBuilder()
                .richtung(Bildungsrichtung.FISI)
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR1))
                .studiensemester(null)
                .programmierkenntnisse(false)
                .build();

        assertTrue(validator.validate(dto).isEmpty());
    }

    private static CreatePraktikumsstelleDto.CreatePraktikumsstelleDtoBuilder createBaseBuilder() {
        return CreatePraktikumsstelleDto.builder()
                .dienststelle("ITM-TEST")
                .oertlicheAusbilder("Max Mustermann")
                .email("max.mustermann@example.org")
                .taetigkeiten("Testtaetigkeiten")
                .dringlichkeit(Dringlichkeit.DRINGEND);
    }
}
