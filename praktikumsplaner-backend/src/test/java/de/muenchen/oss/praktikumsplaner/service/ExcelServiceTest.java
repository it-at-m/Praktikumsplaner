package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExcelServiceTest {
    private final ExcelService service;
    private final String base64EncodedExcelMultipleNWK;
    private final String base64EncodedExcelNWKInvalidData;

    public ExcelServiceTest() throws IOException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        service = new ExcelService(validator);
        base64EncodedExcelMultipleNWK = IOUtils.toString(Objects.requireNonNull(this.getClass()
                .getResourceAsStream("base64EncodedExcelMultipleNWK.txt")),
                StandardCharsets.UTF_8);
        base64EncodedExcelNWKInvalidData = IOUtils.toString(Objects.requireNonNull(this.getClass()
                .getResourceAsStream("base64EncodedExcelNWKInvalidData.txt")),
                StandardCharsets.UTF_8);
    }

    @Test
    public void excelToNwkDTOListTestValidData() throws IOException {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "21/24";
        final String vorlesungstage = "Mo + Di";

        CreateNwkDTO createNwkDTO = CreateNwkDTO.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        List<CreateNwkDTO> createNwkDTOS = new ArrayList<>();
        createNwkDTOS.add(createNwkDTO);

        // Because only the first NWK gets checked the others are Placeholders for the correct size
        createNwkDTOS.add(CreateNwkDTO.builder().build());
        createNwkDTOS.add(CreateNwkDTO.builder().build());
        createNwkDTOS.add(CreateNwkDTO.builder().build());

        List<CreateNwkDTO> resultList = service.excelToNwkDTOList(base64EncodedExcelMultipleNWK);

        assertEquals(createNwkDTOS.size(), resultList.size());
        assertEquals(createNwkDTOS.get(0), resultList.get(0));
    }

    @Test
    public void excelToNwkDTOListTestInvalidData() {

        Set<ConstraintViolation<?>> violations = assertThrows(ConstraintViolationException.class, () -> service.excelToNwkDTOList(base64EncodedExcelNWKInvalidData)).getConstraintViolations();

        violations.forEach(System.out::println);

        assertEquals(1, violations.stream().filter(e -> e.getPropertyPath().toString().equals("nachname")).count());
        assertEquals(3, violations.stream().filter(e -> e.getPropertyPath().toString().equals("vorname")).count());
        assertEquals(2, violations.stream().filter(e -> e.getPropertyPath().toString().equals("studiengang")).count());
        assertEquals(3, violations.stream().filter(e -> e.getPropertyPath().toString().equals("jahrgang")).count());
        assertEquals(6, violations.stream().filter(e -> e.getPropertyPath().toString().equals("vorlesungstage")).count());
    }
}
