package de.muenchen.oss.praktikumsplaner.service;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ExcelExportServiceTest {

    @InjectMocks
    private ExcelExportService service;
    @Mock
    private PraktikumsstellenService praktikumsstellenService;

    private final ServiceTestHelper helper = new ServiceTestHelper();

    @Test
    public void testGetTemplateExcelFile() throws IOException {
        assertNotNull(service.getTemplateExcelFile());
    }

    @Test
    public void testFillTemplatePraktikumsstellen() throws IOException {
        when(praktikumsstellenService.getAllAssignedAusbildungspraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(getTestListOfAusbildungsPraktikumsstelleDto());
        when(praktikumsstellenService.getAllAssignedStudiumspraktikumsstellenInMostRecentPassedMeldezeitraum())
                .thenReturn(getTestListOfStudiumsPraktikumsstelleDto());

        assertNotNull(service.fillTemplatePraktikumsstellen());
        service.fillTemplatePraktikumsstellen().write(new FileOutputStream(
                "src/test/resources/templates/ITM_IT_POR_filled.xlsx"));
    }

    @Test
    public void testGetBase64EncodedExcelFile() throws IOException {
        assertNotNull(service.getBase64EncodedExcelFile());
    }

    @Test
    public void testSortPraktikumsstellen() {

    }

    public List<AusbildungsPraktikumsstelleDto> getTestListOfAusbildungsPraktikumsstelleDto() {
        return List.of(
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("Dienststelle 1", "Ausbilder 1", "a@b.c", "Taetigkeiten 1",
                        Dringlichkeit.DRINGEND, Referat.ITM, Ausbildungsjahr.JAHR1, Ausbildungsrichtung.FISI, false, null,
                        helper.createNwkEntity("Vorname 1", "Nachname 1", null, Ausbildungsrichtung.FISI, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createAusbildungsPraktikumsstelleEntity("Dienststelle 2", "Ausbilder 2", "a@b.c", "Taetigkeiten 2",
                        Dringlichkeit.DRINGEND, Referat.ITM, Ausbildungsjahr.JAHR1, Ausbildungsrichtung.FISI, false, null,
                        helper.createNwkEntity("Vorname 2", "Nachname 2", null, Ausbildungsrichtung.FISI, "22/23", null, true))));
    }

    public List<StudiumsPraktikumsstelleDto> getTestListOfStudiumsPraktikumsstelleDto() {
        return List.of(
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 3", "Ausbilder 3", "a@b.c", "Taetigkeiten 3",
                        Dringlichkeit.DRINGEND, Referat.ITM, Studiensemester.SEMESTER1, Studiengang.BSC, "false", null,
                        helper.createNwkEntity("Vorname 3", "Nachname 3", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 4", "Ausbilder 4", "a@b.c", "Taetigkeiten 4",
                        Dringlichkeit.DRINGEND, Referat.ITM, Studiensemester.SEMESTER1, Studiengang.BSC, "false", null,
                        helper.createNwkEntity("Vorname 4", "Nachname 4", Studiengang.BSC, null, "22/23", null, true))),
                helper.createPraktikumsstelleDto(helper.createStudiumsPraktikumsstelleEntity("Dienststelle 5", "Ausbilder 5", "a@b.c", "Taetigkeiten 5",
                        Dringlichkeit.DRINGEND, Referat.ITM, Studiensemester.SEMESTER1, Studiengang.BSC, "false", null,
                        helper.createNwkEntity("Vorname 5", "Nachname 5", null, Ausbildungsrichtung.FISI, "22/23", null, true))));
    }
}
