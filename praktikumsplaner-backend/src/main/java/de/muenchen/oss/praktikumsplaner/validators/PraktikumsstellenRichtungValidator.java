package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.ValidPraktikumsstellenRichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;

public class PraktikumsstellenRichtungValidator implements ConstraintValidator<ValidPraktikumsstellenRichtung, Object> {

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            final Bildungsrichtung richtung = (Bildungsrichtung) invokeGetter(value, "richtung");
            if (richtung == null) {
                return true;
            }

            final Object ausbildungsjahr = invokeGetter(value, "ausbildungsjahr");
            final Object studiensemester = invokeGetter(value, "studiensemester");
            final Object programmierkenntnisse = invokeGetter(value, "programmierkenntnisse");

            context.disableDefaultConstraintViolation();

            return switch (richtung.getArt()) {
                case AUSBILDUNG -> validateAusbildung(context, ausbildungsjahr, studiensemester);
                case STUDIUM -> validateStudium(context, ausbildungsjahr, studiensemester, programmierkenntnisse);
            };
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static boolean validateAusbildung(final ConstraintValidatorContext context, final Object ausbildungsjahr,
            final Object studiensemester) {
        boolean valid = true;

        if (isNullOrEmpty(ausbildungsjahr)) {
            addViolation(context, "ausbildungsjahr", "Ausbildungsjahr muss fuer Ausbildungsrichtungen gesetzt sein.");
            valid = false;
        }

        if (!isNullOrEmpty(studiensemester)) {
            addViolation(context, "studiensemester", "Studiensemester darf fuer Ausbildungsrichtungen nicht gesetzt sein.");
            valid = false;
        }

        return valid;
    }

    private static boolean validateStudium(final ConstraintValidatorContext context, final Object ausbildungsjahr,
            final Object studiensemester, final Object programmierkenntnisse) {
        boolean valid = true;

        if (isNullOrEmpty(studiensemester)) {
            addViolation(context, "studiensemester", "Studiensemester muss fuer Studienrichtungen gesetzt sein.");
            valid = false;
        }

        if (!isNullOrEmpty(ausbildungsjahr)) {
            addViolation(context, "ausbildungsjahr", "Ausbildungsjahr darf fuer Studienrichtungen nicht gesetzt sein.");
            valid = false;
        }

        if (isBlank(programmierkenntnisse)) {
            addViolation(context, "programmierkenntnisse", "Programmierkenntnisse muessen fuer Studienrichtungen gesetzt sein.");
            valid = false;
        }

        return valid;
    }

    private static void addViolation(final ConstraintValidatorContext context, final String property, final String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }

    private static boolean isNullOrEmpty(final Object value) {
        return value == null || value instanceof Collection<?> collection && collection.isEmpty();
    }

    private static boolean isBlank(final Object value) {
        return value == null || value instanceof String string && string.isBlank() || Objects.equals(value, "");
    }

    private static Object invokeGetter(final Object target, final String methodName) throws ReflectiveOperationException {
        final Method getter = target.getClass().getMethod(methodName);
        return getter.invoke(target);
    }
}
