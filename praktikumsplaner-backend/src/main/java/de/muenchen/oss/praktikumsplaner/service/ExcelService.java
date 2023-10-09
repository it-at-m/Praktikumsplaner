package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
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

    public List<NwkDTO> excelToNwkDTOList(String fileString) throws IOException {
        InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(fileString));
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        return getAllNwkFromSheet(sheet);
    }

    private List<NwkDTO> getAllNwkFromSheet(XSSFSheet sheet) throws InvalidObjectException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<NwkDTO> nwkDTOList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        for (Row row : sheet) {
            NwkDTO nwkDTO = new NwkDTO();
            if (row.getRowNum() == 0) continue;
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                switch (cell.getColumnIndex()) {
                case 0 -> nwkDTO.setNachname(cellValue);
                case 1 -> nwkDTO.setVorname(cellValue);
                case 2 -> nwkDTO.setStudiengang(cellValue);
                case 3 -> nwkDTO.setJahrgang(cellValue);
                case 4 -> nwkDTO.setVorlesungstage(cellValue);
                default -> {
                }
                }
            }
            if (isEmpty(nwkDTO)) {
                continue;
            }
            Set<ConstraintViolation<NwkDTO>> violations = validator.validate(nwkDTO);
            if (violations.isEmpty()) {
                nwkDTOList.add(nwkDTO);
            } else {
                throw new InvalidObjectException("Ein NWK Datensatz ist Fehlerhaft. Hochladen wurde abgebrochen.");
            }
        }
        return nwkDTOList;
    }

    public boolean isEmpty(NwkDTO nwkDTO) {
        return nwkDTO.getVorname().isEmpty() && nwkDTO.getNachname().isEmpty()
                && nwkDTO.getStudiengang().isEmpty() && nwkDTO.getJahrgang().isEmpty();
    }
}
