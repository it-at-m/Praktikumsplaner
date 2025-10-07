package de.muenchen.oss.praktikumsplaner.domain.converter;

import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Set;

@Converter
public class StudiensemesterConverter implements AttributeConverter<Set<Studiensemester>, String> {
    private static final String COMMA = ",";

    @Override
    public String convertToDatabaseColumn(final Set<Studiensemester> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Studiensemester::name)
                .collect(java.util.stream.Collectors.joining(COMMA));
    }

    @Override
    public Set<Studiensemester> convertToEntityAttribute(final String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return java.util.Collections.emptySet();
        }
        return java.util.Arrays.stream(dbData.split(COMMA))
                .map(String::trim)
                .map(Studiensemester::valueOf)
                .collect(java.util.stream.Collectors.toSet());
    }
}
