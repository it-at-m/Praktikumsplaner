package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateNwkDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.exception.ExcelImportException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExcelServiceTest {
    private final ExcelService service;
    private final String base64EncodedExcelMultipleNwk;
    private final String base64EncodedExcelNwkInvalidData;

    public ExcelServiceTest() throws IOException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        service = new ExcelService(validator);
        base64EncodedExcelMultipleNwk = Base64.getEncoder().encodeToString(Objects
                .requireNonNull(this.getClass().getResourceAsStream("ExcelMultipleNwk.xlsx")).readAllBytes());
        base64EncodedExcelNwkInvalidData = Base64.getEncoder().encodeToString(Objects
                .requireNonNull(this.getClass().getResourceAsStream("ExcelNwkInvalidData.xlsx")).readAllBytes());
    }

    @Test
    public void testExcelToNwkDtoListValidData() throws IOException {
        final String nachname = "Mustermann";
        final String vorname = "Max";
        final Studiengang studiengang = Studiengang.BSC;
        final String jahrgang = "21/24";
        final Set<DayOfWeek> vorlesungstage = new HashSet<>();
        vorlesungstage.add(DayOfWeek.MONDAY);
        vorlesungstage.add(DayOfWeek.TUESDAY);

        CreateNwkDto createNwkDto = CreateNwkDto.builder().vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang)
                .vorlesungstage(vorlesungstage).build();

        List<CreateNwkDto> createNwkDtos = new ArrayList<>();
        createNwkDtos.add(createNwkDto);

        // Because only the first Nwk gets checked the others are Placeholders for the correct size
        createNwkDtos.add(CreateNwkDto.builder().build());
        createNwkDtos.add(CreateNwkDto.builder().build());
        createNwkDtos.add(CreateNwkDto.builder().build());

        List<CreateNwkDto> resultList = service.excelToNwkDtoList(base64EncodedExcelMultipleNwk);

        assertEquals(createNwkDtos.size(), resultList.size());
        assertEquals(createNwkDtos.get(0), resultList.get(0));
    }

    @Test
    public void testExcelToNwkDtoListInvalidData() {

        var violations = assertThrows(ExcelImportException.class, () -> service.excelToNwkDtoList(base64EncodedExcelNwkInvalidData)).getExceptionInfos();

        assertEquals(1, violations.stream().filter(e -> e.getColumName().equals("nachname")).count());
        assertEquals(3, violations.stream().filter(e -> e.getColumName().equals("vorname")).count());
        assertEquals(2, violations.stream().filter(e -> e.getColumName().equals("studiengang")).count());
        assertEquals(3, violations.stream().filter(e -> e.getColumName().equals("jahrgang")).count());
        assertEquals(2, violations.stream().filter(e -> e.getColumName().equals("vorlesungstage")).count());
    }
}
