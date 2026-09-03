package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.ValidPraktikumsstellenRichtung;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleI;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class PraktikumsstellenRichtungValidator implements ConstraintValidator<ValidPraktikumsstellenRichtung, PraktikumsstelleI> {

    @Override
    public boolean isValid(final PraktikumsstelleI value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        final Bildungsrichtung richtung = value.richtung();
        if (richtung == null) {
            return true;
        }

        final Set<Ausbildungsjahr> ausbildungsjahr = value.ausbildungsjahr();
        final Set<Studiensemester> studiensemester = value.studiensemester();

        context.disableDefaultConstraintViolation();

        return switch (richtung.getArt()) {
        case AUSBILDUNG -> validateAusbildung(context, ausbildungsjahr, studiensemester);
        case STUDIUM -> validateStudium(context, ausbildungsjahr, studiensemester);
        };
    }

    private static boolean validateAusbildung(final ConstraintValidatorContext context, final Set<Ausbildungsjahr> ausbildungsjahr,
            final Set<Studiensemester> studiensemester) {
        boolean valid = true;

        if (ausbildungsjahr == null || ausbildungsjahr.isEmpty()) {
            addViolation(context, "ausbildungsjahr", "Ausbildungsjahr muss fuer Ausbildungsrichtungen gesetzt sein.");
            valid = false;
        }

        if (studiensemester != null && !studiensemester.isEmpty()) {
            addViolation(context, "studiensemester", "Studiensemester darf fuer Ausbildungsrichtungen nicht gesetzt sein.");
            valid = false;
        }

        return valid;
    }

    private static boolean validateStudium(final ConstraintValidatorContext context, final Set<Ausbildungsjahr> ausbildungsjahr,
            final Set<Studiensemester> studiensemester) {
        boolean valid = true;

        if (studiensemester == null || studiensemester.isEmpty()) {
            addViolation(context, "studiensemester", "Studiensemester muss fuer Studienrichtungen gesetzt sein.");
            valid = false;
        }

        if (ausbildungsjahr != null && !ausbildungsjahr.isEmpty()) {
            addViolation(context, "ausbildungsjahr", "Ausbildungsjahr darf fuer Studienrichtungen nicht gesetzt sein.");
            valid = false;
        }

        return valid;
    }

    private static void addViolation(final ConstraintValidatorContext context, final String property, final String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }
}
