package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {
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
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        List<NwkDTO> nwkDTOList = new ArrayList<>();
        final DataFormatter dataFormatter = new DataFormatter();

        for (Row row : sheet) {
            NwkDTO nwkDTO = new NwkDTO();
            if (row.getRowNum() == 0) continue;
            for (Cell cell : row) {
                final String cellValue = dataFormatter.formatCellValue(cell);
                switch (cell.getColumnIndex()) {
                    case NACHNAME_COLUM -> nwkDTO.setNachname(cellValue);
                    case VORNAME_COLUM -> nwkDTO.setVorname(cellValue);
                    case STUDIENGANG_COLUM -> nwkDTO.setStudiengang(cellValue);
                    case JAHRGANG_COLUM -> nwkDTO.setJahrgang(cellValue);
                    case VORLESUNGSTAGE_COLUM -> nwkDTO.setVorlesungstage(cellValue);
                default -> {
                }
                }
            }
            if (isNwkTDOEmpty(nwkDTO)) {
                continue;
            }
            Set<ConstraintViolation<NwkDTO>> violations = validator.validate(nwkDTO);
            if (violations.isEmpty()) {
                nwkDTOList.add(nwkDTO);
            } else {
                throw new ConstraintViolationException(violations);
            }
        }
        return nwkDTOList;
    }

    protected boolean isNwkTDOEmpty(NwkDTO nwkDTO) {
        return nwkDTO.getVorname().isEmpty() && nwkDTO.getNachname().isEmpty()
                && nwkDTO.getStudiengang().isEmpty() && nwkDTO.getJahrgang().isEmpty();
    }
}
