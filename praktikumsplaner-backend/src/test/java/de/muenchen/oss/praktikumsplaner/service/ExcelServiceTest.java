package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExcelServiceTest {
    private final ExcelService service;
    private final String base64EncodedExcel1NWK;
    private final String base64EncodedExcel2NWKInvalidData;

    public ExcelServiceTest() throws IOException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        service = new ExcelService(validator);
        base64EncodedExcel1NWK = IOUtils.toString(Objects.requireNonNull(this.getClass()
                .getResourceAsStream("base64EncodedExcel1NWK.txt")),
                StandardCharsets.UTF_8);
        base64EncodedExcel2NWKInvalidData = IOUtils.toString(Objects.requireNonNull(this.getClass()
                .getResourceAsStream("base64EncodedExcel2NWKInvalidData.txt")),
                StandardCharsets.UTF_8);
    }

    @Test
    public void excelToNwkDTOListTestValidData() throws IOException {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final String studiengang = "BSC";
        final String jahrgang = "21/24";
        final String vorlesungstage = "Mo + Di";

        CreateNwkDTO createNwkDTO = CreateNwkDTO.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        List<CreateNwkDTO> createNwkDTOS = new ArrayList<>();
        createNwkDTOS.add(createNwkDTO);

        List<CreateNwkDTO> resultList = service.excelToNwkDTOList(base64EncodedExcel1NWK);

        assertEquals(createNwkDTOS.get(0).nachname(), resultList.get(0).nachname());
        assertEquals(createNwkDTOS.get(0).vorname(), resultList.get(0).vorname());
        assertEquals(createNwkDTOS.get(0).studiengang(), resultList.get(0).studiengang());
        assertEquals(createNwkDTOS.get(0).jahrgang(), resultList.get(0).jahrgang());
    }

    @Test
    public void excelToNwkDTOListTestInvalidData() {
        assertThrows(ConstraintViolationException.class, () -> service.excelToNwkDTOList(base64EncodedExcel2NWKInvalidData));
    }
}
