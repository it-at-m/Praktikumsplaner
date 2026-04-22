package de.muenchen.oss.praktikumsplaner.service;

import static de.muenchen.oss.praktikumsplaner.TestConstants.SPRING_TEST_PROFILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.configuration.PraktikumsplanerProperties;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = { PraktikumsplanerProperties.class, ExcelExportService.class })
@EnableConfigurationProperties(PraktikumsplanerProperties.class)
@ActiveProfiles(SPRING_TEST_PROFILE)
@ExtendWith(MockitoExtension.class)
public class ExcelExportServiceTest {

    @Autowired
    private ExcelExportService service;
    @MockitoBean
    private PraktikumsstellenService praktikumsstellenService;

    private final ServiceTestHelper helper = new ServiceTestHelper();

    @Test
    public void testFillTemplatePraktikumsstellen() throws IOException {
        when(praktikumsstellenService.getAllAssignedAusbildungspraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(getTestListOfAusbildungsPraktikumsstelleDto());
        when(praktikumsstellenService.getAllAssignedStudiumspraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(getTestListOfStudiumsPraktikumsstelleDto());
        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(
                Base64.getDecoder().decode(service.getBase64EncodedExcelFile())))) {

            XSSFSheet ausbildungsSheet = workbook.getSheetAt(ExcelExportService.AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX);
            XSSFSheet studiumsSheet = workbook.getSheetAt(ExcelExportService.STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX);

            List<AusbildungsPraktikumsstelleDto> ausbildungsPraktikumsstellen = getTestListOfAusbildungsPraktikumsstelleDto();

            assertNotNull(workbook);
            assertEquals(4, workbook.getNumberOfSheets());
            assertEquals("ITM", ausbildungsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals("oertlAL", ausbildungsSheet.getRow(3).getCell(1).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().dienststelle(), ausbildungsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals("dienststellen Adresse", ausbildungsSheet.getRow(3).getCell(4).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().oertlicheAusbilder(), ausbildungsSheet.getRow(3).getCell(5).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().taetigkeiten(), ausbildungsSheet.getRow(3).getCell(7).getStringCellValue());
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Programmierkenntnisse von Vorteil")));
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Namentliche Anforderung:")));
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), containsString("Wuensche 1"));
            assertEquals("Nein", ausbildungsSheet.getRow(3).getCell(9).getStringCellValue());
            assertEquals("vorrangig 2., 3. Lehrjahr", ausbildungsSheet.getRow(3).getCell(13).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().dringlichkeit().name(), ausbildungsSheet.getRow(3).getCell(12).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().ausbildungsrichtung().name(), ausbildungsSheet.getRow(3).getCell(14).getStringCellValue());
            assertEquals("Praktikumsplatz", ausbildungsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().assignedNwk().nachname(), ausbildungsSheet.getRow(3).getCell(15).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().assignedNwk().vorname(), ausbildungsSheet.getRow(3).getCell(16).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.getFirst().assignedNwk().jahrgang(), ausbildungsSheet.getRow(3).getCell(17).getStringCellValue());
            assertEquals("Ja", ausbildungsSheet.getRow(3).getCell(18).getStringCellValue());
            assertEquals("Ja", ausbildungsSheet.getRow(4).getCell(9).getStringCellValue());

            List<StudiumsPraktikumsstelleDto> studiumsPraktikumsstellen = getTestListOfStudiumsPraktikumsstelleDto();

            assertEquals("ITM", studiumsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals("oertlAL", studiumsSheet.getRow(3).getCell(1).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().dienststelle(), studiumsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals("dienststellen Adresse", studiumsSheet.getRow(3).getCell(4).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().oertlicheAusbilder(), studiumsSheet.getRow(3).getCell(5).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().taetigkeiten(), studiumsSheet.getRow(3).getCell(7).getStringCellValue());
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Namentliche Anforderung:")));
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), containsString("Programmierkenntnisse von Vorteil"));
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), containsString("Wuensche 3"));
            assertEquals("Ja", studiumsSheet.getRow(3).getCell(10).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().dringlichkeit().name(), studiumsSheet.getRow(3).getCell(12).getStringCellValue());
            assertEquals("vorrangig 4., 5. Semester", studiumsSheet.getRow(3).getCell(13).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().studiengang().name(), studiumsSheet.getRow(3).getCell(14).getStringCellValue());
            assertEquals("Praktikumsplatz", studiumsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().assignedNwk().nachname(), studiumsSheet.getRow(3).getCell(15).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().assignedNwk().vorname(), studiumsSheet.getRow(3).getCell(16).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.getFirst().assignedNwk().jahrgang(), studiumsSheet.getRow(3).getCell(17).getStringCellValue());
        }
    }

    @Test
    public void testSortPraktikumsstellen() throws IOException {
        List<AusbildungsPraktikumsstelleDto> ausbildungsPraktikumsstellenWithStudent = List.of(
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("Dienststelle 1", "Ausbilder 1", "a@b.c", "Taetigkeiten 1",
                        null, Dringlichkeit.DRINGEND, Set.of(Ausbildungsjahr.JAHR1), Ausbildungsrichtung.FISI, false, false, null,
                        helper.createNwkEntity("Vorname 1", "Nachname 1", Studiengang.BSC, null, "22/23", null, true))));

        List<StudiumsPraktikumsstelleDto> studiumsPraktikumsstellenWithAuszubildende = List.of(
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 3", "Ausbilder 3", "a@b.c", "Taetigkeiten 3",
                        null, Dringlichkeit.DRINGEND, Set.of(Studiensemester.SEMESTER1), Studiengang.BSC, "false", null,
                        helper.createNwkEntity("Vorname 3", "Nachname 3", null, Ausbildungsrichtung.FISI, "22/23", null, true))));

        when(praktikumsstellenService.getAllAssignedAusbildungspraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(ausbildungsPraktikumsstellenWithStudent);
        when(praktikumsstellenService.getAllAssignedStudiumspraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(studiumsPraktikumsstellenWithAuszubildende);

        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(
                Base64.getDecoder().decode(service.getBase64EncodedExcelFile())))) {
            XSSFSheet ausbildungsSheet = workbook.getSheetAt(ExcelExportService.AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX);
            XSSFSheet studiumsSheet = workbook.getSheetAt(ExcelExportService.STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX);

            // Test if Studiums-NWKs on Ausbildungs-Stellen are sorted to Studiums-Sheet and vice versa
            assertEquals(ausbildungsPraktikumsstellenWithStudent.getFirst().dienststelle(), studiumsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals(studiumsPraktikumsstellenWithAuszubildende.getFirst().dienststelle(), ausbildungsSheet.getRow(3).getCell(2).getStringCellValue());
        }
    }

    @Test
    public void testGetBase64EncodedExcelFile() throws IOException {
        assertNotNull(service.getBase64EncodedExcelFile());
    }

    private List<AusbildungsPraktikumsstelleDto> getTestListOfAusbildungsPraktikumsstelleDto() {
        return List.of(
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("ITM-DS1", "Ausbilder 1", "a@b.c", "Taetigkeiten 1",
                        "Wuensche 1", Dringlichkeit.DRINGEND, Set.of(Ausbildungsjahr.JAHR2, Ausbildungsjahr.JAHR3), Ausbildungsrichtung.FISI, false, true, null,
                        helper.createNwkEntity("Vorname 1", "Nachname 1", null, Ausbildungsrichtung.FISI, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("ITM-DS2", "Ausbilder 2", "a@b.c", "Taetigkeiten 2",
                        null, Dringlichkeit.DRINGEND, Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, true, false, null,
                        helper.createNwkEntity("Vorname 2", "Nachname 2", null, Ausbildungsrichtung.FISI, "22/23", null, true))));
    }

    private List<StudiumsPraktikumsstelleDto> getTestListOfStudiumsPraktikumsstelleDto() {
        return List.of(
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("ITM-DS3", "Ausbilder 3", "a@b.c", "Taetigkeiten 3",
                        "Wuensche 3", Dringlichkeit.DRINGEND, Set.of(Studiensemester.SEMESTER5, Studiensemester.SEMESTER4), Studiengang.BWI, "true", null,
                        helper.createNwkEntity("Vorname 3", "Nachname 3", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("ITM-DS4", "Ausbilder 4", "a@b.c", "Taetigkeiten 4",
                        null, Dringlichkeit.ZWINGEND, Set.of(Studiensemester.SEMESTER2), Studiengang.VI, "false", null,
                        helper.createNwkEntity("Vorname 4", "Nachname 4", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("ITM-DS5", "Ausbilder 5", "a@b.c", "Taetigkeiten 5",
                        null, Dringlichkeit.NACHRANGIG, Set.of(Studiensemester.SEMESTER3), Studiengang.BSC, "false", null,
                        helper.createNwkEntity("Vorname 5", "Nachname 5", null, Ausbildungsrichtung.FISI, "22/23", null, true))));
    }
}
