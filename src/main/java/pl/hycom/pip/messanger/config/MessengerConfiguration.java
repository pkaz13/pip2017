package pl.hycom.pip.messanger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.messenger4j.MessengerPlatformWrapper;
import com.github.messenger4j.profile.MessengerProfileClient;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.setup.MessengerSetupClient;

import pl.hycom.pip.messanger.handler.MessengerHelloWorldHandler;
import pl.hycom.pip.messanger.handler.MessengerProductsRecommendationHandler;
import pl.hycom.pip.messanger.handler.PipelineMessageHandler;

/**
 * Created by patry on 07/03/2017.
 */

@Configuration
public class MessengerConfiguration {

    @Value("${messenger.pageAccessToken}")
    private String pageAccessToken;

    @Value("${messenger.appSecret}")
    private String appSecret;

    @Value("${messenger.verifyToken}")
    private String verifyToken;

    @Value("${messenger.recommendation.products-amount:3}")
    private Integer productsAmount;

    @Bean
    public PipelineMessageHandler pipelineMessageHandler() {
        return new PipelineMessageHandler();
    }

    @Bean
    public MessengerProductsRecommendationHandler messengerProductsRecommendationHandler() {
        return new MessengerProductsRecommendationHandler().setProductsAmount(productsAmount);
    }

    @Bean
    public MessengerHelloWorldHandler messengerHelloWorldHandler() {
        return new MessengerHelloWorldHandler(sendClient());
    }

    @Bean
    public MessengerReceiveClient receiveClient() {
        return MessengerPlatformWrapper.newReceiveClientBuilder(appSecret, verifyToken)
                .onTextMessageEvent(messengerProductsRecommendationHandler())
                // .onTextMessageEvent(pipelineMessageHandler())
                .build();
    }

    @Bean
    public MessengerSendClient sendClient() {
        return MessengerPlatformWrapper.newSendClientBuilder(pageAccessToken).build();
    }

    @Bean
    public MessengerSetupClient setupClient() {
        return MessengerPlatformWrapper.newSetupClientBuilder(pageAccessToken).build();
    }

    @Bean
    public MessengerProfileClient profileClient() {
        return MessengerPlatformWrapper.newProfileClientBuilder(pageAccessToken).build();
    }

}
