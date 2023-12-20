package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class AusbildungsValidator implements ConstraintValidator<AusbildungsAnnotation, Object> {

    private String studiengangField;

    @Override
    public void initialize(final AusbildungsAnnotation constraintAnnotation) {
        this.studiengangField = constraintAnnotation.studiengang();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        try {
            if (object == null) {
                return true;
            }

            final Method studiengangGetter = object.getClass().getMethod(studiengangField);
            final Studiengang studiengang = (Studiengang) studiengangGetter.invoke(object);

            return studiengang == Studiengang.FISI;

        } catch (Exception e) {
            return false;
        }
    }
}
