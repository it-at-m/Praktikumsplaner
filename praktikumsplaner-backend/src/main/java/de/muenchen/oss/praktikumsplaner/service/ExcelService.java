package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    public List<CreateNwkDTO> excelToNwkDTOList(String base64String) throws IOException {
        final InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64String));
        final XSSFWorkbook workbook = new XSSFWorkbook(stream);
        final XSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);

        return getAllNwkFromSheet(sheet);
    }

    private List<CreateNwkDTO> getAllNwkFromSheet(XSSFSheet sheet) {
        List<CreateNwkDTO> createNwkDTOS = new ArrayList<>();
        Set<ConstraintViolation<CreateNwkDTO>> violations = new HashSet<>();
        for (Row row : sheet) {
            if (row.getRowNum() == FIRST_ROW) continue;
            CreateNwkDTO createNwkDTO = getNwkDTOFromRow(row);
            if (isCreateNwkDTOEmpty(createNwkDTO)) {
                continue;
            }
            violations.addAll(validator.validate(createNwkDTO));
            createNwkDTOS.add(createNwkDTO);
        }
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
        return createNwkDTOS;
    }

    protected boolean isCreateNwkDTOEmpty(CreateNwkDTO createNwkDTO) {
        return (createNwkDTO.vorname() == null || createNwkDTO.vorname().isEmpty()) && (createNwkDTO.nachname() == null || createNwkDTO.nachname().isEmpty())
                && createNwkDTO.studiengang() == null && (createNwkDTO.jahrgang() == null || createNwkDTO.jahrgang().isEmpty());
    }

    private CreateNwkDTO getNwkDTOFromRow(Row row) {
        CreateNwkDTO.CreateNwkDTOBuilder createNwkDTOBuilder = CreateNwkDTO.builder();
        for (Cell cell : row) {
            final String cellValue = dataFormatter.formatCellValue(cell);
            switch (cell.getColumnIndex()) {
            case NACHNAME_COLUM -> createNwkDTOBuilder.nachname(cellValue);
            case VORNAME_COLUM -> createNwkDTOBuilder.vorname(cellValue);
            case STUDIENGANG_COLUM -> createNwkDTOBuilder
                    .studiengang(Objects.equals(cellValue, "") ? null : Studiengang.valueOf(cellValue));
            case JAHRGANG_COLUM -> createNwkDTOBuilder.jahrgang(cellValue);
            case VORLESUNGSTAGE_COLUM -> createNwkDTOBuilder.vorlesungstage(cellValue);
            default -> {
            }
            }
        }
        return createNwkDTOBuilder.build();
    }
}
