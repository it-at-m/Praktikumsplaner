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
        this.ausbildungsrichtungField = constraintAnnotation.ausbildungsrichtungGetMethod();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        try {
            if (object == null) {
                return true;
            }

            final Method getter = object.getClass().getMethod(ausbildungsrichtungField);
            final Object value = getter.invoke(object);

            // Null is valid (optional: handle differently if required)
            if (value == null) {
                return true;
            }

            // Explicit type check before casting
            if (value instanceof Ausbildungsrichtung) {
                return value == Ausbildungsrichtung.FISI;
            } else {
                return false; // or throw, if this is unexpected logic-wise
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
}
