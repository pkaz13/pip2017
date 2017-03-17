package pl.hycom.pip.messanger.handler;

import com.github.messenger4j.send.templates.GenericTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;
import com.github.messenger4j.send.MessengerSendClient;

import lombok.extern.log4j.Log4j2;

/**
 * Created by patry on 09/03/2017.
 */
@Log4j2
@Component
public class MessengerHelloWorldHandler implements TextMessageEventHandler {

    private MessengerSendClient sendClient;

    public MessengerHelloWorldHandler(MessengerSendClient sendClient) {
        this.sendClient = sendClient;
    }

    @Override
    public void handle(TextMessageEvent msg) {
        if (StringUtils.equalsIgnoreCase("hy", msg.getText())) {
            sendGenericMessage(msg.getSender().getId());
        } else {
            sendTextMessage(msg.getSender().getId(), "Hello World");
        }
    }

    private void sendTextMessage(String id, String message) {
        log.info("Sending message[" + message + "] to[" + id + "]");

        try {
            sendClient.sendTextMessage(id, message);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during sending answer", e);
        }
    }

    private void sendGenericMessage(String id) {

        final GenericTemplate genericTemplate = GenericTemplate.newBuilder()
                .addElements()
                    .addElement("rift")
                        .subtitle("Next generation virtual reality")
                        .itemUrl("https://www.oculus.com/en-us/rift/")
                        .imageUrl("http://messengerdemo.parseapp.com/img/rift.png")
                        .toList()
                    .addElement("touch")
                        .subtitle("Your Hands, Now in VR")
                        .itemUrl("https://www.oculus.com/en-us/touch/")
                        .imageUrl("http://messengerdemo.parseapp.com/img/touch.png")
                        .toList()
                    .done()
                .build();

        log.info("Sending structured message to[" + id + "]");

        try {
            sendClient.sendTemplate(id, genericTemplate);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during sending answer", e);
        }
    }
}
