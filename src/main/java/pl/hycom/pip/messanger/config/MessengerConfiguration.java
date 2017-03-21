package pl.hycom.pip.messanger.config;

import com.github.messenger4j.MessengerPlatformWrapper;
import com.github.messenger4j.profile.MessengerProfileClient;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.setup.MessengerSetupClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hycom.pip.messanger.handler.MessengerHelloWorldHandler;
import pl.hycom.pip.messanger.handler.MessengerProductsRecommendationHandler;

/**
 * Created by patry on 07/03/2017.
 */

@Configuration
public class MessengerConfiguration {

    @Autowired
    private ConfigService configService;

    @Autowired
    private MessengerProductsRecommendationHandler messengerProductsRecommendationHandler;

    @Bean
    public MessengerHelloWorldHandler messengerHelloWorldHandler() {
        return new MessengerHelloWorldHandler(sendClient());
    }

    @Bean
    public MessengerReceiveClient receiveClient() {
        return MessengerPlatformWrapper.newReceiveClientBuilder(configService.getAppSecret(), configService.getVerifyToken())
                .onTextMessageEvent(messengerProductsRecommendationHandler)
                .build();
    }

    @Bean
    public MessengerSendClient sendClient() {
        return MessengerPlatformWrapper.newSendClientBuilder(configService.getPageAccessToken()).build();
    }

    @Bean
    public MessengerSetupClient setupClient() {
        return MessengerPlatformWrapper.newSetupClientBuilder(configService.getPageAccessToken()).build();
    }

    @Bean
    public MessengerProfileClient profileClient() {
        return MessengerPlatformWrapper.newProfileClientBuilder(configService.getPageAccessToken()).build();
    }

}
