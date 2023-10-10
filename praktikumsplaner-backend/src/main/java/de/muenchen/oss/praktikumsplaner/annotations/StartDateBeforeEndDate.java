package de.muenchen.oss.praktikumsplaner.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.muenchen.oss.praktikumsplaner.validators.StartDateBeforeEndDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { StartDateBeforeEndDateValidator.class })
@Documented
public @interface StartDateBeforeEndDate {
    String message() default "{constraints.validDates}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startDate();

    String endDate();
}
