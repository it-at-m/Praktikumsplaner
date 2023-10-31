package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.StartDateBeforeEndDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class StartDateBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, Object> {
    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(final StartDateBeforeEndDate constraintAnnotation) {
        startDateFieldName = constraintAnnotation.startDate();
        endDateFieldName = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (o == null) {
                return true;
            }

            final Method startDateGetter = o.getClass().getMethod(startDateFieldName);
            final LocalDate startDate = (LocalDate) startDateGetter.invoke(o);

            final Method endDateGetter = o.getClass().getMethod(endDateFieldName);
            final LocalDate endDate = (LocalDate) endDateGetter.invoke(o);

            return startDate.isBefore(endDate);
        } catch (Exception e) {
            return false;
        }
    }
}
