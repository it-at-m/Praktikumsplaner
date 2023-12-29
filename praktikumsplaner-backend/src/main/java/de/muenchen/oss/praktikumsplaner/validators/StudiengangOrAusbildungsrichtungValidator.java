package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.StudiengangOrAusbildungsrichtungConstraint;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class StudiengangOrAusbildungsrichtungValidator implements ConstraintValidator<StudiengangOrAusbildungsrichtungConstraint, Object> {

    private String ausbildungsrichtungField;
    private String studiengangField;

    @Override
    public void initialize(StudiengangOrAusbildungsrichtungConstraint constraintAnnotation) {
        this.ausbildungsrichtungField = constraintAnnotation.ausbildungsrichtungGetMethod();
        this.studiengangField = constraintAnnotation.studiengangGetMethod();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            final Method ausbildungsrichtungGetter = obj.getClass().getMethod(ausbildungsrichtungField);
            final Ausbildungsrichtung ausbildungsrichtung = (Ausbildungsrichtung) ausbildungsrichtungGetter.invoke(obj);

            final Method studiengangGetter = obj.getClass().getMethod(studiengangField);
            final Studiengang studiengang = (Studiengang) studiengangGetter.invoke(obj);

            return (studiengang == null) != (ausbildungsrichtung == null);
        } catch (Exception e) {
            return false;
        }
    }
}
