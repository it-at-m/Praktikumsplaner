package de.muenchen.oss.praktikumsplaner.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.muenchen.oss.praktikumsplaner.validators.StudiengangOrAusbildungsrichtungValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = StudiengangOrAusbildungsrichtungValidator.class)
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
public @interface StudiengangOrAusbildungsrichtungConstraint {
    String message() default "Entweder Studiengang oder Ausbildungsrichtung muss gesetzt sein, aber nicht beide";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String ausbildungsrichtungGetMethod();

    String studiengangGetMethod();
}
