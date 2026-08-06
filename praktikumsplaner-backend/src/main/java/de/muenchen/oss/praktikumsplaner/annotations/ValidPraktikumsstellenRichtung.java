package de.muenchen.oss.praktikumsplaner.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.muenchen.oss.praktikumsplaner.validators.PraktikumsstellenRichtungValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { PraktikumsstellenRichtungValidator.class })
@Documented
public @interface ValidPraktikumsstellenRichtung {
    String message() default "Ungültige Kombination von Richtung und Praktikumsstellenfeldern.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
