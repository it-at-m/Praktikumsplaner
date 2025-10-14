package de.muenchen.oss.praktikumsplaner.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExcelExportServiceTest {

    public static final String comma = ",";
    @InjectMocks
    private ExcelExportService service;
    @Mock
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
            assertEquals(ausbildungsPraktikumsstellen.get(0).referat().name(), ausbildungsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).dienststelle(), ausbildungsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).oertlicheAusbilder(), ausbildungsSheet.getRow(3).getCell(5).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).taetigkeiten(), ausbildungsSheet.getRow(3).getCell(7).getStringCellValue());
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Programmierkenntnisse von Vorteil")));
            assertThat(ausbildungsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Namentliche Anforderung:")));
            assertEquals("Nein", ausbildungsSheet.getRow(3).getCell(9).getStringCellValue());
            assertEquals("vorrangig 1. Jahr", ausbildungsSheet.getRow(3).getCell(13).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).dringlichkeit().name(), ausbildungsSheet.getRow(3).getCell(12).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).ausbildungsrichtung().name(), ausbildungsSheet.getRow(3).getCell(14).getStringCellValue());
            assertEquals("Praktikumsplatz", ausbildungsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).assignedNwk().nachname(), ausbildungsSheet.getRow(3).getCell(15).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).assignedNwk().vorname(), ausbildungsSheet.getRow(3).getCell(16).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).assignedNwk().jahrgang(), ausbildungsSheet.getRow(3).getCell(17).getStringCellValue());

            List<StudiumsPraktikumsstelleDto> studiumsPraktikumsstellen = getTestListOfStudiumsPraktikumsstelleDto();

            assertEquals(studiumsPraktikumsstellen.get(0).referat().name(), studiumsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).dienststelle(), studiumsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).oertlicheAusbilder(), studiumsSheet.getRow(3).getCell(5).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).taetigkeiten(), studiumsSheet.getRow(3).getCell(7).getStringCellValue());
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), not(containsString("Namentliche Anforderung:")));
            assertThat(studiumsSheet.getRow(3).getCell(8).getStringCellValue(), containsString("Programmierkenntnisse von Vorteil"));
            assertEquals("Ja", studiumsSheet.getRow(3).getCell(10).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).dringlichkeit().name(), studiumsSheet.getRow(3).getCell(12).getStringCellValue());
            assertEquals("vorrangig 1. Jahr", studiumsSheet.getRow(3).getCell(13).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).studiengang().name(), studiumsSheet.getRow(3).getCell(14).getStringCellValue());
            assertEquals("Praktikumsplatz", studiumsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).assignedNwk().nachname(), studiumsSheet.getRow(3).getCell(15).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).assignedNwk().vorname(), studiumsSheet.getRow(3).getCell(16).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).assignedNwk().jahrgang(), studiumsSheet.getRow(3).getCell(17).getStringCellValue());
        }
    }

    @Test
    public void testSortPraktikumsstellen() throws IOException {
        List<AusbildungsPraktikumsstelleDto> ausbildungsPraktikumsstellenWithStudent = List.of(
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("Dienststelle 1", "Ausbilder 1", "a@b.c", "Taetigkeiten 1",
                        Dringlichkeit.DRINGEND, Referat.ITM, Set.of(Ausbildungsjahr.JAHR1), Ausbildungsrichtung.FISI, false, null,
                        helper.createNwkEntity("Vorname 1", "Nachname 1", Studiengang.BSC, null, "22/23", null, true))));

        List<StudiumsPraktikumsstelleDto> studiumsPraktikumsstellenWithAuszubildende = List.of(
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 3", "Ausbilder 3", "a@b.c", "Taetigkeiten 3",
                        Dringlichkeit.DRINGEND, Referat.ITM, Set.of(Studiensemester.SEMESTER1), Studiengang.BSC, "false", null,
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
            assertEquals(ausbildungsPraktikumsstellenWithStudent.get(0).dienststelle(), studiumsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals(studiumsPraktikumsstellenWithAuszubildende.get(0).dienststelle(), ausbildungsSheet.getRow(3).getCell(2).getStringCellValue());
        }
    }

    @Test
    public void testGetBase64EncodedExcelFile() throws IOException {
        assertNotNull(service.getBase64EncodedExcelFile());
    }

    private List<AusbildungsPraktikumsstelleDto> getTestListOfAusbildungsPraktikumsstelleDto() {
        return List.of(
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("Dienststelle 1", "Ausbilder 1", "a@b.c", "Taetigkeiten 1",
                        Dringlichkeit.DRINGEND, Referat.ITM, Set.of(Ausbildungsjahr.JAHR1), Ausbildungsrichtung.FISI, false, null,
                        helper.createNwkEntity("Vorname 1", "Nachname 1", null, Ausbildungsrichtung.FISI, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("Dienststelle 2", "Ausbilder 2", "a@b.c", "Taetigkeiten 2",
                        Dringlichkeit.DRINGEND, Referat.ITM, Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, true, null,
                        helper.createNwkEntity("Vorname 2", "Nachname 2", null, Ausbildungsrichtung.FISI, "22/23", null, true))));
    }

    private List<StudiumsPraktikumsstelleDto> getTestListOfStudiumsPraktikumsstelleDto() {
        return List.of(
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 3", "Ausbilder 3", "a@b.c", "Taetigkeiten 3",
                        Dringlichkeit.DRINGEND, Referat.RIT, Set.of(Studiensemester.SEMESTER1), Studiengang.BWI, "true", null,
                        helper.createNwkEntity("Vorname 3", "Nachname 3", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 4", "Ausbilder 4", "a@b.c", "Taetigkeiten 4",
                        Dringlichkeit.ZWINGEND, Referat.ITM, Set.of(Studiensemester.SEMESTER2), Studiengang.VI, "false", null,
                        helper.createNwkEntity("Vorname 4", "Nachname 4", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 5", "Ausbilder 5", "a@b.c", "Taetigkeiten 5",
                        Dringlichkeit.NACHRANGIG, Referat.ITM, Set.of(Studiensemester.SEMESTER3), Studiengang.BSC, "false", null,
                        helper.createNwkEntity("Vorname 5", "Nachname 5", null, Ausbildungsrichtung.FISI, "22/23", null, true))));
    }
}
