package de.muenchen.oss.praktikumsplaner.service;

import static de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr.JAHR1;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr.JAHR2;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr.JAHR3;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester.SEMESTER1;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester.SEMESTER2;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester.SEMESTER3;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester.SEMESTER4;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester.SEMESTER5;
import static de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester.SEMESTER6;
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
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
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
    public static final String YES = "Ja";
    public static final String NO = "Nein";

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
        try (XSSFWorkbook workbook = fillTemplatePraktikumsstellen();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
            workbook.write(bytes);
            return Base64.getEncoder().encodeToString(bytes.toByteArray());
        }
    }

    private XSSFWorkbook getTemplateExcelFile() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/templates/ITM_IT_POR.xlsx")) {
            if (stream == null) {
                throw new IOException("ExcelExport Vorlage nicht gefunden.");
            }
            return new XSSFWorkbook(stream);
        }
    }

    private XSSFWorkbook fillTemplatePraktikumsstellen() throws IOException {
        final XSSFWorkbook workbook = getTemplateExcelFile();
        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        final XSSFSheet ausbildungsSheet = workbook.getSheetAt(AUSBILDUNGSPRAKTIKUMSSTELLEN_SHEET_INDEX);
        final XSSFSheet studiumsSheet = workbook.getSheetAt(STUDIUMSPRAKTIKUMSSTELLEN_SHEET_INDEX);
        final Pair<List<AusbildungsPraktikumsstelleDto>, List<StudiumsPraktikumsstelleDto>> sortedPraktikumsstellen = preparePraktikumsstellen();
        final List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen = sortedPraktikumsstellen.getLeft();
        final List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen = sortedPraktikumsstellen.getRight();
        fillAusbildungspraktikumsstellen(assignedAusbildungspraktikumsstellen, ausbildungsSheet);
        fillStudiumspraktikumsstellen(assignedStudiumspraktikumsstellen, studiumsSheet);
        return workbook;
    }

    //Ignore Duplicate Code with fillStudiumspraktikumsstellen as Mapping is not always the same
    @SuppressWarnings("CPD-START")
    private void fillAusbildungspraktikumsstellen(final List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen,
            final XSSFSheet ausbildungsSheet) {
        for (int i = 0; i < assignedAusbildungspraktikumsstellen.size(); i++) {
            final AusbildungsPraktikumsstelleDto praktikumsstelle = assignedAusbildungspraktikumsstellen.get(i);
            final Row row = getRow(ausbildungsSheet, i);

            row.getCell(convertColStringToIndex("A")).setCellValue(getReferatFromDienststelle(praktikumsstelle));
            row.getCell(convertColStringToIndex("B")).setCellValue(this.oertlAusbildungsleitungName);
            row.getCell(convertColStringToIndex("C")).setCellValue(praktikumsstelle.dienststelle());
            // row.getCell(convertColStringToIndex"D")).setCellValue(---);
            row.getCell(convertColStringToIndex("E")).setCellValue(this.dienstelleAdresse);
            row.getCell(convertColStringToIndex("F")).setCellValue(praktikumsstelle.oertlicheAusbilder());
            row.getCell(convertColStringToIndex("G")).setCellValue(praktikumsstelle.email());
            row.getCell(convertColStringToIndex("H")).setCellValue(praktikumsstelle.taetigkeiten());
            row.getCell(convertColStringToIndex("I")).setCellValue(getWuensche(praktikumsstelle));
            row.getCell(convertColStringToIndex("J")).setCellValue(praktikumsstelle.projektarbeit() ? YES : NO);
            row.getCell(convertColStringToIndex("K")).setCellValue(praktikumsstelle.erwFuehrungszeugnisVorhanden() ? YES : NO);
            row.getCell(convertColStringToIndex("L")).setCellValue(praktikumsstelle.planstelleVorhanden() ? "Planstelle" : "Praktikumsplatz");
            row.getCell(convertColStringToIndex("M")).setCellValue(praktikumsstelle.dringlichkeit().name());
            row.getCell(convertColStringToIndex("N")).setCellValue(ausbildungsjahrToStringConverter(praktikumsstelle.ausbildungsjahr()));
            row.getCell(convertColStringToIndex("O")).setCellValue(praktikumsstelle.ausbildungsrichtung().name());
            row.getCell(convertColStringToIndex("P")).setCellValue(praktikumsstelle.assignedNwk().nachname());
            row.getCell(convertColStringToIndex("Q")).setCellValue(praktikumsstelle.assignedNwk().vorname());
            row.getCell(convertColStringToIndex("R")).setCellValue(praktikumsstelle.assignedNwk().jahrgang());
        }
    }

    private void fillStudiumspraktikumsstellen(final List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen, final XSSFSheet studiumsSheet) {
        for (int i = 0; i < assignedStudiumspraktikumsstellen.size(); i++) {
            final StudiumsPraktikumsstelleDto praktikumsstelle = assignedStudiumspraktikumsstellen.get(i);
            final Row row = getRow(studiumsSheet, i);

            row.getCell(convertColStringToIndex("A")).setCellValue(getReferatFromDienststelle(praktikumsstelle));
            row.getCell(convertColStringToIndex("B")).setCellValue(this.oertlAusbildungsleitungName);
            row.getCell(convertColStringToIndex("C")).setCellValue(praktikumsstelle.dienststelle());
            // row.getCell(convertColStringToIndex"D")).setCellValue(---);
            row.getCell(convertColStringToIndex("E")).setCellValue(this.dienstelleAdresse);
            row.getCell(convertColStringToIndex("F")).setCellValue(praktikumsstelle.oertlicheAusbilder());
            row.getCell(convertColStringToIndex("G")).setCellValue(praktikumsstelle.email());
            row.getCell(convertColStringToIndex("H")).setCellValue(praktikumsstelle.taetigkeiten());
            row.getCell(convertColStringToIndex("I")).setCellValue(getWuensche(praktikumsstelle));
            row.getCell(convertColStringToIndex("J")).setCellValue(praktikumsstelle.erwFuehrungszeugnisVorhanden() ? YES : NO);
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
        final List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen = new ArrayList<>(praktikumsstellenService
                .getAllAssignedAusbildungspraktikumsstellenInMostRecentPassedMeldezeitraum());
        final List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen = new ArrayList<>(praktikumsstellenService
                .getAllAssignedStudiumspraktikumsstellenInMostRecentPassedMeldezeitraum());
        final List<AusbildungsPraktikumsstelleDto> toDeleteAusbildungspraktikumsstellen = new ArrayList<>();
        final List<StudiumsPraktikumsstelleDto> toDeleteStudiumspraktikumsstellen = new ArrayList<>();

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
                .dienststelle(praktikumsstelle.dienststelle())
                .oertlicheAusbilder(praktikumsstelle.oertlicheAusbilder())
                .email(praktikumsstelle.email())
                .erwFuehrungszeugnisVorhanden(praktikumsstelle.erwFuehrungszeugnisVorhanden())
                .taetigkeiten(praktikumsstelle.taetigkeiten())
                .namentlicheAnforderung(praktikumsstelle.namentlicheAnforderung())
                .programmierkenntnisse(praktikumsstelle.programmierkenntnisse())
                .dringlichkeit(praktikumsstelle.dringlichkeit())
                .ausbildungsjahr(Set.of(JAHR1, JAHR2, JAHR3))
                .ausbildungsrichtung(praktikumsstelle.assignedNwk().ausbildungsrichtung())
                .planstelleVorhanden(praktikumsstelle.planstelleVorhanden())
                .assignedNwk(praktikumsstelle.assignedNwk())
                .build();
    }

    private static StudiumsPraktikumsstelleDto turnAusbildungsIntoStudiumspraktikumsstelle(final AusbildungsPraktikumsstelleDto praktikumsstelle) {
        return StudiumsPraktikumsstelleDto.builder()
                .dienststelle(praktikumsstelle.dienststelle())
                .oertlicheAusbilder(praktikumsstelle.oertlicheAusbilder())
                .email(praktikumsstelle.email())
                .erwFuehrungszeugnisVorhanden(praktikumsstelle.erwFuehrungszeugnisVorhanden())
                .taetigkeiten(praktikumsstelle.taetigkeiten())
                .namentlicheAnforderung(praktikumsstelle.namentlicheAnforderung())
                .programmierkenntnisse(praktikumsstelle.programmierkenntnisse())
                .dringlichkeit(praktikumsstelle.dringlichkeit())
                .studiensemester(Set.of(SEMESTER1, SEMESTER2, SEMESTER3,
                        SEMESTER4, SEMESTER5, SEMESTER6))
                .studiengang(praktikumsstelle.assignedNwk().studiengang())
                .planstelleVorhanden(praktikumsstelle.planstelleVorhanden())
                .assignedNwk(praktikumsstelle.assignedNwk())
                .build();
    }

    private static String getWuensche(final PraktikumsstelleDto praktikumsstelle) {
        final StringJoiner wuensche = new StringJoiner(", ");

        if (StringUtils.hasText(praktikumsstelle.namentlicheAnforderung())) {
            wuensche.add("Namentliche Anforderung: " + praktikumsstelle.namentlicheAnforderung());
        }

        addProgrammierkenntnisseWunsch(wuensche, praktikumsstelle.programmierkenntnisse());

        return wuensche.toString();
    }

    private static void addProgrammierkenntnisseWunsch(final StringJoiner wuensche, final String programmierkenntnisse) {
        if (!StringUtils.hasText(programmierkenntnisse)) {
            return;
        }
        if (Boolean.parseBoolean(programmierkenntnisse.trim())) {
            wuensche.add("Programmierkenntnisse von Vorteil");
        }
    }

    private static String mapProgrammierkenntnisse(final String programmierkenntnisse) {
        return (StringUtils.hasText(programmierkenntnisse) && Boolean.parseBoolean(programmierkenntnisse.trim())) ? YES : NO;
    }

    private static String ausbildungsjahrToStringConverter(final Set<Ausbildungsjahr> ausbildungsjahr) {

        if (ausbildungsjahr.containsAll(List.of(JAHR1, JAHR2, JAHR3))) {
            return "";
        }

        if (ausbildungsjahr.contains(JAHR3)) {
            return "vorrangig 3. Jahr";
        }
        if (ausbildungsjahr.contains(JAHR2)) {
            return "vorrangig 2. Jahr";
        }
        if (ausbildungsjahr.contains(JAHR1)) {
            return "vorrangig 1. Jahr";
        }

        return "";
    }

    private static String studiensemesterToStringConverter(final Set<Studiensemester> studiensemester) {

        if (studiensemester.containsAll(List.of(SEMESTER1, SEMESTER2, SEMESTER3, SEMESTER4, SEMESTER5, SEMESTER6))) {
            return "";
        }

        if (studiensemester.contains(SEMESTER5) || studiensemester.contains(SEMESTER6)) {
            return "vorrangig 3. Jahr";
        }
        if (studiensemester.contains(SEMESTER3) || studiensemester.contains(SEMESTER4)) {
            return "vorrangig 2. Jahr";
        }
        if (studiensemester.contains(SEMESTER1) || studiensemester.contains(SEMESTER2)) {
            return "vorrangig 1. Jahr";
        }

        return "";
    }

    private static Row getRow(final XSSFSheet sheet, final int i) {
        Row row = sheet.getRow(i + 3);
        if (row == null) {
            row = sheet.createRow(i + 3);
        }
        return row;
    }


    private static String getReferatFromDienststelle(final PraktikumsstelleDto praktikumsstelle) {
        if (praktikumsstelle.dienststelle() == null || praktikumsstelle.dienststelle().isBlank()) {
            return "";
        }
        return praktikumsstelle.dienststelle().split("-")[0];
    }
}
