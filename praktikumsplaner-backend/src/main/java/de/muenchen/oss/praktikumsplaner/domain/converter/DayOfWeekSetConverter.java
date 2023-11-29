package de.muenchen.oss.praktikumsplaner.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class DayOfWeekSetConverter implements AttributeConverter<Set<DayOfWeek>, String> {
    @Override
    public String convertToDatabaseColumn(final Set<DayOfWeek> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(DayOfWeek::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<DayOfWeek> convertToEntityAttribute(final String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(dbData.split(","))
                .map(String::trim)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());
    }
}
