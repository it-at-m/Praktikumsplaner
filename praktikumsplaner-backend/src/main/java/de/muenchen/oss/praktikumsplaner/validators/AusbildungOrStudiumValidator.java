package de.muenchen.oss.praktikumsplaner.validators;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungOrStudium;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class AusbildungOrStudiumValidator implements ConstraintValidator<AusbildungOrStudium, Object> {
    private String projektarbeitFieldName;
    private String ausbildungsjahrFieldName;
    private String ausbildungsrichtungFieldName;
    private String studienartFieldName;

    private String studiensemesterFieldName;
    private String programmierkenntnisseFieldName;

    @Override
    public void initialize(final AusbildungOrStudium constraintAnnotation) {
        projektarbeitFieldName = constraintAnnotation.projektarbeit();
        ausbildungsjahrFieldName = constraintAnnotation.ausbildungsjahr();
        ausbildungsrichtungFieldName = constraintAnnotation.ausbildungsrichtung();
        studienartFieldName = constraintAnnotation.studienart();
        studiensemesterFieldName = constraintAnnotation.studiensemester();
        programmierkenntnisseFieldName = constraintAnnotation.programmierkenntnisse();
    }

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (o == null) {
                return true;
            }
            final Method projektarbeitGetter = o.getClass().getMethod(projektarbeitFieldName);
            final String projektarbeit = (String) projektarbeitGetter.invoke(o);

            final Method ausbildungsjahrGetter = o.getClass().getMethod(ausbildungsjahrFieldName);
            final String ausbildungsjahr = (String) ausbildungsjahrGetter.invoke(o);

            final Method ausbildungsrichtungGetter = o.getClass().getMethod(ausbildungsrichtungFieldName);
            final String ausbildungsrichtung = (String) ausbildungsrichtungGetter.invoke(o);

            final Method studienartGetter = o.getClass().getMethod(studienartFieldName);
            final String studienart = (String) studienartGetter.invoke(o);

            final Method studiensemesterGetter = o.getClass().getMethod(studiensemesterFieldName);
            final String studiensemester = (String) studiensemesterGetter.invoke(o);

            final Method programmierkenntnisseGetter = o.getClass().getMethod(programmierkenntnisseFieldName);
            final String programmierkenntnisse = (String) programmierkenntnisseGetter.invoke(o);


            if (ausbildungsrichtung != null && !ausbildungsrichtung.isEmpty() && (studienart == null || studienart.isEmpty())){
            }
        } catch (Exception e) {
            return false;
        }
    }
}
