package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExcelServiceTest {

    private final ExcelService service = new ExcelService();

    private final String base64EncodedExcel1NWK;

    private final String base64EncodedExcel2NWKInvalidData;

    public ExcelServiceTest() throws IOException {
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

        NwkDTO max = NwkDTO.builder().id(null).vorname(vorname).nachname(nachname).studiengang(studiengang).jahrgang(jahrgang).vorlesungstage(vorlesungstage)
                .build();

        List<NwkDTO> maxList = new ArrayList<>();
        maxList.add(max);

        List<NwkDTO> resultList = service.excelToNwkDTOList(base64EncodedExcel1NWK);

        assertEquals(maxList.get(0).nachname(), resultList.get(0).nachname());
        assertEquals(maxList.get(0).vorname(), resultList.get(0).vorname());
        assertEquals(maxList.get(0).studiengang(), resultList.get(0).studiengang());
        assertEquals(maxList.get(0).jahrgang(), resultList.get(0).jahrgang());
    }

    @Test
    public void excelToNwkDTOListTestInvalidData() {
        assertThrows(ConstraintViolationException.class, () -> service.excelToNwkDTOList(base64EncodedExcel2NWKInvalidData));
    }
}
