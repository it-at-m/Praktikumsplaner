package de.muenchen.oss.praktikumsplaner.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.muenchen.oss.praktikumsplaner.validators.AusbildungOrStudiumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { AusbildungOrStudiumValidator.class })
@Documented
public @interface AusbildungOrStudium {
    String message() default "{constraints.AusbildungOrStudium}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String projektarbeit();

    String ausbildungsjahr();

    String ausbildungsrichtung();

    String studienart();

    String programmierkenntnisse();

    String studiensemester();
}
