package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.mail.EmailSender;
import pl.hycom.pip.messanger.repository.model.User;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Piotr on 21.05.2017.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class EmailService implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(MimeMessage message) {
        javaMailSender.send(message);
        log.info("Sending email to ...");
    }

    private MimeMessage constructEmail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom("messenger.recommendations2017@gmail.com");
            helper.setSubject(subject);
            helper.setText(content, true);

        } catch (MessagingException e) {
            log.info(e.getMessage());
        }
        return message;
    }

    public MimeMessage constructResetTokenEmail(String contextPath, User user, String token) {
        String url = contextPath + "/change/password/token/" + token;
        return constructEmail(user.getEmail(), "Reset password", url);
    }

}
