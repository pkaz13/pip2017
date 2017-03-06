package pl.hycom.pip.messanger;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import lombok.extern.log4j.Log4j2;
//import okhttp3.*
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.hycom.pip.messanger.config.MessengerIntegrationProperties;
import pl.hycom.pip.messanger.config.ConfigService;
import pl.hycom.pip.messanger.model.MessageRequestBody;
import pl.hycom.pip.messanger.model.MessageResponse;

import javax.ws.rs.core.MediaType;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Rafal Lebioda on 02.03.2017.
 */
@Controller
@Log4j2
public class WebhookMessenger {
    //Temporary for testing, accessToken must is different for each user
    private final String pageAccessToken = "EAAImJ54xVrcBAJp5Aw1dU1zIPSw92mprMUo5QIRbux0WxrfKZCayfyEBJMmTJXoqrSfSglcUBV39YRvPZBo2jAaQu2QyiyA5vdTkCBbJE9NOAjpiM33PQ7sS0sIaMSsR6COd5IWihqYSjhTZBdQxfPqE7oliQ95lFKknSCUqQZDZD";
    private final String verToken = "token";
    private final String appSecret = "d44fb500a9e69c572a8fa8d01fab8218";
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(value = "/webhook", method = POST, consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public void sendMessage(@RequestBody final String payload,
                            @RequestHeader(value = "X-Hub-Signature", defaultValue = "niewiem") String signature) {

        //MessengerIntegrationProperties properties = configService.getMsgIntegrationProperties();

        System.out.println("Payload: " + payload);
        System.out.println("Signature: " + signature);

        MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder(pageAccessToken).build();

        MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder(appSecret, verToken)
                .onTextMessageEvent(event -> sendTextMessage(event.getSender().getId(), event.getText()))
                .build();

        try {
            receiveClient.processCallbackPayload(payload, signature);
        } catch (MessengerVerificationException e) {
            e.printStackTrace();
        }

//        if(StringUtils.equals(body.object,"page"))
//        {
//            for(MessageRequestBody.Entry entry : body.entry)
//            {
//                String pageID=entry.id;
//                String time=entry.time;
//                for(MessageRequestBody.Messaging messaging : entry.messaging)
//                {
//                    if(messaging!=null)
//                    {
//                        receivedMessage(messaging);
//                    }
//                    else
//                    {
//                        log.info("Webhook received unknown event: ");
//                    }
//                }
//            }
//        }
//        try {
//            for (MessageRequestBody.Entry entry : body.entry) {
//                for (MessageRequestBody.Messaging messaging : entry.messaging) {
//                    MessageResponse messageResponse = new MessageResponse();
//                    messageResponse.recipient = new MessageResponse.Recipient();
//                    messageResponse.recipient.id = messaging.sender.id;
//
//                    messageResponse.message = new MessageResponse.MessageData();
//
//                    if (messaging.message != null && messaging.message.text != null) {
//                        messageResponse.message.text = "Hello World!";
//
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        String responseString = objectMapper.writeValueAsString(messageResponse);
//                        okhttp3.RequestBody requestBody = okhttp3.RequestBody.
//                                create(okhttp3.MediaType.parse("application/json; charset=utf-8"), responseString);
//                        Request request = new Request.Builder().
//                                url("https://graph.facebook.com/v2.6/me/messages?access_token=" + accessToken).
//                                post(requestBody).build();
//                        OkHttpClient client = new OkHttpClient();
//                        Response response = client.newCall(request).execute();
//                        log.info("Sending response:");
//                        log.info(response.toString());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private void receivedMessage(MessageRequestBody.Messaging messaging) {
        if (messaging.getMessage().getText() != null) {
            sendTextMessage(messaging.getSender().getId(), messaging.getMessage().getText());
        }
    }

    private void sendTextMessage(String id, String message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setRecipient(new MessageResponse.Recipient());
        messageResponse.getRecipient().setId(id);
        messageResponse.setMessage(new MessageResponse.MessageData());
        messageResponse.getMessage().setText(message);
        callSendApi(messageResponse);
    }

    private void callSendApi(MessageResponse messageResponse) {
        //MessengerIntegrationProperties properties = configService.getMsgIntegrationProperties();

        MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
        try {
            sendClient.sendTextMessage(messageResponse.getRecipient().getId(), messageResponse.getMessage().getText());
        } catch (MessengerApiException | MessengerIOException e) {
            e.printStackTrace();
        }

    }
}
