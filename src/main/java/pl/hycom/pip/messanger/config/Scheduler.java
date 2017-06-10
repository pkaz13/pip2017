package pl.hycom.pip.messanger.config;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hycom.pip.messanger.repository.PasswordResetTokenRepository;
import pl.hycom.pip.messanger.repository.model.PasswordResetToken;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Created by Piotr on 09.06.2017.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class Scheduler {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    private final PasswordResetTokenRepository tokenRepository;

    @Scheduled(cron = "* */30 * * * *")
    public void deleteExpiredTokens() {
        log.info("deleteExpiredTokens method from Scheduler invoked");
        List<PasswordResetToken> resetTokens = StreamSupport.stream(tokenRepository.findAll().spliterator(), false).collect(Collectors.toList());

        for (PasswordResetToken token : resetTokens) {
            if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
                log.info("token " + token.getToken() + " expired and will be removed from database");
                tokenRepository.delete(token);
            }
        }
    }
}
