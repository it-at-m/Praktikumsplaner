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
import java.util.List;
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
            if (stream == null) throw new IOException("Template file not found");
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

    private void fillAusbildungspraktikumsstellen(List<AusbildungsPraktikumsstelleDto> assignedAusbildungspraktikumsstellen, XSSFSheet ausbildungsSheet) {
        for (int i = 0; i < assignedAusbildungspraktikumsstellen.size(); i++) {
            AusbildungsPraktikumsstelleDto praktikumsstelle = assignedAusbildungspraktikumsstellen.get(i);
            ausbildungsSheet.getRow(i + 3).getCell(0).setCellValue(praktikumsstelle.referat().name());
            ausbildungsSheet.getRow(i + 3).getCell(1).setCellValue(praktikumsstelle.dienststelle());
            ausbildungsSheet.getRow(i + 3).getCell(2).setCellValue(praktikumsstelle.oertlicheAusbilder());
            ausbildungsSheet.getRow(i + 3).getCell(3).setCellValue(praktikumsstelle.taetigkeiten());
            ausbildungsSheet.getRow(i + 3).getCell(4).setCellValue(praktikumsstelle.namentlicheAnforderung());
            ausbildungsSheet.getRow(i + 3).getCell(5).setCellValue(programmierkenntnisseSwitch(praktikumsstelle.programmierkenntnisse()));
            ausbildungsSheet.getRow(i + 3).getCell(6).setCellValue(praktikumsstelle.projektarbeit() ? "Ja" : "Nein");
            ausbildungsSheet.getRow(i + 3).getCell(7).setCellValue(ausbildungsjahrSwitch(praktikumsstelle.ausbildungsjahr()));
            ausbildungsSheet.getRow(i + 3).getCell(8).setCellValue(praktikumsstelle.dringlichkeit().name());
            ausbildungsSheet.getRow(i + 3).getCell(9).setCellValue(praktikumsstelle.ausbildungsrichtung().name());
            ausbildungsSheet.getRow(i + 3).getCell(10).setCellValue(praktikumsstelle.planstelleVorhanden() ? "Ja" : "Nein");
            ausbildungsSheet.getRow(i + 3).getCell(11).setCellValue(praktikumsstelle.assignedNwk().nachname());
            ausbildungsSheet.getRow(i + 3).getCell(12).setCellValue(praktikumsstelle.assignedNwk().vorname());
            ausbildungsSheet.getRow(i + 3).getCell(13).setCellValue(praktikumsstelle.assignedNwk().jahrgang());
        }
    }

    private void fillStudiumspraktikumsstellen(List<StudiumsPraktikumsstelleDto> assignedStudiumspraktikumsstellen, XSSFSheet studiumsSheet) {
        for (int i = 0; i < assignedStudiumspraktikumsstellen.size(); i++) {
            StudiumsPraktikumsstelleDto praktikumsstelle = assignedStudiumspraktikumsstellen.get(i);
            studiumsSheet.getRow(i + 3).getCell(0).setCellValue(praktikumsstelle.referat().name());
            studiumsSheet.getRow(i + 3).getCell(1).setCellValue(praktikumsstelle.dienststelle());
            studiumsSheet.getRow(i + 3).getCell(2).setCellValue(praktikumsstelle.oertlicheAusbilder());
            studiumsSheet.getRow(i + 3).getCell(3).setCellValue(praktikumsstelle.taetigkeiten());
            studiumsSheet.getRow(i + 3).getCell(4).setCellValue(praktikumsstelle.namentlicheAnforderung());
            studiumsSheet.getRow(i + 3).getCell(5).setCellValue(programmierkenntnisseSwitch(praktikumsstelle.programmierkenntnisse()));
            studiumsSheet.getRow(i + 3).getCell(6).setCellValue(praktikumsstelle.dringlichkeit().name());
            studiumsSheet.getRow(i + 3).getCell(7).setCellValue(studiensemesterSwitch(praktikumsstelle.studiensemester()));
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

        assignedAusbildungspraktikumsstellen.stream().filter(praktikumsstelle -> praktikumsstelle.assignedNwk().ausbildungsrichtung() == null).forEach(praktikumsstelle ->{
            assignedStudiumspraktikumsstellen.add(turnAusbildungsIntoStudiumspraktikumsstelle(praktikumsstelle));
            toDeleteAusbildungspraktikumsstellen.add(praktikumsstelle);
        });
        assignedAusbildungspraktikumsstellen.removeAll(toDeleteAusbildungspraktikumsstellen);

        assignedStudiumspraktikumsstellen.stream().filter(praktikumsstelle -> praktikumsstelle.assignedNwk().studiengang() == null).forEach(praktikumsstelle ->{
            assignedAusbildungspraktikumsstellen.add(turnStudiumsIntoAusbildungspraktikumsstelle(praktikumsstelle));
            toDeleteStudiumspraktikumsstellen.add(praktikumsstelle);
        });
        assignedStudiumspraktikumsstellen.removeAll(toDeleteStudiumspraktikumsstellen);

        return Pair.of(assignedAusbildungspraktikumsstellen, assignedStudiumspraktikumsstellen);
    }

    private static AusbildungsPraktikumsstelleDto turnStudiumsIntoAusbildungspraktikumsstelle(StudiumsPraktikumsstelleDto praktikumsstelle) {
        return AusbildungsPraktikumsstelleDto.builder()
                .referat(praktikumsstelle.referat())
                .dienststelle(praktikumsstelle.dienststelle())
                .oertlicheAusbilder(praktikumsstelle.oertlicheAusbilder())
                .taetigkeiten(praktikumsstelle.taetigkeiten())
                .namentlicheAnforderung(praktikumsstelle.namentlicheAnforderung())
                .programmierkenntnisse(praktikumsstelle.programmierkenntnisse())
                .dringlichkeit(praktikumsstelle.dringlichkeit())
                .ausbildungsjahr(Ausbildungsjahr.JAHR1)
                .ausbildungsrichtung(praktikumsstelle.assignedNwk().ausbildungsrichtung())
                .planstelleVorhanden(praktikumsstelle.planstelleVorhanden())
                .assignedNwk(praktikumsstelle.assignedNwk())
                .build();
    }

    private static StudiumsPraktikumsstelleDto turnAusbildungsIntoStudiumspraktikumsstelle(AusbildungsPraktikumsstelleDto praktikumsstelle) {
        return StudiumsPraktikumsstelleDto.builder()
                .referat(praktikumsstelle.referat())
                .dienststelle(praktikumsstelle.dienststelle())
                .oertlicheAusbilder(praktikumsstelle.oertlicheAusbilder())
                .taetigkeiten(praktikumsstelle.taetigkeiten())
                .namentlicheAnforderung(praktikumsstelle.namentlicheAnforderung())
                .programmierkenntnisse(praktikumsstelle.programmierkenntnisse())
                .dringlichkeit(praktikumsstelle.dringlichkeit())
                .studiensemester(Studiensemester.SEMESTER1)
                .studiengang(praktikumsstelle.assignedNwk().studiengang())
                .planstelleVorhanden(praktikumsstelle.planstelleVorhanden())
                .assignedNwk(praktikumsstelle.assignedNwk())
                .build();
    }

    private String programmierkenntnisseSwitch(String programmierkenntnisse) {
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

    private String ausbildungsjahrSwitch(Ausbildungsjahr ausbildungsjahr) {
        switch (ausbildungsjahr) {
        case JAHR1 -> {
            return "ab 1. Jahr";
        }
        case JAHR2 -> {
            return "ab 2. Jahr";
        }
        case JAHR3 -> {
            return "ab 3. Jahr";
        }
        default -> {
            return "";
        }
        }
    }

    private String studiensemesterSwitch(Studiensemester studiensemester) {
        switch (studiensemester) {
        case SEMESTER1 -> {
            return "ab 1. Semester";
        }
        case SEMESTER2 -> {
            return "ab 2. Semester";
        }
        case SEMESTER3 -> {
            return "ab 3. Semester";
        }
        case SEMESTER4 -> {
            return "ab 4. Semester";
        }
        case SEMESTER5 -> {
            return "ab 5. Semester";
        }
        case SEMESTER6 -> {
            return "ab 6. Semester";
        }
        default -> {
            return "";
        }
        }
    }
}
