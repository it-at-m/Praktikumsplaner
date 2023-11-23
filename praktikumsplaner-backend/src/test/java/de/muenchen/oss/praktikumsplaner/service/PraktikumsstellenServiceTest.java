package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateAusbildungsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.CreateStudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDTO;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import de.muenchen.oss.praktikumsplaner.domain.mappers.PraktikumsstellenMapper;
import de.muenchen.oss.praktikumsplaner.repository.AusbildungsPraktikumsstellenRepository;
import de.muenchen.oss.praktikumsplaner.repository.StudiumsPraktikumsstellenRepository;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PraktikumsstellenServiceTest {
    @Spy
    private PraktikumsstellenMapper mapper = Mappers.getMapper(PraktikumsstellenMapper.class);
    @Mock
    private AusbildungsPraktikumsstellenRepository ausbildungsRepository;
    @Mock
    private StudiumsPraktikumsstellenRepository studiumsRepository;
    @InjectMocks
    private PraktikumsstellenService service;

    @Test
    public void testCreateStudiumsPraktikumsstelle() {
        StudiumsPraktikumsstelle studiumsPraktikumsstelle = new StudiumsPraktikumsstelle();
        studiumsPraktikumsstelle.setId(UUID.randomUUID());
        studiumsPraktikumsstelle.setDienststelle("Testdienststelle");
        studiumsPraktikumsstelle.setOertlicheAusbilder("TestoertlicheAusbilder");
        studiumsPraktikumsstelle.setEmail("test@test.de");
        studiumsPraktikumsstelle.setTaetigkeiten("Testtätigkeiten");
        studiumsPraktikumsstelle.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        studiumsPraktikumsstelle.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        studiumsPraktikumsstelle.setReferat(Referat.ITM);
        studiumsPraktikumsstelle.setProgrammierkenntnisse(true);
        studiumsPraktikumsstelle.setStudiensemester(Studiensemester.SEMESTER1);
        studiumsPraktikumsstelle.setStudienart(Studiengang.BSC);

        CreateStudiumsPraktikumsstelleDTO createDTO = CreateStudiumsPraktikumsstelleDTO.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse(true)
                .studiensemester(Studiensemester.SEMESTER1).studienart(Studiengang.BSC).build();

        StudiumsPraktikumsstelleDTO dto = StudiumsPraktikumsstelleDTO.builder().id(studiumsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).programmierkenntnisse(true)
                .studiensemester(Studiensemester.SEMESTER1).studienart(Studiengang.BSC).build();

        when(studiumsRepository.save(studiumsPraktikumsstelle)).thenReturn(studiumsPraktikumsstelle);
        when(mapper.toEntity(createDTO)).thenReturn(studiumsPraktikumsstelle);
        when(mapper.toDTO(studiumsPraktikumsstelle)).thenReturn(dto);

        StudiumsPraktikumsstelleDTO result = service.saveStudiumsPraktikumsstelle(createDTO);

        assertEquals(dto, result);
    }

    @Test
    public void testCreateAusbildungsPraktikumsstelle() {
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle = new AusbildungsPraktikumsstelle();
        ausbildungsPraktikumsstelle.setId(UUID.randomUUID());
        ausbildungsPraktikumsstelle.setDienststelle("Testdienststelle");
        ausbildungsPraktikumsstelle.setOertlicheAusbilder("TestoertlicheAusbilder");
        ausbildungsPraktikumsstelle.setEmail("test@test.de");
        ausbildungsPraktikumsstelle.setTaetigkeiten("Testtätigkeiten");
        ausbildungsPraktikumsstelle.setDringlichkeit(Dringlichkeit.NACHRANGIG);
        ausbildungsPraktikumsstelle.setNamentlicheAnforderung("TestnamentlicheAnforderung");
        ausbildungsPraktikumsstelle.setReferat(Referat.ITM);
        ausbildungsPraktikumsstelle.setProjektarbeit(true);
        ausbildungsPraktikumsstelle.setAusbildungsjahr(Ausbildungsjahr.JAHR1);
        ausbildungsPraktikumsstelle.setAusbildungsrichtung(Studiengang.FISI);

        CreateAusbildungsPraktikumsstelleDTO createDTO = CreateAusbildungsPraktikumsstelleDTO.builder()
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Ausbildungsjahr.JAHR1).ausbildungsrichtung(Studiengang.FISI).build();

        AusbildungsPraktikumsstelleDTO dto = AusbildungsPraktikumsstelleDTO.builder().id(ausbildungsPraktikumsstelle.getId())
                .dienststelle("Testdienststelle").oertlicheAusbilder("TestoertlicheAusbilder")
                .email("test@test.de").taetigkeiten("Testtätigkeiten")
                .dringlichkeit(Dringlichkeit.NACHRANGIG).namentlicheAnforderung("TestnamentlicheAnforderung")
                .referat(Referat.ITM).projektarbeit(true)
                .ausbildungsjahr(Ausbildungsjahr.JAHR1).ausbildungsrichtung(Studiengang.FISI).build();

        when(ausbildungsRepository.save(ausbildungsPraktikumsstelle)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toEntity(createDTO)).thenReturn(ausbildungsPraktikumsstelle);
        when(mapper.toDTO(ausbildungsPraktikumsstelle)).thenReturn(dto);

        AusbildungsPraktikumsstelleDTO result = service.saveAusbildungsPraktikumsstelle(createDTO);

        assertEquals(dto, result);
    }

    @Test
    public void testGetAllPraktikumsstellen() {
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle1 =
                createAusbildungsPraktikumsstelle("KM81", "Max Musterfrau", "max@musterfrau.de",
                        "Entwicklung eines Praktikumsplaners", Dringlichkeit.ZWINGEND, Referat.ITM,
                        true, Ausbildungsjahr.JAHR2, Studiengang.FISI);
        AusbildungsPraktikumsstelle ausbildungsPraktikumsstelle2 =
                createAusbildungsPraktikumsstelle("KM22", "Erika Mustermann", "erika@mustermann.de",
                        "Einarbeitung für Übernahme", Dringlichkeit.DRINGEND, Referat.ITM,
                        true, Ausbildungsjahr.JAHR3, Studiengang.FISI);
        Iterable<AusbildungsPraktikumsstelle> ausbildungsIterable =
                Arrays.asList(ausbildungsPraktikumsstelle1, ausbildungsPraktikumsstelle2);

        StudiumsPraktikumsstelle studiumsPraktikumsstelle1 =
                createStudiumsPraktikumsstelle("KM83", "Test Tester", "test@tester.de",
                        "Entwicklung eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM, true,
                        Studiensemester.SEMESTER5, Studiengang.BSC);
        StudiumsPraktikumsstelle studiumsPraktikumsstelle2 =
                createStudiumsPraktikumsstelle("InnoLab", "Test Testerin", "test@testerin.de",
                        "Design eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM, true,
                        Studiensemester.SEMESTER5, Studiengang.BWI);
        StudiumsPraktikumsstelle studiumsPraktikumsstelle3 =
                createStudiumsPraktikumsstelle("GL13", "John Smith", "John@smith.com",
                        "Planung von Events", Dringlichkeit.ZWINGEND, Referat.ITM, true,
                        Studiensemester.SEMESTER3, Studiengang.BWI);
        Iterable<StudiumsPraktikumsstelle> studiumsIterable =
                Arrays.asList(studiumsPraktikumsstelle1, studiumsPraktikumsstelle2, studiumsPraktikumsstelle3);

        // Mocken der findAll()-Methoden der Repositories
        when(ausbildungsRepository.findAll()).thenReturn(ausbildungsIterable);
        when(studiumsRepository.findAll()).thenReturn(studiumsIterable);

        // Mocken der Mapper-Methoden
        when(mapper.toDTO(any(AusbildungsPraktikumsstelle.class)))
                .thenAnswer(invocation -> createPraktikumsstelleDTO((AusbildungsPraktikumsstelle) invocation.getArguments()[0]));
        when(mapper.toDTO(any(StudiumsPraktikumsstelle.class)))
                .thenAnswer(invocation -> createPraktikumsstelleDTO((StudiumsPraktikumsstelle) invocation.getArguments()[0]));

        // Aufruf der zu testenden Methode
        TreeMap<String, List<PraktikumsstelleDTO>> result = service.getAllPraktiumsstellen();

        //Überprüfen der Ergebnisse
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("GL1", result.firstKey());
        assertTrue(result.containsKey("KM2"));
        assertTrue(result.containsKey("InnoLab"));
        assertEquals("KM8", result.lastKey());

        //Überprüfen der Anzahl an Praktikumsstellen in jeder Gruppe
        assertEquals(2, result.get("KM8").size());
        assertEquals(1, result.get("KM2").size());
        assertEquals(1, result.get("InnoLab").size());
        assertEquals(1, result.get("GL1").size());
    }

    private AusbildungsPraktikumsstelle createAusbildungsPraktikumsstelle(
            String dienststelle, String ausbilder, String email, String taetigkeiten, Dringlichkeit dringlichkeit,
            Referat referat, boolean projektarbeit, Ausbildungsjahr ausbildungsjahr, Studiengang studiengang
    ) {
        AusbildungsPraktikumsstelle stelle = new AusbildungsPraktikumsstelle();
        stelle.setId(UUID.randomUUID());
        stelle.setDienststelle(dienststelle);
        stelle.setOertlicheAusbilder(ausbilder);
        stelle.setEmail(email);
        stelle.setTaetigkeiten(taetigkeiten);
        stelle.setDringlichkeit(dringlichkeit);
        stelle.setReferat(referat);
        stelle.setProjektarbeit(projektarbeit);
        stelle.setAusbildungsjahr(ausbildungsjahr);
        stelle.setAusbildungsrichtung(studiengang);
        return stelle;
    }

    private StudiumsPraktikumsstelle createStudiumsPraktikumsstelle(
            String dienststelle, String ausbilder, String email, String taetigkeiten, Dringlichkeit dringlichkeit,
            Referat referat, boolean programmierkenntnisse, Studiensemester semester, Studiengang studiengang
    ) {
        StudiumsPraktikumsstelle stelle = new StudiumsPraktikumsstelle();
        stelle.setId(UUID.randomUUID());
        stelle.setDienststelle(dienststelle);
        stelle.setOertlicheAusbilder(ausbilder);
        stelle.setEmail(email);
        stelle.setTaetigkeiten(taetigkeiten);
        stelle.setDringlichkeit(dringlichkeit);
        stelle.setReferat(referat);
        stelle.setProgrammierkenntnisse(programmierkenntnisse);
        stelle.setStudiensemester(semester);
        stelle.setStudienart(studiengang);
        return stelle;
    }

    private AusbildungsPraktikumsstelleDTO createPraktikumsstelleDTO(AusbildungsPraktikumsstelle stelle) {
        return AusbildungsPraktikumsstelleDTO.builder()
                .dienststelle(stelle.getDienststelle()).oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail()).taetigkeiten(stelle.getTaetigkeiten())
                .dringlichkeit(stelle.getDringlichkeit()).namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .referat(stelle.getReferat()).projektarbeit(stelle.isProjektarbeit())
                .ausbildungsjahr(stelle.getAusbildungsjahr())
                .ausbildungsrichtung(stelle.getAusbildungsrichtung()).build();
    }

    private StudiumsPraktikumsstelleDTO createPraktikumsstelleDTO(StudiumsPraktikumsstelle stelle) {
        return StudiumsPraktikumsstelleDTO.builder()
                .dienststelle(stelle.getDienststelle()).oertlicheAusbilder(stelle.getOertlicheAusbilder())
                .email(stelle.getEmail()).taetigkeiten(stelle.getTaetigkeiten())
                .dringlichkeit(stelle.getDringlichkeit()).namentlicheAnforderung(stelle.getNamentlicheAnforderung())
                .referat(stelle.getReferat()).programmierkenntnisse(stelle.isProgrammierkenntnisse())
                .studiensemester(stelle.getStudiensemester()).studienart(stelle.getStudienart()).build();
    }
}
