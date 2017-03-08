package pl.hycom.pip.messanger;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Rafal Lebioda on 02.03.2017.
 */
@Controller
public class MessengerWebhook {
    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(MessengerWebhook.class);
    //Temporary for testing, accessToken must is different for each user
    private final String pageAccessToken = "EAAFPm2dotHsBAO4KjZBZCZCeUWLsS7IXoACB9ZAZCARsRYh9PY0ogrSM39NdKAihy2jbdlypfT5a8TBjPbUwjeFaxo28ORZCXd7MIejDBv4suX0AuwVtTtlMu1zfLy3MGmQWggZBUARwzGWsg0eoYcVmqYCAhzLOJFMgUaonbe67QZDZD";
    private final String verToken = "token";
    private final String appSecret = "bf00817292428306cb0c761941ad4d14";
//    @Autowired
//    private ConfigService configService;

    @RequestMapping(value = "/webhook", method = GET, produces = MediaType.TEXT_PLAIN)
    @ResponseBody
    public ResponseEntity<String> verify(@RequestParam("hub.verify_token") final String verifyToken,
                                         @RequestParam("hub.mode") final String mode,
                                         @RequestParam("hub.challenge") final String challenge) {
        //MessengerIntegrationProperties properties = configService.getMsgIntegrationProperties();
        if (StringUtils.equals(verifyToken, verToken) && StringUtils.equals(mode, "subscribe")) {
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
            MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder(appSecret, verToken)
                    .onTextMessageEvent(event -> sendTextMessage(event.getSender().getId(), "Hello World !!! Działa ;)"))
                    .build();
            receiveClient.processCallbackPayload(payload, signature);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (MessengerVerificationException e) {
            log.error("Error during token verification {} from msg: {}", payload, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void sendTextMessage(String id, String message) {
        MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
        try {
            log.info("Sending answer");
            sendClient.sendTextMessage(id, message);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during sending answer "+ e.getMessage());
        }

    }


}
