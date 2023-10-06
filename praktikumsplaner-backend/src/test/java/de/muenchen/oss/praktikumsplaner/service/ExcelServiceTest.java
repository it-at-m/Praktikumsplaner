package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDTO;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.io.InvalidObjectException;
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

        NwkDTO max = new NwkDTO(null, vorname, nachname, studiengang, jahrgang);
        List<NwkDTO> maxList = new ArrayList<>();
        maxList.add(max);

        List<NwkDTO> resultList = service.excelToNwkDTOList(base64EncodedExcel1NWK);

        assertEquals(maxList.get(0).getNachname(), resultList.get(0).getNachname());
        assertEquals(maxList.get(0).getVorname(), resultList.get(0).getVorname());
        assertEquals(maxList.get(0).getStudiengang(), resultList.get(0).getStudiengang());
        assertEquals(maxList.get(0).getJahrgang(), resultList.get(0).getJahrgang());
    }

    @Test
    public void excelToNwkDTOListTestInvalidData() {
        assertThrows(InvalidObjectException.class, () -> service.excelToNwkDTOList(base64EncodedExcel2NWKInvalidData));
    }
}
