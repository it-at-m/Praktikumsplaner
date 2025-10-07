package de.muenchen.oss.praktikumsplaner.service;

import com.nimbusds.jose.util.Pair;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExcelExportService {
    private final PraktikumsstellenService praktikumsstellenService;
    private static final int AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX = 0;
    private static final int STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX = 1;

    public String getBase64EncodedExcelFile() throws IOException {
        XSSFWorkbook workbook = fillTemplatePraktikumsstellen();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        workbook.write(bytes);
        return Base64.getEncoder().encodeToString(bytes.toByteArray());
    }

    private XSSFWorkbook getTemplateExcelFile() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/templates/ITM_IT_POR.xlsx")) {
            if (stream == null) throw new IOException("ExcelExport Vorlage nicht gefunden.");
            return new XSSFWorkbook(stream);
        }
    }

    private XSSFWorkbook fillTemplatePraktikumsstellen() throws IOException {
        XSSFWorkbook workbook = getTemplateExcelFile();
        XSSFSheet ausbildungsSheet = workbook.getSheetAt(AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX);
        XSSFSheet studiumsSheet = workbook.getSheetAt(STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX);
        Pair<List<AusbildungsPraktikumsstelleDto>, List<StudiumsPraktikumsstelleDto>> sortedPraktikumsstellen = preparePraktikumsstellen();
        List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen = sortedPraktikumsstellen.getLeft();
        List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen = sortedPraktikumsstellen.getRight();
        fillAusbildungspraktikumsstellen(assignedAusbildungspraktikumsstellen, ausbildungsSheet);
        fillStudiumspraktikumsstellen(assignedStudiumspraktikumsstellen, studiumsSheet);
        return workbook;
    }

    private void fillAusbildungspraktikumsstellen(final List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen,
            final XSSFSheet ausbildungsSheet) {
        for (int i = 0; i < assignedAusbildungspraktikumsstellen.size(); i++) {
            AusbildungsPraktikumsstelleDto praktikumsstelle = assignedAusbildungspraktikumsstellen.get(i);
            ausbildungsSheet.getRow(i + 3).getCell(0).setCellValue(praktikumsstelle.referat().name());
            ausbildungsSheet.getRow(i + 3).getCell(1).setCellValue(praktikumsstelle.dienststelle());
            ausbildungsSheet.getRow(i + 3).getCell(2).setCellValue(praktikumsstelle.oertlicheAusbilder());
            ausbildungsSheet.getRow(i + 3).getCell(3).setCellValue(praktikumsstelle.taetigkeiten());
            ausbildungsSheet.getRow(i + 3).getCell(4).setCellValue(praktikumsstelle.namentlicheAnforderung());
            ausbildungsSheet.getRow(i + 3).getCell(5).setCellValue(programmierkenntnisseSwitch(praktikumsstelle.programmierkenntnisse()));
            ausbildungsSheet.getRow(i + 3).getCell(6).setCellValue(praktikumsstelle.projektarbeit() ? "Ja" : "Nein");
            ausbildungsSheet.getRow(i + 3).getCell(7).setCellValue(ausbildungsjahrToStringConverter(praktikumsstelle.ausbildungsjahr()));
            ausbildungsSheet.getRow(i + 3).getCell(8).setCellValue(praktikumsstelle.dringlichkeit().name());
            ausbildungsSheet.getRow(i + 3).getCell(9).setCellValue(praktikumsstelle.ausbildungsrichtung().name());
            ausbildungsSheet.getRow(i + 3).getCell(10).setCellValue(praktikumsstelle.planstelleVorhanden() ? "Ja" : "Nein");
            ausbildungsSheet.getRow(i + 3).getCell(11).setCellValue(praktikumsstelle.assignedNwk().nachname());
            ausbildungsSheet.getRow(i + 3).getCell(12).setCellValue(praktikumsstelle.assignedNwk().vorname());
            ausbildungsSheet.getRow(i + 3).getCell(13).setCellValue(praktikumsstelle.assignedNwk().jahrgang());
        }
    }

    private void fillStudiumspraktikumsstellen(final List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen, final XSSFSheet studiumsSheet) {
        for (int i = 0; i < assignedStudiumspraktikumsstellen.size(); i++) {
            StudiumsPraktikumsstelleDto praktikumsstelle = assignedStudiumspraktikumsstellen.get(i);
            studiumsSheet.getRow(i + 3).getCell(0).setCellValue(praktikumsstelle.referat().name());
            studiumsSheet.getRow(i + 3).getCell(1).setCellValue(praktikumsstelle.dienststelle());
            studiumsSheet.getRow(i + 3).getCell(2).setCellValue(praktikumsstelle.oertlicheAusbilder());
            studiumsSheet.getRow(i + 3).getCell(3).setCellValue(praktikumsstelle.taetigkeiten());
            studiumsSheet.getRow(i + 3).getCell(4).setCellValue(praktikumsstelle.namentlicheAnforderung());
            studiumsSheet.getRow(i + 3).getCell(5).setCellValue(programmierkenntnisseSwitch(praktikumsstelle.programmierkenntnisse()));
            studiumsSheet.getRow(i + 3).getCell(6).setCellValue(praktikumsstelle.dringlichkeit().name());
            studiumsSheet.getRow(i + 3).getCell(7).setCellValue(studiensemesterToStringConverter(praktikumsstelle.studiensemester()));
            studiumsSheet.getRow(i + 3).getCell(8).setCellValue(praktikumsstelle.studiengang().name());
            studiumsSheet.getRow(i + 3).getCell(9).setCellValue(praktikumsstelle.planstelleVorhanden() ? "Ja" : "Nein");
            studiumsSheet.getRow(i + 3).getCell(10).setCellValue(praktikumsstelle.assignedNwk().nachname());
            studiumsSheet.getRow(i + 3).getCell(11).setCellValue(praktikumsstelle.assignedNwk().vorname());
            studiumsSheet.getRow(i + 3).getCell(12).setCellValue(praktikumsstelle.assignedNwk().jahrgang());
        }
    }

    /*
     * This method prepares the praktikumsstellen by making all praktikumsstellen with students assigned
     * to them studiumspraktikumsstellen, regardless if they were ausbildungspraktikumsstellen before,
     * and all praktikumsstellen with apprentices assigned to them ausbildungspraktikumsstellen.
     */
    private Pair<List<AusbildungsPraktikumsstelleDto>, List<StudiumsPraktikumsstelleDto>> preparePraktikumsstellen() {
        List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen = new ArrayList<>(praktikumsstellenService
                .getAllAssignedAusbildungspraktikumsstellenInMostRecentPassedMeldezeitraum());
        List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen = new ArrayList<>(praktikumsstellenService
                .getAllAssignedStudiumspraktikumsstellenInMostRecentPassedMeldezeitraum());
        List<AusbildungsPraktikumsstelleDto> toDeleteAusbildungspraktikumsstellen = new ArrayList<>();
        List<StudiumsPraktikumsstelleDto> toDeleteStudiumspraktikumsstellen = new ArrayList<>();

        assignedAusbildungspraktikumsstellen.stream().filter(praktikumsstelle -> praktikumsstelle.assignedNwk().ausbildungsrichtung() == null)
                .forEach(praktikumsstelle -> {
                    assignedStudiumspraktikumsstellen.add(turnAusbildungsIntoStudiumspraktikumsstelle(praktikumsstelle));
                    toDeleteAusbildungspraktikumsstellen.add(praktikumsstelle);
                });
        assignedAusbildungspraktikumsstellen.removeAll(toDeleteAusbildungspraktikumsstellen);

        assignedStudiumspraktikumsstellen.stream().filter(praktikumsstelle -> praktikumsstelle.assignedNwk().studiengang() == null)
                .forEach(praktikumsstelle -> {
                    assignedAusbildungspraktikumsstellen.add(turnStudiumsIntoAusbildungspraktikumsstelle(praktikumsstelle));
                    toDeleteStudiumspraktikumsstellen.add(praktikumsstelle);
                });
        assignedStudiumspraktikumsstellen.removeAll(toDeleteStudiumspraktikumsstellen);

        return Pair.of(assignedAusbildungspraktikumsstellen, assignedStudiumspraktikumsstellen);
    }

    private static AusbildungsPraktikumsstelleDto turnStudiumsIntoAusbildungspraktikumsstelle(final StudiumsPraktikumsstelleDto praktikumsstelle) {
        return AusbildungsPraktikumsstelleDto.builder()
                .referat(praktikumsstelle.referat())
                .dienststelle(praktikumsstelle.dienststelle())
                .oertlicheAusbilder(praktikumsstelle.oertlicheAusbilder())
                .taetigkeiten(praktikumsstelle.taetigkeiten())
                .namentlicheAnforderung(praktikumsstelle.namentlicheAnforderung())
                .programmierkenntnisse(praktikumsstelle.programmierkenntnisse())
                .dringlichkeit(praktikumsstelle.dringlichkeit())
                .ausbildungsjahr(Set.of(Ausbildungsjahr.JAHR1, Ausbildungsjahr.JAHR2, Ausbildungsjahr.JAHR3))
                .ausbildungsrichtung(praktikumsstelle.assignedNwk().ausbildungsrichtung())
                .planstelleVorhanden(praktikumsstelle.planstelleVorhanden())
                .assignedNwk(praktikumsstelle.assignedNwk())
                .build();
    }

    private static StudiumsPraktikumsstelleDto turnAusbildungsIntoStudiumspraktikumsstelle(final AusbildungsPraktikumsstelleDto praktikumsstelle) {
        return StudiumsPraktikumsstelleDto.builder()
                .referat(praktikumsstelle.referat())
                .dienststelle(praktikumsstelle.dienststelle())
                .oertlicheAusbilder(praktikumsstelle.oertlicheAusbilder())
                .taetigkeiten(praktikumsstelle.taetigkeiten())
                .namentlicheAnforderung(praktikumsstelle.namentlicheAnforderung())
                .programmierkenntnisse(praktikumsstelle.programmierkenntnisse())
                .dringlichkeit(praktikumsstelle.dringlichkeit())
                .studiensemester(Set.of(Studiensemester.SEMESTER1, Studiensemester.SEMESTER2, Studiensemester.SEMESTER3,
                        Studiensemester.SEMESTER4, Studiensemester.SEMESTER5, Studiensemester.SEMESTER6))
                .studiengang(praktikumsstelle.assignedNwk().studiengang())
                .planstelleVorhanden(praktikumsstelle.planstelleVorhanden())
                .assignedNwk(praktikumsstelle.assignedNwk())
                .build();
    }

    private String programmierkenntnisseSwitch(final String programmierkenntnisse) {
        if (programmierkenntnisse == null) return ("");
        switch (programmierkenntnisse) {
        case "true" -> {
            return "Ja";
        }
        case "false" -> {
            return "Nein";
        }
        case "egal" -> {
            return "Egal";
        }
        default -> {
            return "";
        }
        }
    }

    private String ausbildungsjahrToStringConverter(final Set<Ausbildungsjahr> ausbildungsjahr) {
        StringJoiner returnString = new StringJoiner(", ");
        List<Ausbildungsjahr> ausbildungsjahrSortedList = ausbildungsjahr.stream().sorted(Comparator.comparingInt(Ausbildungsjahr::ordinal))
                .collect(Collectors.toCollection(ArrayList::new));
        for (Ausbildungsjahr jahr : ausbildungsjahrSortedList) {
            switch (jahr) {
            case JAHR1 -> returnString.add("1. Ausbildungsjahr");
            case JAHR2 -> returnString.add("2. Ausbildungsjahr");
            case JAHR3 -> returnString.add("3. Ausbildungsjahr");
            }
        }
        return returnString.toString();
    }

    private String studiensemesterToStringConverter(final Set<Studiensemester> studiensemester) {
        StringJoiner returnString = new StringJoiner(", ");
        List<Studiensemester> studiensemesterSortedList = studiensemester.stream().sorted(Comparator.comparingInt(Studiensemester::ordinal))
                .collect(Collectors.toCollection(ArrayList::new));
        for (Studiensemester semester : studiensemesterSortedList) {
            switch (semester) {
            case SEMESTER1 -> returnString.add("1. Semester");
            case SEMESTER2 -> returnString.add("2. Semester");
            case SEMESTER3 -> returnString.add("3. Semester");
            case SEMESTER4 -> returnString.add("4. Semester");
            case SEMESTER5 -> returnString.add("5. Semester");
            case SEMESTER6 -> returnString.add("6. Semester");
            }
        }
        return returnString.toString();
    }
}
