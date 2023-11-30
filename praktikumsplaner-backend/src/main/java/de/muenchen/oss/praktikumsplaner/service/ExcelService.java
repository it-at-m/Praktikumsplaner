package de.muenchen.oss.praktikumsplaner.service;

import static org.apache.logging.log4j.util.Strings.isBlank;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.exception.ExcelImportException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcelService {
    private final Validator validator;
    private final DataFormatter dataFormatter = new DataFormatter();
    private static final int FIRST_SHEET = 0;
    private static final int FIRST_ROW = 0;
    private static final int NACHNAME_COLUM = 0;
    private static final int VORNAME_COLUM = 1;
    private static final int STUDIENGANG_COLUM = 2;
    private static final int JAHRGANG_COLUM = 3;
    private static final int VORLESUNGSTAGE_COLUM = 4;
    private static final String SPLIT_VORLESUNGSTAGE_REGEX = "[+]";

    public List<CreateNwkDto> excelToNwkDtoList(final String base64String) throws IOException {
        try (InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64String));
                final XSSFWorkbook workbook = new XSSFWorkbook(stream)) {
            final XSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);
            return getAllNwkFromSheet(sheet);
        }
    }

    private List<CreateNwkDto> getAllNwkFromSheet(final XSSFSheet sheet) {
        List<CreateNwkDto> createNwkDtos = new ArrayList<>();
        List<ExcelImportException.ExcelImportExceptionInfo> importExceptionInfoList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == FIRST_ROW) continue;
            CreateNwkDto createNwkDto = null;
            try {
                createNwkDto = getNwkDtoFromRow(row);
            } catch (ExcelImportException ex) {
                importExceptionInfoList.addAll(ex.getExceptionInfos());
            }
            if (createNwkDto == null || isCreateNwkDtoEmpty(createNwkDto)) {
                continue;
            }
            validator.validate(createNwkDto).forEach(violation -> importExceptionInfoList.add(
                    new ExcelImportException.ExcelImportExceptionInfo(row.getRowNum(), violation.getPropertyPath().toString(), violation.getMessage())));
            createNwkDtos.add(createNwkDto);
        }
        if (!importExceptionInfoList.isEmpty())
            throw new ExcelImportException(importExceptionInfoList);
        return createNwkDtos;
    }

    private boolean isCreateNwkDtoEmpty(final CreateNwkDto createNwkDto) {
        return StringUtils.isEmpty(createNwkDto.vorname())
                && StringUtils.isEmpty(createNwkDto.nachname())
                && createNwkDto.studiengang() == null
                && StringUtils.isEmpty(createNwkDto.jahrgang());
    }

    private CreateNwkDto getNwkDtoFromRow(final Row row) {
        CreateNwkDto.CreateNwkDtoBuilder createNwkDtoBuilder = CreateNwkDto.builder();
        for (Cell cell : row) {
            final String cellValue = dataFormatter.formatCellValue(cell);
            switch (cell.getColumnIndex()) {
            case NACHNAME_COLUM -> createNwkDtoBuilder.nachname(cellValue);
            case VORNAME_COLUM -> createNwkDtoBuilder.vorname(cellValue);
            case STUDIENGANG_COLUM -> {
                try {
                    createNwkDtoBuilder
                            .studiengang(isBlank(cellValue) ? null : Studiengang.valueOf(cellValue));
                } catch (IllegalArgumentException ex) {
                    throw new ExcelImportException(List.of(new ExcelImportException.ExcelImportExceptionInfo(row.getRowNum(), "studiengang", ex.getMessage())));
                }
            }
            case JAHRGANG_COLUM -> createNwkDtoBuilder.jahrgang(cellValue);
            case VORLESUNGSTAGE_COLUM -> {
                try {
                    createNwkDtoBuilder.vorlesungstage(extractVorlesungstage(cellValue));
                } catch (IllegalArgumentException ex) {
                    throw new ExcelImportException(
                            List.of(new ExcelImportException.ExcelImportExceptionInfo(row.getRowNum(), "vorlesungstage", ex.getMessage())));
                }
            }
            default -> {
            }
            }
        }
        return createNwkDtoBuilder.build();
    }

    private Set<DayOfWeek> extractVorlesungstage(final String vorlesungstageString) {
        return Arrays.stream(vorlesungstageString.split(SPLIT_VORLESUNGSTAGE_REGEX))
                .map(String::trim)
                .filter(vorlesungstagAsString -> !vorlesungstagAsString.isEmpty())
                .map(this::mapToDayOfWeek)
                .collect(Collectors.toSet());
    }

    private DayOfWeek mapToDayOfWeek(final String vorlesungstagString) {
        switch (vorlesungstagString) {
        case "Mo" -> {
            return DayOfWeek.MONDAY;
        }
        case "Di" -> {
            return DayOfWeek.TUESDAY;
        }
        case "Mi" -> {
            return DayOfWeek.WEDNESDAY;
        }
        case "Do" -> {
            return DayOfWeek.THURSDAY;
        }
        case "Fr" -> {
            return DayOfWeek.FRIDAY;
        }
        case "Sa" -> {
            return DayOfWeek.SATURDAY;
        }
        }
        throw new IllegalArgumentException(vorlesungstagString);
    }
}
