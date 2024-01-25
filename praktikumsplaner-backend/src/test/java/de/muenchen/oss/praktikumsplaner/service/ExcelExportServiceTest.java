package de.muenchen.oss.praktikumsplaner.service;

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

            XSSFSheet ausbildungsSheet = workbook.getSheetAt(0);
            XSSFSheet studiumsSheet = workbook.getSheetAt(1);

            List<AusbildungsPraktikumsstelleDto> ausbildungsPraktikumsstellen = getTestListOfAusbildungsPraktikumsstelleDto();

            assertNotNull(workbook);
            assertEquals(2, workbook.getNumberOfSheets());
            assertEquals(ausbildungsPraktikumsstellen.get(0).referat().name(), ausbildungsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).dienststelle(), ausbildungsSheet.getRow(3).getCell(1).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).oertlicheAusbilder(), ausbildungsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).taetigkeiten(), ausbildungsSheet.getRow(3).getCell(3).getStringCellValue());
            assertEquals(
                    ausbildungsPraktikumsstellen.get(0).namentlicheAnforderung() == null ? "" : ausbildungsPraktikumsstellen.get(0).namentlicheAnforderung(),
                    ausbildungsSheet.getRow(3).getCell(4).getStringCellValue());
            assertEquals(
                    ausbildungsPraktikumsstellen.get(0).programmierkenntnisse() == null ? ""
                            : decodeProgrammierkenntnisse(ausbildungsPraktikumsstellen.get(0).programmierkenntnisse()),
                    ausbildungsSheet.getRow(3).getCell(5).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).projektarbeit(),
                    convertJaNeinToBoolean(ausbildungsSheet.getRow(3).getCell(6).getStringCellValue()));
            assertEquals(ausbildungsPraktikumsstellen.get(0).ausbildungsjahr(),
                    decodeAusbildungsjahr(ausbildungsSheet.getRow(3).getCell(7).getStringCellValue()));
            assertEquals(ausbildungsPraktikumsstellen.get(0).dringlichkeit().name(), ausbildungsSheet.getRow(3).getCell(8).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).ausbildungsrichtung().name(), ausbildungsSheet.getRow(3).getCell(9).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).planstelleVorhanden(),
                    convertJaNeinToBoolean(ausbildungsSheet.getRow(3).getCell(10).getStringCellValue()));
            assertEquals(ausbildungsPraktikumsstellen.get(0).assignedNwk().nachname(), ausbildungsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).assignedNwk().vorname(), ausbildungsSheet.getRow(3).getCell(12).getStringCellValue());
            assertEquals(ausbildungsPraktikumsstellen.get(0).assignedNwk().jahrgang(), ausbildungsSheet.getRow(3).getCell(13).getStringCellValue());

            List<StudiumsPraktikumsstelleDto> studiumsPraktikumsstellen = getTestListOfStudiumsPraktikumsstelleDto();

            assertEquals(studiumsPraktikumsstellen.get(0).referat().name(), studiumsSheet.getRow(3).getCell(0).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).dienststelle(), studiumsSheet.getRow(3).getCell(1).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).oertlicheAusbilder(), studiumsSheet.getRow(3).getCell(2).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).taetigkeiten(), studiumsSheet.getRow(3).getCell(3).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).namentlicheAnforderung() == null ? "" : studiumsPraktikumsstellen.get(0).namentlicheAnforderung(),
                    studiumsSheet.getRow(3).getCell(4).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).programmierkenntnisse(),
                    decodeProgrammierkenntnisse(studiumsSheet.getRow(3).getCell(5).getStringCellValue()));
            assertEquals(studiumsPraktikumsstellen.get(0).dringlichkeit().name(), studiumsSheet.getRow(3).getCell(6).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).studiensemester(), decodeStudiensemester(studiumsSheet.getRow(3).getCell(7).getStringCellValue()));
            assertEquals(studiumsPraktikumsstellen.get(0).studiengang().name(), studiumsSheet.getRow(3).getCell(8).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).planstelleVorhanden(),
                    convertJaNeinToBoolean(studiumsSheet.getRow(3).getCell(9).getStringCellValue()));
            assertEquals(studiumsPraktikumsstellen.get(0).assignedNwk().nachname(), studiumsSheet.getRow(3).getCell(10).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).assignedNwk().vorname(), studiumsSheet.getRow(3).getCell(11).getStringCellValue());
            assertEquals(studiumsPraktikumsstellen.get(0).assignedNwk().jahrgang(), studiumsSheet.getRow(3).getCell(12).getStringCellValue());
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
            XSSFSheet ausbildungsSheet = workbook.getSheetAt(0);
            XSSFSheet studiumsSheet = workbook.getSheetAt(1);
            assertEquals(ausbildungsPraktikumsstellenWithStudent.get(0).dienststelle(), studiumsSheet.getRow(3).getCell(1).getStringCellValue());

            assertEquals(studiumsPraktikumsstellenWithAuszubildende.get(0).dienststelle(), ausbildungsSheet.getRow(3).getCell(1).getStringCellValue());
        }
    }

    @Test
    public void testGetBase64EncodedExcelFile() throws IOException {
        assertNotNull(service.getBase64EncodedExcelFile());
    }

    private boolean convertJaNeinToBoolean(String jaNein) {
        return jaNein.equals("Ja");
    }

    private Set<Ausbildungsjahr> decodeAusbildungsjahr(String ausbildungsjahr) {
        String[] ausbildungsjahre = ausbildungsjahr.split(comma);
        Set<Ausbildungsjahr> ausbildungsjahrSet = new java.util.HashSet<>(Set.of());
        for (String jahr : ausbildungsjahre) {
            switch (jahr.trim()) {
            case "1. Ausbildungsjahr" -> ausbildungsjahrSet.add(Ausbildungsjahr.JAHR1);
            case "2. Ausbildungsjahr" -> ausbildungsjahrSet.add(Ausbildungsjahr.JAHR2);
            case "3. Ausbildungsjahr" -> ausbildungsjahrSet.add(Ausbildungsjahr.JAHR3);
            }
        }
        return ausbildungsjahrSet;
    }

    private Set<Studiensemester> decodeStudiensemester(String studiensemester) {
        String[] studiensemesterArray = studiensemester.split(comma);
        Set<Studiensemester> studiensemesterSet = new java.util.HashSet<>(Set.of());
        for (String semester : studiensemesterArray) {
            switch (semester.trim()) {
            case "1. Semester" -> studiensemesterSet.add(Studiensemester.SEMESTER1);
            case "2. Semester" -> studiensemesterSet.add(Studiensemester.SEMESTER2);
            case "3. Semester" -> studiensemesterSet.add(Studiensemester.SEMESTER3);
            case "4. Semester" -> studiensemesterSet.add(Studiensemester.SEMESTER4);
            case "5. Semester" -> studiensemesterSet.add(Studiensemester.SEMESTER5);
            case "6. Semester" -> studiensemesterSet.add(Studiensemester.SEMESTER6);
            }
        }
        return studiensemesterSet;
    }

    private String decodeProgrammierkenntnisse(String programmierkenntnisse) {
        return switch (programmierkenntnisse) {
        case "Ja" -> "true";
        case "Nein" -> "false";
        case "egal" -> "egal";
        default -> null;
        };
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
                        Dringlichkeit.DRINGEND, Referat.RIT, Set.of(Studiensemester.SEMESTER1), Studiengang.BWI, "false", null,
                        helper.createNwkEntity("Vorname 3", "Nachname 3", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 4", "Ausbilder 4", "a@b.c", "Taetigkeiten 4",
                        Dringlichkeit.ZWINGEND, Referat.ITM, Set.of(Studiensemester.SEMESTER2), Studiengang.VI, "false", null,
                        helper.createNwkEntity("Vorname 4", "Nachname 4", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 5", "Ausbilder 5", "a@b.c", "Taetigkeiten 5",
                        Dringlichkeit.NACHRANGIG, Referat.ITM, Set.of(Studiensemester.SEMESTER3), Studiengang.BSC, "false", null,
                        helper.createNwkEntity("Vorname 5", "Nachname 5", null, Ausbildungsrichtung.FISI, "22/23", null, true))));
    }
}
