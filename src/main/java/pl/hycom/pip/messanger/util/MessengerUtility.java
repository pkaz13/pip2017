package pl.hycom.pip.messanger.util;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.MessengerSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.log4j.Log4j2;


/**
 * Created by patry on 09/03/2017.
 */
@Log4j2
public class MessengerUtility {

    @Autowired
    private MessengerSendClient sendClient;

    public void sendTextMessage(String id, String message) {
        try {
            log.info("Sending answer");
            sendClient.sendTextMessage(id, message);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during sending answer " + e.getMessage());
        }
    }
}
