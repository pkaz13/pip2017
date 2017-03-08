package pl.hycom.pip.messanger;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.hycom.pip.messanger.config.ConfigService;

import javax.ws.rs.core.MediaType;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Rafal Lebioda on 02.03.2017.
 */
@Log4j2
@Controller
public class MessengerWebhook {

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/webhook", method = GET, produces = MediaType.TEXT_PLAIN)
    @ResponseBody
    public ResponseEntity<String> verify(@RequestParam("hub.verify_token") final String verifyToken,
                                         @RequestParam("hub.mode") final String mode,
                                         @RequestParam("hub.challenge") final String challenge) {
        //MessengerIntegrationProperties properties = configService.getMsgIntegrationProperties();
        if (StringUtils.equals(verifyToken, configService.getVerifyToken()) && StringUtils.equals(mode, "subscribe")) {
            return ResponseEntity.ok(challenge);
        } else {
            log.info("Failed validation. Make sure the validation tokens match.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/webhook", method = POST, consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> sendMessage(@RequestBody final String payload,
                                            @RequestHeader(value = "X-Hub-Signature") String signature) {
        try {
            //MessengerIntegrationProperties properties = configService.getMsgIntegrationProperties();
            log.info("Received message - starting prepare answer ");
            MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder(configService.getAppSecret(), configService.getVerifyToken())
                    .onTextMessageEvent(event -> sendTextMessage(event.getSender().getId(), "Hello World !!! Działa ;)"))
                    .build();
            receiveClient.processCallbackPayload(payload, signature);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (MessengerVerificationException e) {
            log.error("Error during token verification {} from msg: {}", payload, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void sendTextMessage(String id, String message) {
        MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder(configService.getPageAccessToken()).build();
        try {
            log.info("Sending answer");
            sendClient.sendTextMessage(id, message);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during sending answer " + e.getMessage());
        }
    }


}
