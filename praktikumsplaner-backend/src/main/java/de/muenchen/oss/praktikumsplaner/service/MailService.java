package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import de.muenchen.oss.praktikumsplaner.domain.dtos.ZeitraumDto;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailService {

    private final ITemplateEngine templateEngine;

    private final PraktikumsstellenService praktikumsstellenService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final AsyncMailSender mailSender;

    /*
     * Send Mails to all Assigned Praktikumsplätze.
     * Aggregates all failed deliveries.
     */
    public List<PraktikumsstelleDto> sendMailsToAssignedPraktikumsplaetze(final Map<String, ZeitraumDto> assignmentPeriods) {
        List<CompletableFuture<PraktikumsstelleDto>> futures = new ArrayList<>();

        for (PraktikumsstelleDto stelle : praktikumsstellenService.getAllAssignedPraktikumsstellenInMostRecentPassedMeldezeitraum()) {
            try {
                String mailBody = buildMailBody("successfulAssignmentMail", buildMailData(stelle, assignmentPeriods));
                futures.add(mailSender.sendSingleMailAsync(stelle, mailBody));
            } catch (Exception e) {
                log.warn("Skipping mail for dienststelle={} due to missing period or template error", stelle.dienststelle(), e);
                futures.add(CompletableFuture.completedFuture(stelle));
            }
        }

        // Await completion and collect failed sendings
        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();
    }

    private String buildMailBody(final String templateName, final Map<String, String> data) {
        Context context = new Context();
        context.setVariables(Collections.unmodifiableMap(data));

        return templateEngine.process(templateName, context);
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
