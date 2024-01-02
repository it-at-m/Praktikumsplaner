package de.muenchen.oss.praktikumsplaner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import de.muenchen.oss.praktikumsplaner.domain.dtos.*;
import de.muenchen.oss.praktikumsplaner.domain.enums.*;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import java.time.LocalDate;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MailServiceTest {

    @InjectMocks
    private MailService mailService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private ITemplateEngine templateEngine;

    @Mock
    private PraktikumsstellenService praktikumsstellenService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(mailService, "from", "testSender");
    }

    @Test
    public void testSendMailsToAssignedPraktikumsplaetze() {
        // Arrange
        MimeMessage mockMimeMessage = Mockito.mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("Mock-Mail-Body");

        NwkDto assignedNwk1 = createNwkDto("Max", "Mustermann", Studiengang.BSC, null);
        NwkDto assignedNwk2 = createNwkDto("Erika", "Musterfrau", Studiengang.BWI, null);
        NwkDto assignedNwk3 = createNwkDto("John", "Smith", null, Ausbildungsrichtung.FISI);

        List<PraktikumsstelleDto> allPraktikumsstellen = new ArrayList<>();

        AusbildungsPraktikumsstelleDto ausbildungsPraktikumsstelle1 = createAusbildungsPraktikumsstelleDto("KM81", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.ZWINGEND, Referat.ITM,
                Ausbildungsjahr.JAHR2, Ausbildungsrichtung.FISI, assignedNwk3);
        allPraktikumsstellen.add(ausbildungsPraktikumsstelle1);

        StudiumsPraktikumsstelleDto studiumsPraktikumsstelle1 = createStudiumsPraktikumsstelleDto("KM83", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Studiensemester.SEMESTER5, Studiengang.BSC, assignedNwk2);
        StudiumsPraktikumsstelleDto studiumsPraktikumsstelle2 = createStudiumsPraktikumsstelleDto("InnoLab", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", Dringlichkeit.NACHRANGIG, Referat.ITM,
                Studiensemester.SEMESTER5, Studiengang.BWI, assignedNwk1);
        allPraktikumsstellen.add(studiumsPraktikumsstelle1);
        allPraktikumsstellen.add(studiumsPraktikumsstelle2);

        when(praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum()).thenReturn(allPraktikumsstellen);

        // Act
        List<PraktikumsstelleDto> result = mailService.sendMailsToAssignedPraktikumsplaetze(Map.of(
                "BSC", ZeitraumDto.builder().startZeitpunkt(LocalDate.now()).endZeitpunkt(LocalDate.now().plusDays(20)).build(),
                "BWI", ZeitraumDto.builder().startZeitpunkt(LocalDate.now()).endZeitpunkt(LocalDate.now().plusDays(20)).build(),
                "FISI", ZeitraumDto.builder().startZeitpunkt(LocalDate.now()).endZeitpunkt(LocalDate.now().plusDays(20)).build()));

        // Assert
        assertEquals(0, result.size());
        verify(mailSender, times(3)).createMimeMessage();
    }

    private AusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten, final Dringlichkeit dringlichkeit,
            final Referat referat, final Ausbildungsjahr semester, final Ausbildungsrichtung ausbildungsrichtung, final NwkDto assignedNwk) {
        return AusbildungsPraktikumsstelleDto.builder()
                .dienststelle(dienststelle).oertlicheAusbilder(ausbilder).email(email).taetigkeiten(taetigkeiten)
                .dringlichkeit(dringlichkeit).referat(referat).ausbildungsjahr(semester)
                .ausbildungsrichtung(ausbildungsrichtung).assignedNwk(assignedNwk).build();
    }

    private StudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten, final Dringlichkeit dringlichkeit,
            final Referat referat, final Studiensemester semester, final Studiengang studiengang, final NwkDto assignedNwk) {
        return StudiumsPraktikumsstelleDto.builder().dienststelle(dienststelle).oertlicheAusbilder(ausbilder).email(email).taetigkeiten(taetigkeiten)
                .dringlichkeit(dringlichkeit).referat(referat).studiensemester(semester)
                .studiengang(studiengang).assignedNwk(assignedNwk).build();
    }

    private NwkDto createNwkDto(
            final String vorname, final String nachname, final Studiengang studiengang, final Ausbildungsrichtung ausbildungsrichtung) {
        return NwkDto.builder().id(UUID.randomUUID()).vorname(vorname).nachname(nachname).studiengang(studiengang)
                .ausbildungsrichtung(ausbildungsrichtung).build();
    }
}
