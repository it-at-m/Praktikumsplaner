package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender mailSender;

    private final PraktikumsstellenService praktikumsstellenService;

    /*
     * @return List of all Praktikumsstellen where the mail could not be sent
     */
    public List<PraktikumsstelleDto> sendMailsToAssignedPraktikumsplaetze() {
        List<PraktikumsstelleDto> faultyStellen = new ArrayList<>();

        praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum().forEach(
                stelle -> {
                    try {
                        sendSingleMail(stelle.email(), "Praktikumsplatz zugeteilt", buildMailBody("successfulAssignmentMail", buildMailData(stelle)));
                    } catch (MessagingException e) {
                        faultyStellen.add(stelle);
                    }
                });

        return faultyStellen;
    }

    private String buildMailBody(String templateName, Map<String, String> data) {
        Context context = new Context();
        context.setVariables(Collections.unmodifiableMap(data));

        return templateEngine.process(templateName, context);
    };

    private void sendSingleMail(String to, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);

        mailSender.send(mimeMessage);
    }

    private Map<String, String> buildMailData(PraktikumsstelleDto praktikumsstelleDto) {
        return Map.of(
                "ausbilder", praktikumsstelleDto.oertlicheAusbilder(),
                "nachwuchskraftName", praktikumsstelleDto.assignedNwk().vorname() + " " + praktikumsstelleDto.assignedNwk().nachname(),
                "startDatum", "01.01.2024",
                "endDatum", "01.01.2025"
        );
    }
}
