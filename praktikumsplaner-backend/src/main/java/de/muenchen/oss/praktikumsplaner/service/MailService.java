package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailService {

    private final ITemplateEngine templateEngine;

    private final JavaMailSender mailSender;

    private final PraktikumsstellenService praktikumsstellenService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Value("${spring.mail.username}")
    private String from;

    /*
     * @return List of all Praktikumsstellen where the mail could not be sent
     */
    public List<PraktikumsstelleDto> sendMailsToAssignedPraktikumsplaetze(final Map<String, ZeitraumDto> assignmentPeriods) {
        List<PraktikumsstelleDto> faultyStellen = new ArrayList<>();
        praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum().forEach(
                stelle -> {
                    try {
                        sendSingleMail(stelle.email(), "Praktikumsplatz zugeteilt",
                                buildMailBody("successfulAssignmentMail", buildMailData(stelle, assignmentPeriods)));
                    } catch (MessagingException e) {
                        faultyStellen.add(stelle);
                    }
                });

        return faultyStellen;
    }

    private String buildMailBody(final String templateName, final Map<String, String> data) {
        Context context = new Context();
        context.setVariables(Collections.unmodifiableMap(data));

        return templateEngine.process(templateName, context);
    }

    private void sendSingleMail(final String to, final String subject, final String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);

        mailSender.send(mimeMessage);
    }

    private Map<String, String> buildMailData(final PraktikumsstelleDto praktikumsstelleDto, final Map<String, ZeitraumDto> assignmentPeriods) {
        String studiengangOderAusbildungsrichtungKey = praktikumsstelleDto.assignedNwk().studiengang() != null
                ? praktikumsstelleDto.assignedNwk().studiengang().toString()
                : praktikumsstelleDto.assignedNwk().ausbildungsrichtung().toString();

        return Map.of(
                "ausbilder", praktikumsstelleDto.oertlicheAusbilder(),
                "nachwuchskraftName", praktikumsstelleDto.assignedNwk().vorname() + " " + praktikumsstelleDto.assignedNwk().nachname(),
                "startDatum", assignmentPeriods.get(studiengangOderAusbildungsrichtungKey).startZeitpunkt().format(dateTimeFormatter),
                "endDatum", assignmentPeriods.get(studiengangOderAusbildungsrichtungKey).endZeitpunkt().format(dateTimeFormatter));
    }
}
