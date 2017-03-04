package pl.hycom.pip.messanger;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import pl.hycom.model.MessageRequestBody;
import pl.hycom.model.MessageResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Rafal Lebioda on 02.03.2017.
 */
@Controller
@Log4j2
public class WebhookMessenger
{
    //Temporary for testing, acessToken must is different for each user
    private final String accessToken = "EAASEpnxfYrwBAK7MZAPvt0awlzY8Ph8yDTHVe41QnBJDflZAgBQxD5U6T2Y6AG3z8nKTswiF5qPIevrZA8ftjoHRHQZABCCzcgWxwrOUBAU5ZBoQZA4IuHo1prqzZCgGZBIF1N07gdORcU9cVbLcScLUNAYccwTl67Dk40UMZClI6QQZDZD";

    @RequestMapping(value = "/webhook", method = GET, produces = MediaType.TEXT_PLAIN)
    public String verify(@RequestParam("hub.verify_token") final String verifyToken,
                         @RequestParam("hub.mode") final String mode,
                         @RequestParam("hub.challenge") final String challenge) {
        if (StringUtils.equals(verifyToken,"token") && StringUtils.equals(mode,"subscribe")){
            return challenge;
        } else {
            return "Failed validation. Make sure the validation tokens match.";
        }
    }

    @RequestMapping(value = "/webhook", method = POST, consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public void sendMessage(@RequestBody final MessageRequestBody body) {
        try {
            for (MessageRequestBody.Entry entry : body.entry) {
                for (MessageRequestBody.Messaging messaging : entry.messaging) {
                    MessageResponse messageResponse = new MessageResponse();
                    messageResponse.recipient = new MessageResponse.Recipient();
                    messageResponse.recipient.id = messaging.sender.id;

                    messageResponse.message = new MessageResponse.MessageData();

                    if (messaging.message != null && messaging.message.text != null) {
                        messageResponse.message.text = "Hello World!";

                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseString = objectMapper.writeValueAsString(messageResponse);
                        okhttp3.RequestBody requestBody = okhttp3.RequestBody.
                                create(okhttp3.MediaType.parse("application/json; charset=utf-8"), responseString);
                        Request request = new Request.Builder().
                                url("https://graph.facebook.com/v2.6/me/messages?access_token=" + accessToken).
                                post(requestBody).build();
                        OkHttpClient client = new OkHttpClient();
                        Response response = client.newCall(request).execute();
                        log.info("Sending response:");
                        log.info(response.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
