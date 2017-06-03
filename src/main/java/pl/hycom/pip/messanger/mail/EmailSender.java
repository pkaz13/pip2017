package pl.hycom.pip.messanger.mail;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

/**
 * Created by Piotr on 21.05.2017.
 */
public interface EmailSender {
    void sendEmail(MimeMessage message);
}
