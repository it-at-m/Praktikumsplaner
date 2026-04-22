package de.muenchen.oss.praktikumsplaner.service;

import static de.muenchen.oss.praktikumsplaner.TestConstants.SPRING_TEST_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.praktikumsplaner.configuration.PraktikumsplanerProperties;
import de.muenchen.oss.praktikumsplaner.domain.dtos.AusbildungsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.NwkDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.StudiumsPraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest(classes = { PraktikumsplanerProperties.class })
@EnableConfigurationProperties(PraktikumsplanerProperties.class)
@ActiveProfiles(SPRING_TEST_PROFILE)
public class MailServiceTest {

    @Autowired
    private PraktikumsplanerProperties praktikumsplanerProperties;

    @MockitoBean
    private JavaMailSender mailSender;

    @MockitoBean
    private ITemplateEngine templateEngine;

    @MockitoBean
    private PraktikumsstellenService praktikumsstellenService;

    private MailService mailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        AsyncMailSender asyncMailSender = new AsyncMailSender(mailSender);
        mailService = new MailService(templateEngine, praktikumsstellenService, asyncMailSender, praktikumsplanerProperties);

        // Set fields via reflection
        ReflectionTestUtils.setField(asyncMailSender, "from", "testSender");
        ReflectionTestUtils.setField(asyncMailSender, "replyTo", "testReply");
    }

    @Test
    public void testSendMailsToAssignedPraktikumsplaetze() {
        // Arrange
        MimeMessage mockMimeMessage = Mockito.mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("Mock-Mail-Body");

        NwkDto assignedNwk1 = createNwkDto("Max", "Mustermann", Studiengang.BSC, null, "19/25");
        NwkDto assignedNwk2 = createNwkDto("Erika", "Musterfrau", Studiengang.BWI, null, "23/27");
        NwkDto assignedNwk3 = createNwkDto("John", "Smith", null, Ausbildungsrichtung.FISI, "25/29");

        List<PraktikumsstelleDto> allPraktikumsstellen = new ArrayList<>();

        AusbildungsPraktikumsstelleDto ausbildungsPraktikumsstelle1 = createAusbildungsPraktikumsstelleDto("ITM-SLP31", "Max Musterfrau", "max@musterfrau.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.ZWINGEND,
                Set.of(Ausbildungsjahr.JAHR2), Ausbildungsrichtung.FISI, assignedNwk3);
        allPraktikumsstellen.add(ausbildungsPraktikumsstelle1);

        StudiumsPraktikumsstelleDto studiumsPraktikumsstelle1 = createStudiumsPraktikumsstelleDto("ITM-SLP33", "Test Tester", "test@tester.de",
                "Entwicklung eines Praktikumsplaners", Dringlichkeit.NACHRANGIG,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BSC, assignedNwk2);
        StudiumsPraktikumsstelleDto studiumsPraktikumsstelle2 = createStudiumsPraktikumsstelleDto("ITM-DKL-IL", "Test Testerin", "test@testerin.de",
                "Design eines Praktikumsplaners", Dringlichkeit.NACHRANGIG,
                Set.of(Studiensemester.SEMESTER5), Studiengang.BWI, assignedNwk1);
        allPraktikumsstellen.add(studiumsPraktikumsstelle1);
        allPraktikumsstellen.add(studiumsPraktikumsstelle2);

        when(praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum()).thenReturn(allPraktikumsstellen);

        // Act
        List<PraktikumsstelleDto> result = mailService.sendMailsToAssignedPraktikumsplaetze();

        // Assert
        assertEquals(0, result.size());
        verify(mailSender, times(3)).createMimeMessage();
    }

    private AusbildungsPraktikumsstelleDto createAusbildungsPraktikumsstelleDto(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten, final Dringlichkeit dringlichkeit,
            final Set<Ausbildungsjahr> semester, final Ausbildungsrichtung ausbildungsrichtung, final NwkDto assignedNwk) {
        return AusbildungsPraktikumsstelleDto.builder()
                .dienststelle(dienststelle).oertlicheAusbilder(ausbilder).email(email).taetigkeiten(taetigkeiten)
                .dringlichkeit(dringlichkeit).ausbildungsjahr(semester)
                .ausbildungsrichtung(ausbildungsrichtung).assignedNwk(assignedNwk).build();
    }

    private StudiumsPraktikumsstelleDto createStudiumsPraktikumsstelleDto(
            final String dienststelle, final String ausbilder, final String email, final String taetigkeiten, final Dringlichkeit dringlichkeit,
            final Set<Studiensemester> semester, final Studiengang studiengang, final NwkDto assignedNwk) {
        return StudiumsPraktikumsstelleDto.builder().dienststelle(dienststelle).oertlicheAusbilder(ausbilder).email(email).taetigkeiten(taetigkeiten)
                .dringlichkeit(dringlichkeit).studiensemester(semester)
                .studiengang(studiengang).assignedNwk(assignedNwk).build();
    }

    private NwkDto createNwkDto(
            final String vorname, final String nachname, final Studiengang studiengang, final Ausbildungsrichtung ausbildungsrichtung, String jahrgang) {
        return NwkDto.builder().id(UUID.randomUUID()).vorname(vorname).nachname(nachname).studiengang(studiengang)
                .ausbildungsrichtung(ausbildungsrichtung).jahrgang(jahrgang).build();
    }
}
