package de.muenchen.oss.praktikumsplaner.service;

import static org.apache.poi.ss.util.CellReference.convertColStringToIndex;

import com.nimbusds.jose.util.Pair;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
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
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class ExcelExportService {
    private final PraktikumsstellenService praktikumsstellenService;

    /*
     * The template has 4 sheets:
     * 0. Drop-Down (hidden)
     * 1. Ausbildung (QE2)
     * 2. Studium (QE3)
     * 3. Legende
     */
    public static final int AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX = 1;
    public static final int STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX = 2;

    @Value("${app.export.oertl-ausbildungsleitung-name:}")
    private String oertlAusbildungsleitungName;

    @Value("${app.export.dienststelle-adresse:}")
    private String dienstelleAdresse;

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
        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        XSSFSheet ausbildungsSheet = workbook.getSheetAt(AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX);
        XSSFSheet studiumsSheet = workbook.getSheetAt(STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX);
        Pair<List<AusbildungsPraktikumsstelleDto>, List<StudiumsPraktikumsstelleDto>> sortedPraktikumsstellen = preparePraktikumsstellen();
        List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen = sortedPraktikumsstellen.getLeft();
        List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen = sortedPraktikumsstellen.getRight();
        fillAusbildungspraktikumsstellen(assignedAusbildungspraktikumsstellen, ausbildungsSheet);
        fillStudiumspraktikumsstellen(assignedStudiumspraktikumsstellen, studiumsSheet);
        return workbook;
    }

    //Ignore Duplicate Code with fillStudiumspraktikumsstellen as Mapping is not always the same
    @SuppressWarnings("CPD-START")
    private void fillAusbildungspraktikumsstellen(final List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen,
            final XSSFSheet ausbildungsSheet) {
        for (int i = 0; i < assignedAusbildungspraktikumsstellen.size(); i++) {
            AusbildungsPraktikumsstelleDto praktikumsstelle = assignedAusbildungspraktikumsstellen.get(i);
            Row row = getRow(ausbildungsSheet, i);

            row.getCell(convertColStringToIndex("A")).setCellValue(praktikumsstelle.referat().name());
            row.getCell(convertColStringToIndex("B")).setCellValue(this.oertlAusbildungsleitungName);
            row.getCell(convertColStringToIndex("C")).setCellValue(praktikumsstelle.dienststelle());
            // row.getCell(ROW_MAP.get("D")).setCellValue(---);
            row.getCell(convertColStringToIndex("E")).setCellValue(this.dienstelleAdresse);
            row.getCell(convertColStringToIndex("F")).setCellValue(praktikumsstelle.oertlicheAusbilder());
            row.getCell(convertColStringToIndex("G")).setCellValue(praktikumsstelle.email());
            row.getCell(convertColStringToIndex("H")).setCellValue(praktikumsstelle.taetigkeiten());
            row.getCell(convertColStringToIndex("I")).setCellValue(getWuensche(praktikumsstelle));
            // row.getCell(ROW_MAP.get("J")).setCellValue(---);
            row.getCell(convertColStringToIndex("K")).setCellValue(praktikumsstelle.planstelleVorhanden() ? "Planstelle" : "Praktikumsplatz");
            row.getCell(convertColStringToIndex("L")).setCellValue(praktikumsstelle.dringlichkeit().name());
            row.getCell(convertColStringToIndex("M")).setCellValue(ausbildungsjahrToStringConverter(praktikumsstelle.ausbildungsjahr()));
            row.getCell(convertColStringToIndex("N")).setCellValue(praktikumsstelle.ausbildungsrichtung().name());
            row.getCell(convertColStringToIndex("O")).setCellValue(praktikumsstelle.assignedNwk().nachname());
            row.getCell(convertColStringToIndex("P")).setCellValue(praktikumsstelle.assignedNwk().vorname());
            row.getCell(convertColStringToIndex("Q")).setCellValue(praktikumsstelle.assignedNwk().jahrgang());
        }
    }

    private void fillStudiumspraktikumsstellen(final List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen, final XSSFSheet studiumsSheet) {
        for (int i = 0; i < assignedStudiumspraktikumsstellen.size(); i++) {
            StudiumsPraktikumsstelleDto praktikumsstelle = assignedStudiumspraktikumsstellen.get(i);
            Row row = getRow(studiumsSheet, i);

            row.getCell(convertColStringToIndex("A")).setCellValue(praktikumsstelle.referat().name());
            row.getCell(convertColStringToIndex("B")).setCellValue(this.oertlAusbildungsleitungName);
            row.getCell(convertColStringToIndex("C")).setCellValue(praktikumsstelle.dienststelle());
            // row.getCell(ROW_MAP.get("D")).setCellValue(---);
            row.getCell(convertColStringToIndex("E")).setCellValue(this.dienstelleAdresse);
            row.getCell(convertColStringToIndex("F")).setCellValue(praktikumsstelle.oertlicheAusbilder());
            row.getCell(convertColStringToIndex("G")).setCellValue(praktikumsstelle.email());
            row.getCell(convertColStringToIndex("H")).setCellValue(praktikumsstelle.taetigkeiten());
            row.getCell(convertColStringToIndex("I")).setCellValue(getWuensche(praktikumsstelle));
            // row.getCell(ROW_MAP.get("J")).setCellValue(---);
            row.getCell(convertColStringToIndex("K")).setCellValue(mapProgrammierkenntnisse(praktikumsstelle.programmierkenntnisse()));
            row.getCell(convertColStringToIndex("L")).setCellValue(praktikumsstelle.planstelleVorhanden() ? "Planstelle" : "Praktikumsplatz");
            row.getCell(convertColStringToIndex("M")).setCellValue(praktikumsstelle.dringlichkeit().name());
            row.getCell(convertColStringToIndex("N")).setCellValue(studiensemesterToStringConverter(praktikumsstelle.studiensemester()));
            row.getCell(convertColStringToIndex("O")).setCellValue(praktikumsstelle.studiengang().name());
            row.getCell(convertColStringToIndex("P")).setCellValue(praktikumsstelle.assignedNwk().nachname());
            row.getCell(convertColStringToIndex("Q")).setCellValue(praktikumsstelle.assignedNwk().vorname());
            row.getCell(convertColStringToIndex("R")).setCellValue(praktikumsstelle.assignedNwk().jahrgang());
        }
    }

    /*
     * This method prepares the praktikumsstellen by making all praktikumsstellen with students assigned
     * to them studiumspraktikumsstellen, regardless if they were ausbildungspraktikumsstellen before,
     * and all praktikumsstellen with apprentices assigned to them ausbildungspraktikumsstellen.
     */
    @SuppressWarnings("CPD-END")
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

    private static String getWuensche(PraktikumsstelleDto praktikumsstelle) {
        var wuensche = new StringJoiner(", ");

        if (StringUtils.hasText(praktikumsstelle.namentlicheAnforderung())) {
            wuensche.add("Namentliche Anforderung: " + praktikumsstelle.namentlicheAnforderung());
        }

        addProgrammierkenntnisseWunsch(wuensche, praktikumsstelle.programmierkenntnisse());

        return wuensche.toString();
    }

    private static void addProgrammierkenntnisseWunsch(StringJoiner wuensche, final String programmierkenntnisse) {
        if (!StringUtils.hasText(programmierkenntnisse)) return;
        if (Boolean.parseBoolean(programmierkenntnisse.trim())) {
            wuensche.add("Programmierkenntnisse von Vorteil");
        }
    }

    private static String mapProgrammierkenntnisse(final String programmierkenntnisse) {
        return (StringUtils.hasText(programmierkenntnisse) && Boolean.parseBoolean(programmierkenntnisse.trim())) ? "Ja" : "Nein";
    }

    private static String ausbildungsjahrToStringConverter(final Set<Ausbildungsjahr> ausbildungsjahr) {
        StringJoiner returnString = new StringJoiner(", ");
        List<Ausbildungsjahr> ausbildungsjahrSortedList = ausbildungsjahr.stream().sorted(Comparator.comparingInt(Ausbildungsjahr::ordinal))
                .collect(Collectors.toCollection(ArrayList::new));
        for (Ausbildungsjahr jahr : ausbildungsjahrSortedList) {
            switch (jahr) {
            case JAHR1 -> returnString.add("vorrangig 1. Jahr");
            case JAHR2 -> returnString.add("vorrangig 2. Jahr");
            case JAHR3 -> returnString.add("vorrangig 3. Jahr");
            }
        }
        return returnString.toString();
    }

    private static String studiensemesterToStringConverter(final Set<Studiensemester> studiensemester) {
        StringJoiner returnString = new StringJoiner(", ");
        List<Studiensemester> studiensemesterSortedList = studiensemester.stream().sorted(Comparator.comparingInt(Studiensemester::ordinal))
                .collect(Collectors.toCollection(ArrayList::new));
        for (Studiensemester semester : studiensemesterSortedList) {
            switch (semester) {
            case SEMESTER1, SEMESTER2 -> returnString.add("vorrangig 1. Jahr");
            case SEMESTER3, SEMESTER4 -> returnString.add("vorrangig 2. Jahr");
            case SEMESTER5, SEMESTER6 -> returnString.add("vorrangig 3. Jahr");
            }
        }
        return returnString.toString();
    }

    private static Row getRow(XSSFSheet sheet, int i) {
        Row row = sheet.getRow(i + 3);
        if (row == null) {
            row = sheet.createRow(i + 3);
        }
        return row;
    }
}
