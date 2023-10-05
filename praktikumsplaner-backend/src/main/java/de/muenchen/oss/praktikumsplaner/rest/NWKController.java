package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import de.muenchen.oss.praktikumsplaner.service.NWKService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/excel", produces = "application/json; charset=UTF-8")
public class NWKController {
    private final NWKService nwkService;

    @PostMapping("/")
    public void saveNWKExcel(@RequestBody String fileString) throws IOException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        fileString = fileString.replaceAll("\"", "");
        InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(fileString));

        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        NwkDTO nwkDTO = new NwkDTO();

        for (Row row : sheet) {
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                switch (cell.getColumnIndex()) {
                case 0:
                    nwkDTO.setNachname(cellValue);
                case 1:
                    nwkDTO.setVorname(cellValue);
                case 2:
                    nwkDTO.setStudiengang(cellValue);
                case 3:
                    nwkDTO.setJahrgang(cellValue);
                default:
                }
            }
            Set<ConstraintViolation<NwkDTO>> violations = validator.validate(nwkDTO);
            if (violations.isEmpty()) {
                nwkService.saveNWK(nwkDTO);
            }
        }
    }
}
