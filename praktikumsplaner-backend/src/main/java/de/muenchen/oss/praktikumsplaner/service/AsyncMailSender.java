package de.muenchen.oss.praktikumsplaner.service;

import de.muenchen.oss.praktikumsplaner.domain.dtos.PraktikumsstelleDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Log4j2
public class AsyncMailSender {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:${spring.mail.username:}}")
    private String from;

    @Value("${app.mail.reply-to:}")
    private String replyTo;

    /*
     * Send a Mail Async
     */
    @Async
    public CompletableFuture<PraktikumsstelleDto> sendSingleMailAsync(final PraktikumsstelleDto stelle, final String mailBody) {
        try {
            log.debug("Start Mail-Send for {} at {}", stelle.assignedNwk(), stelle.dienststelle());
            sendSingleMail(stelle.email(), "Praktikumsplatz zugeteilt", mailBody);
            return CompletableFuture.completedFuture(null); // null for success, could also use Optional
        } catch (Exception e) {
            return CompletableFuture.completedFuture(stelle); // return failed stelle
        }
    }

    private void sendSingleMail(final String to, final String subject, final String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(from);
        if(StringUtils.hasText(replyTo)) {
            mimeMessageHelper.setReplyTo(replyTo);
        }
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);

        mailSender.send(mimeMessage);
    }

}
