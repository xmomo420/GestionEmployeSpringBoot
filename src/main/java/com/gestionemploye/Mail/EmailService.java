package com.gestionemploye.Mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    @Override
    public void envoyerCourriel(String destinataire, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(message, true);
            mimeMessageHelper.setTo(destinataire);
            mimeMessageHelper.setSubject("Récuperation mot de passe");
            mimeMessageHelper.setFrom("davidvilla75@live.ca");
        } catch (MessagingException e) {
            final String MSG_ERROR = "Echec de l'envoi du courriel";
            System.out.println(MSG_ERROR);
            LOGGER.error(MSG_ERROR);
            throw new IllegalStateException(MSG_ERROR);
        }
    }
}
