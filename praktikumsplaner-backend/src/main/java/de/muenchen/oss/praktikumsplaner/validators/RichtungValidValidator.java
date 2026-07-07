package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.RichtungValid;
import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.enums.RichtungsArt;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RichtungValidValidator implements ConstraintValidator<RichtungValid, Praktikumsstelle> {

    @Override
    public boolean isValid(final Praktikumsstelle value, final ConstraintValidatorContext context) {
        if (value == null || value.getRichtung() == null) {
            return false;
        }

        final RichtungsArt art = value.getRichtung().getArt();
        if (art == RichtungsArt.STUDIUM) {
            final boolean hasSem = value.getStudiensemester() != null && !value.getStudiensemester().isEmpty();
            final boolean hasProg = value.getProgrammierkenntnisse() != null && !value.getProgrammierkenntnisse().isBlank();
            final boolean ausbJahrEmpty = value.getAusbildungsjahr() == null || value.getAusbildungsjahr().isEmpty();
            final boolean minderNull = value.getMinderjaehrigMoeglich() == null || !value.getMinderjaehrigMoeglich();
            // projektarbeit is primitive boolean, treat false as unset for STUDIUM

            return hasSem && hasProg && ausbJahrEmpty && minderNull;
        } else if (art == RichtungsArt.AUSBILDUNG) {
            final boolean hasAusbJahr = value.getAusbildungsjahr() != null && !value.getAusbildungsjahr().isEmpty();
            final boolean minderSet = value.getMinderjaehrigMoeglich() != null; // required
            final boolean studSemEmpty = value.getStudiensemester() == null || value.getStudiensemester().isEmpty();
            // programmierkenntnisse optional -> no check

            return hasAusbJahr && minderSet && studSemEmpty;
        }
        return false;
    }
}
