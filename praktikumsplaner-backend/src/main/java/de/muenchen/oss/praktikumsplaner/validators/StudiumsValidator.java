package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.StudiumsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class StudiumsValidator implements ConstraintValidator<StudiumsAnnotation, Object> {

    private String studiengangField;

    @Override
    public void initialize(final StudiumsAnnotation constraintAnnotation) {
        this.studiengangField = constraintAnnotation.studiengangGetMethod();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        try {
            if (object == null) {
                return true;
            }

            final Method studiengangGetter = object.getClass().getMethod(studiengangField);
            final Studiengang studiengang = (Studiengang) studiengangGetter.invoke(object);

            return studiengang == Studiengang.BSC || studiengang == Studiengang.VI || studiengang == Studiengang.BWI;

        } catch (Exception e) {
            return false;
        }
    }
}
