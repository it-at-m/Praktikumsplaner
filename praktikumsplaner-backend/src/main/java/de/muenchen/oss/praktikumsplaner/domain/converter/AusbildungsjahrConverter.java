package de.muenchen.oss.praktikumsplaner.domain.converter;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Set;

@Converter
public class AusbildungsjahrConverter implements AttributeConverter<Set<Ausbildungsjahr>, String> {
    private static final String comma = ",";

    @Override
    public String convertToDatabaseColumn(final Set<Ausbildungsjahr> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Ausbildungsjahr::name)
                .collect(java.util.stream.Collectors.joining(comma));
    }

    @Override
    public Set<Ausbildungsjahr> convertToEntityAttribute(final String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return java.util.Collections.emptySet();
        }
        return java.util.Arrays.stream(dbData.split(comma))
                .map(String::trim)
                .map(Ausbildungsjahr::valueOf)
                .collect(java.util.stream.Collectors.toSet());
    }
}
