package de.muenchen.oss.praktikumsplaner.rest;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("experimental")
@RequiredArgsConstructor
public class ExperimentalController {

    private final JavaMailSender mailSender;

    @PostMapping("mail")
    public void sendMail(@RequestBody final String mailBody) {
        val mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("pp@localhost");
        mailMessage.setTo("receiver@localhost");
        mailMessage.setSubject("Testmail");
        mailMessage.setText(mailBody);

        mailSender.send(mailMessage);
    }
}
