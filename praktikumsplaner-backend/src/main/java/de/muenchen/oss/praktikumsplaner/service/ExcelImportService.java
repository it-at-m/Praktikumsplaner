package de.muenchen.oss.praktikumsplaner.service;

import static org.apache.logging.log4j.util.Strings.isBlank;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ExcelImportService {
    private final Validator validator;
    private final DataFormatter dataFormatter = new DataFormatter();
    private static final int FIRST_SHEET = 0;
    private static final int FIRST_ROW = 0;
    private static final int NACHNAME_COLUM = 0;
    private static final int VORNAME_COLUM = 1;
    private static final int BILDUNGSRICHTUNG_COLUM = 2;
    private static final int JAHRGANG_COLUM = 3;
    private static final int VORLESUNGSTAGE_COLUM = 4;
    private static final String SPLIT_VORLESUNGSTAGE_REGEX = "[+]";

    public List<CreateNwkDto> excelToNwkDtoList(final String base64String) throws IOException {
        try (InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64String));
                XSSFWorkbook workbook = new XSSFWorkbook(stream)) {
            final XSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);
            return getAllNwkFromSheet(sheet);
        }
    }

    private List<CreateNwkDto> getAllNwkFromSheet(final XSSFSheet sheet) {
        final List<CreateNwkDto> createNwkDtos = new ArrayList<>();
        final List<ExcelImportException.ExcelImportExceptionInfo> importExceptionInfoList = new ArrayList<>();
        for (final Row row : sheet) {
            if (row.getRowNum() == FIRST_ROW) {
                continue;
            }
            CreateNwkDto createNwkDto = null;
            try {
                createNwkDto = getNwkDtoFromRow(row);
            } catch (ExcelImportException ex) {
                importExceptionInfoList.addAll(ex.getExceptionInfos());
            }
            if (createNwkDto == null || isCreateNwkDtoEmpty(createNwkDto)) {
                log.trace("NWK ist leer.");
                continue;
            }
            validator.validate(createNwkDto).forEach(violation -> {
                importExceptionInfoList.add(
                        new ExcelImportException.ExcelImportExceptionInfo(
                                row.getRowNum(),
                                violation.getPropertyPath().toString(),
                                violation.getMessage()));
            });

            createNwkDtos.add(createNwkDto);
            log.trace("NWK wurde geaddet.");
        }
        log.debug(importExceptionInfoList.toString());
        if (!importExceptionInfoList.isEmpty()) {
            throw new ExcelImportException(importExceptionInfoList);
        }
        log.debug("NWKS: {}", createNwkDtos);
        return createNwkDtos;
    }

    private boolean isCreateNwkDtoEmpty(final CreateNwkDto createNwkDto) {
        return StringUtils.isEmpty(createNwkDto.vorname())
                && StringUtils.isEmpty(createNwkDto.nachname())
                && createNwkDto.richtung() == null
                && StringUtils.isEmpty(createNwkDto.jahrgang());
    }

    @SuppressWarnings("PMD.PreserveStackTrace")
    private CreateNwkDto getNwkDtoFromRow(final Row row) {
        final CreateNwkDto.CreateNwkDtoBuilder createNwkDtoBuilder = CreateNwkDto.builder();
        for (final Cell cell : row) {
            final String cellValue = dataFormatter.formatCellValue(cell);
            switch (cell.getColumnIndex()) {
            case NACHNAME_COLUM -> createNwkDtoBuilder.nachname(cellValue);
            case VORNAME_COLUM -> createNwkDtoBuilder.vorname(cellValue);
            case BILDUNGSRICHTUNG_COLUM -> handleBildungsrichtungColumn(row, cellValue, createNwkDtoBuilder);
            case JAHRGANG_COLUM -> createNwkDtoBuilder.jahrgang(cellValue);
            case VORLESUNGSTAGE_COLUM -> {
                try {
                    createNwkDtoBuilder.vorlesungstage(extractVorlesungstage(cellValue));
                } catch (final IllegalArgumentException ex) {
                    throw new ExcelImportException(
                            List.of(new ExcelImportException.ExcelImportExceptionInfo(row.getRowNum(), "vorlesungstage", ex.getMessage())));
                }
            }
            default -> {
            }
            }
        }
        log.trace("NWK wird gebuildet.");
        return createNwkDtoBuilder.build();
    }

    @SuppressWarnings("PMD.PreserveStackTrace")
    private static void handleBildungsrichtungColumn(final Row row, final String cellValue, final CreateNwkDto.CreateNwkDtoBuilder createNwkDtoBuilder) {
        try {
            log.trace("Column Value: {}", cellValue);
            if (isBlank(cellValue)) {
                log.trace("leere Zelle.");
                createNwkDtoBuilder.richtung(null);
            } else {
                createNwkDtoBuilder.richtung(Bildungsrichtung.valueOf(cellValue));
                log.trace("Studiengang: {}", cellValue);
            }
        } catch (final IllegalArgumentException ex) {
            throw new ExcelImportException(
                    List.of(new ExcelImportException.ExcelImportExceptionInfo(row.getRowNum(), "richtung", ex.getMessage())));
        }
    }

    private Set<DayOfWeek> extractVorlesungstage(final String vorlesungstageString) {
        return Arrays.stream(vorlesungstageString.split(SPLIT_VORLESUNGSTAGE_REGEX))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(vorlesungstagAsString -> !vorlesungstagAsString.isEmpty())
                .map(this::mapToDayOfWeek)
                .collect(Collectors.toSet());
    }

    private DayOfWeek mapToDayOfWeek(final String vorlesungstagString) {
        return switch (vorlesungstagString) {
        case "MO" -> DayOfWeek.MONDAY;
        case "DI" -> DayOfWeek.TUESDAY;
        case "MI" -> DayOfWeek.WEDNESDAY;
        case "DO" -> DayOfWeek.THURSDAY;
        case "FR" -> DayOfWeek.FRIDAY;
        default -> throw new IllegalArgumentException(vorlesungstagString);
        };
    }
}
