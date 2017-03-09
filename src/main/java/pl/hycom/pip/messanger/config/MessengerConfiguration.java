package pl.hycom.pip.messanger.config;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hycom.pip.messanger.util.MessengerUtility;

/**
 * Created by patry on 07/03/2017.
 */

@Configuration
public class MessengerConfiguration {

    @Autowired
    private ConfigService configService;

    @Bean
    MessengerReceiveClient getReceiveClient() {
        return MessengerPlatform.newReceiveClientBuilder(configService.getAppSecret(), configService.getVerifyToken())
                .onTextMessageEvent(event -> MessengerUtility.getInstance().sendTextMessage(event.getSender().getId(), "Hello World!"))
                .build();
    }

    @Bean
    MessengerSendClient getSendClient() {
        return MessengerPlatform.newSendClientBuilder(configService.getPageAccessToken()).build();
    }

}
