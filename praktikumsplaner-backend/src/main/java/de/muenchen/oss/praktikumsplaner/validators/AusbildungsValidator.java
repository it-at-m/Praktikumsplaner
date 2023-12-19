package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class AusbildungsValidator implements ConstraintValidator<AusbildungsAnnotation, Object> {

    private String ausbildungsrichtungField;

    @Override
    public void initialize(final AusbildungsAnnotation constraintAnnotation) {
        this.ausbildungsrichtungField = constraintAnnotation.ausbildungsrichtung();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        try {
            if (object == null) {
                return true;
            }

            final Method ausbildungsrichtungGetter = object.getClass().getMethod(ausbildungsrichtungField);
            final Ausbildungsrichtung ausbildungsrichtung = (Ausbildungsrichtung) ausbildungsrichtungGetter.invoke(object);

            return ausbildungsrichtung == Ausbildungsrichtung.FISI;

        } catch (Exception e) {
            return false;
        }
    }
}
