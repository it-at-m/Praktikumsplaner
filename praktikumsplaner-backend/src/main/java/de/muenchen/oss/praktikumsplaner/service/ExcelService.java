package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcelService {

    @Autowired
    private Validator validator;

    private static final int ZERO = 0;
    private static final int NACHNAME_COLUM = 0;
    private static final int VORNAME_COLUM = 1;
    private static final int STUDIENGANG_COLUM = 2;
    private static final int JAHRGANG_COLUM = 3;
    private static final int VORLESUNGSTAGE_COLUM = 4;

    public List<NwkDTO> excelToNwkDTOList(String base64String) throws IOException {
        final InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64String));
        final XSSFWorkbook workbook = new XSSFWorkbook(stream);
        final XSSFSheet sheet = workbook.getSheetAt(ZERO);

        return getAllNwkFromSheet(sheet);
    }

    private List<NwkDTO> getAllNwkFromSheet(XSSFSheet sheet) {
        List<NwkDTO> nwkDTOList = new ArrayList<>();
        final DataFormatter dataFormatter = new DataFormatter();

        for (Row row : sheet) {
            NwkDTO.NwkDTOBuilder nwkDTO = NwkDTO.builder();
            if (row.getRowNum() == 0) continue;
            for (Cell cell : row) {
                final String cellValue = dataFormatter.formatCellValue(cell);
                switch (cell.getColumnIndex()) {
                case NACHNAME_COLUM -> nwkDTO.nachname(cellValue);
                case VORNAME_COLUM -> nwkDTO.vorname(cellValue);
                case STUDIENGANG_COLUM -> nwkDTO.studiengang(cellValue);
                case JAHRGANG_COLUM -> nwkDTO.jahrgang(cellValue);
                case VORLESUNGSTAGE_COLUM -> nwkDTO.vorlesungstage(cellValue);
                default -> {
                }
                }
            }
            if (isNwkTDOEmpty(nwkDTO.build())) {
                continue;
            }
            Set<ConstraintViolation<NwkDTO>> violations = validator.validate(nwkDTO.build());
            if (violations.isEmpty()) {
                nwkDTOList.add(nwkDTO.build());
            } else {
                throw new ConstraintViolationException(violations);
            }
        }
        return nwkDTOList;
    }

    protected boolean isNwkTDOEmpty(NwkDTO nwkDTO) {
        return nwkDTO.vorname().isEmpty() && nwkDTO.nachname().isEmpty()
                && nwkDTO.studiengang().isEmpty() && nwkDTO.jahrgang().isEmpty();
    }
}
