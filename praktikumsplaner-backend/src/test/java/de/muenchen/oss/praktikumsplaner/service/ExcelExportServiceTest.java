package de.muenchen.oss.praktikumsplaner.service;

import static de.muenchen.oss.praktikumsplaner.TestConstants.SPRING_TEST_PROFILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.configuration.PraktikumsplanerProperties;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Bildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
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
class ExcelExportServiceTest {

    @Autowired
    private ExcelExportService service;

    @MockitoBean
    private PraktikumsstellenService praktikumsstellenService;

    private final ServiceTestHelper helper = new ServiceTestHelper();

    @Test
    void testFillTemplatePraktikumsstellen() throws IOException {
        when(praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(getAssignedPraktikumsstellen());

        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(
                Base64.getDecoder().decode(service.getBase64EncodedExcelFile())))) {
            XSSFSheet ausbildungsSheet = workbook.getSheetAt(ExcelExportService.AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX);
            XSSFSheet studiumsSheet = workbook.getSheetAt(ExcelExportService.STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX);

            PraktikumsstelleDto ausbildungsstelle = getAssignedPraktikumsstellen().getFirst();
            PraktikumsstelleDto studiumsstelle = getAssignedPraktikumsstellen().get(2);

            assertNotNull(workbook);
            assertEquals(4, workbook.getNumberOfSheets());

            assertEquals("ITM", ausbildungsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals("oertlAL", ausbildungsSheet.getRow(3).getCell(1).getStringCellValue());
            assertEquals(ausbildungsstelle.dienststelle(), ausbildungsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals("dienststellen Adresse", ausbildungsSheet.getRow(3).getCell(4).getStringCellValue());
            assertEquals(ausbildungsstelle.oertlicheAusbilder(), ausbildungsSheet.getRow(3).getCell(5).getStringCellValue());
            assertEquals(ausbildungsstelle.taetigkeiten(), ausbildungsSheet.getRow(3).getCell(7).getStringCellValue());
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Programmierkenntnisse von Vorteil")));
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Namentliche Anforderung:")));
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), containsString("Wuensche 1"));
            assertEquals("Nein", ausbildungsSheet.getRow(3).getCell(9).getStringCellValue());
            assertEquals("vorrangig 2., 3. Lehrjahr", ausbildungsSheet.getRow(3).getCell(13).getStringCellValue());
            assertEquals(ausbildungsstelle.dringlichkeit().name(), ausbildungsSheet.getRow(3).getCell(12).getStringCellValue());
            assertEquals(Bildungsrichtung.FISI.name(), ausbildungsSheet.getRow(3).getCell(14).getStringCellValue());
            assertEquals("Praktikumsplatz", ausbildungsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(ausbildungsstelle.assignedNwk().nachname(), ausbildungsSheet.getRow(3).getCell(15).getStringCellValue());
            assertEquals(ausbildungsstelle.assignedNwk().vorname(), ausbildungsSheet.getRow(3).getCell(16).getStringCellValue());
            assertEquals(ausbildungsstelle.assignedNwk().jahrgang(), ausbildungsSheet.getRow(3).getCell(17).getStringCellValue());
            assertEquals("Ja", ausbildungsSheet.getRow(3).getCell(18).getStringCellValue());
            assertEquals("Ja", ausbildungsSheet.getRow(4).getCell(9).getStringCellValue());

            assertEquals("ITM", studiumsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals("oertlAL", studiumsSheet.getRow(3).getCell(1).getStringCellValue());
            assertEquals(studiumsstelle.dienststelle(), studiumsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals("dienststellen Adresse", studiumsSheet.getRow(3).getCell(4).getStringCellValue());
            assertEquals(studiumsstelle.oertlicheAusbilder(), studiumsSheet.getRow(3).getCell(5).getStringCellValue());
            assertEquals(studiumsstelle.taetigkeiten(), studiumsSheet.getRow(3).getCell(7).getStringCellValue());
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Namentliche Anforderung:")));
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), containsString("Programmierkenntnisse von Vorteil"));
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), containsString("Wuensche 3"));
            assertEquals("Ja", studiumsSheet.getRow(3).getCell(10).getStringCellValue());
            assertEquals(studiumsstelle.dringlichkeit().name(), studiumsSheet.getRow(3).getCell(12).getStringCellValue());
            assertEquals("vorrangig 4., 5. Semester", studiumsSheet.getRow(3).getCell(13).getStringCellValue());
            assertEquals(Bildungsrichtung.BSC.name(), studiumsSheet.getRow(3).getCell(14).getStringCellValue());
            assertEquals("Praktikumsplatz", studiumsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(studiumsstelle.assignedNwk().nachname(), studiumsSheet.getRow(3).getCell(15).getStringCellValue());
            assertEquals(studiumsstelle.assignedNwk().vorname(), studiumsSheet.getRow(3).getCell(16).getStringCellValue());
            assertEquals(studiumsstelle.assignedNwk().jahrgang(), studiumsSheet.getRow(3).getCell(17).getStringCellValue());
        }
    }

    @Test
    void testSortPraktikumsstellen() throws IOException {
        List<PraktikumsstelleDto> assignedPraktikumsstellen = List.of(
                helper.createPraktikumsstelleDto(helper.createPraktikumsstelleEntity(
                        "Dienststelle 1",
                        "Ausbilder 1",
                        "a@b.c",
                        "Taetigkeiten 1",
                        null,
                        Dringlichkeit.DRINGEND,
                        Bildungsrichtung.FISI,
                        Set.of(Ausbildungsjahr.JAHR1),
                        null,
                        false,
                        false,
                        false,
                        null,
                        helper.createNwkEntity("Vorname 1", "Nachname 1", Bildungsrichtung.BSC, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createPraktikumsstelleEntity(
                        "Dienststelle 3",
                        "Ausbilder 3",
                        "a@b.c",
                        "Taetigkeiten 3",
                        null,
                        Dringlichkeit.DRINGEND,
                        Bildungsrichtung.BSC,
                        null,
                        Set.of(Studiensemester.SEMESTER1),
                        false,
                        false,
                        false,
                        null,
                        helper.createNwkEntity("Vorname 3", "Nachname 3", Bildungsrichtung.FISI, "22/23", null, true))));

        when(praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(assignedPraktikumsstellen);

        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(
                Base64.getDecoder().decode(service.getBase64EncodedExcelFile())))) {
            XSSFSheet ausbildungsSheet = workbook.getSheetAt(ExcelExportService.AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX);
            XSSFSheet studiumsSheet = workbook.getSheetAt(ExcelExportService.STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX);

            assertEquals(assignedPraktikumsstellen.getFirst().dienststelle(), studiumsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals(assignedPraktikumsstellen.get(1).dienststelle(), ausbildungsSheet.getRow(3).getCell(2).getStringCellValue());
        }
    }

    @Test
    void testGetBase64EncodedExcelFile() throws IOException {
        when(praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum()).thenReturn(List.of());
        assertNotNull(service.getBase64EncodedExcelFile());
    }

    private List<PraktikumsstelleDto> getAssignedPraktikumsstellen() {
        return List.of(
                helper.createPraktikumsstelleDto(helper.createPraktikumsstelleEntity(
                        "ITM-DS1",
                        "Ausbilder 1",
                        "a@b.c",
                        "Taetigkeiten 1",
                        "Wuensche 1",
                        Dringlichkeit.DRINGEND,
                        Bildungsrichtung.FISI,
                        Set.of(Ausbildungsjahr.JAHR2, Ausbildungsjahr.JAHR3),
                        null,
                        false,
                        false,
                        true,
                        null,
                        helper.createNwkEntity("Vorname 1", "Nachname 1", Bildungsrichtung.FISI, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createPraktikumsstelleEntity(
                        "ITM-DS2",
                        "Ausbilder 2",
                        "a@b.c",
                        "Taetigkeiten 2",
                        null,
                        Dringlichkeit.DRINGEND,
                        Bildungsrichtung.FISI,
                        Set.of(Ausbildungsjahr.JAHR2),
                        null,
                        false,
                        true,
                        false,
                        null,
                        helper.createNwkEntity("Vorname 2", "Nachname 2", Bildungsrichtung.FISI, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createPraktikumsstelleEntity(
                        "ITM-DS3",
                        "Ausbilder 3",
                        "a@b.c",
                        "Taetigkeiten 3",
                        "Wuensche 3",
                        Dringlichkeit.DRINGEND,
                        Bildungsrichtung.BWI,
                        null,
                        Set.of(Studiensemester.SEMESTER5, Studiensemester.SEMESTER4),
                        true,
                        false,
                        false,
                        null,
                        helper.createNwkEntity("Vorname 3", "Nachname 3", Bildungsrichtung.BSC, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createPraktikumsstelleEntity(
                        "ITM-DS4",
                        "Ausbilder 4",
                        "a@b.c",
                        "Taetigkeiten 4",
                        null,
                        Dringlichkeit.ZWINGEND,
                        Bildungsrichtung.VI,
                        null,
                        Set.of(Studiensemester.SEMESTER2),
                        false,
                        false,
                        false,
                        null,
                        helper.createNwkEntity("Vorname 4", "Nachname 4", Bildungsrichtung.BSC, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createPraktikumsstelleEntity(
                        "ITM-DS5",
                        "Ausbilder 5",
                        "a@b.c",
                        "Taetigkeiten 5",
                        null,
                        Dringlichkeit.NACHRANGIG,
                        Bildungsrichtung.BSC,
                        null,
                        Set.of(Studiensemester.SEMESTER3),
                        false,
                        false,
                        false,
                        null,
                        helper.createNwkEntity("Vorname 5", "Nachname 5", Bildungsrichtung.FISI, "22/23", null, true))));
    }
}
